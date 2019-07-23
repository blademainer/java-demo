package com.xiongyingqi.kafka;

import com.google.common.collect.Lists;
import com.google.common.primitives.Longs;
import kafka.api.*;
import kafka.client.ClientUtils;
import kafka.cluster.Broker;
import kafka.cluster.BrokerEndPoint;
import kafka.common.BrokerNotAvailableException;
import kafka.common.OffsetMetadataAndError;
import kafka.common.TopicAndPartition;
import kafka.consumer.SimpleConsumer;
import kafka.network.BlockingChannel;
import kafka.utils.ZKGroupTopicDirs;
import kafka.utils.ZkUtils;
import lombok.Getter;
import org.apache.kafka.common.protocol.Errors;
import org.apache.kafka.common.protocol.SecurityProtocol;
import org.bouncycastle.util.Strings;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import scala.*;
import scala.collection.*;
import scala.collection.mutable.HashMap;
import scala.collection.mutable.Map;

import java.lang.Long;
import java.util.ArrayList;

import org.apache.kafka.common.network.*;

/**
 * @author qi
 * @since 2018/6/28
 */
@Getter
public class KafkaMonitor {
    private static final Logger logger = LoggerFactory.getLogger(KafkaMonitor.class);
    private String zookeeper;
    private String topic;
    private String group;
    private int timeout = 30000;

    private Map<String, Seq<Object>> topicPartitionIdMap;
    private Map<TopicAndPartition, Long> offsetMap;
    private Map<TopicAndPartition, Long> lagMap = new HashMap<TopicAndPartition, Long>();
    private Map<Integer, Option<SimpleConsumer>> consumerMap = new HashMap<Integer, Option<SimpleConsumer>>();


    public KafkaMonitor(String zookeeper, String topic, String group) {
        this.zookeeper = zookeeper;
        this.topic = topic;
        this.group = group;
    }


    public void monitor() {
        offsetMap = new HashMap<TopicAndPartition, Long>();

        ZkUtils zkUtils = ZkUtils.apply(zookeeper, timeout, timeout, false);
        ArrayList<String> topics = Lists.newArrayList(Strings.split(topic, ','));
        Seq<String> topicsSeq = JavaConverters.asScalaIteratorConverter(topics.iterator()).asScala().toSeq();
        topicPartitionIdMap = zkUtils.getPartitionsForTopics(topicsSeq);

        ArrayList<TopicAndPartition> topicAndPartitionList = new ArrayList<TopicAndPartition>();
        Iterator<Tuple2<String, Seq<Object>>> tuple2Iterator = topicPartitionIdMap.iterator();
        while (tuple2Iterator.hasNext()) {
            Tuple2<String, Seq<Object>> tuple2 = tuple2Iterator.next();
            Seq<Object> objectSeq = tuple2._2;

            scala.collection.Iterator<Object> iterator = objectSeq.iterator();
            while (iterator.hasNext()) {
                Integer size = (Integer) iterator.next();
                TopicAndPartition topicAndPartition = new TopicAndPartition(topic, size);
                topicAndPartitionList.add(topicAndPartition);
            }
        }
        Seq<TopicAndPartition> topicPartition = JavaConverters.asScalaIteratorConverter(topicAndPartitionList.iterator()).asScala().toSeq();

        BlockingChannel channel = ClientUtils.channelToOffsetManager(group, zkUtils, timeout, timeout);
        OffsetFetchRequest offsetFetchRequest = new OffsetFetchRequest(group, topicPartition, OffsetFetchRequest.apply$default$3(), OffsetFetchRequest.apply$default$4(), OffsetFetchRequest.apply$default$5());
        long send = channel.send(offsetFetchRequest);
        logger.debug("Sent request: {} with: {} ", send, offsetFetchRequest);
        OffsetFetchResponse offsetFetchResponse = OffsetFetchResponse.readFrom(channel.receive().payload());

        Iterator<Tuple2<TopicAndPartition, OffsetMetadataAndError>> iterator = offsetFetchResponse.requestInfo().iterator();
        while (iterator.hasNext()) {
            Tuple2<TopicAndPartition, OffsetMetadataAndError> tuple2 = iterator.next();
            TopicAndPartition topicAndPartition = tuple2._1;
            OffsetMetadataAndError offsetAndMetadata = tuple2._2;
            logger.info("Found topicAndPartition: {} and offsetMeta: {}", topicAndPartition, offsetAndMetadata);

            if (OffsetMetadataAndError.NoOffset().equals(offsetAndMetadata)) {
                ZKGroupTopicDirs topicDirs = new ZKGroupTopicDirs(group, topicAndPartition.topic());
                String dir = topicDirs.consumerOffsetDir() + "/" + topicAndPartition.partition();
                String offsetStr = zkUtils.readData(dir)._1;
                try {
                    long offset = Longs.tryParse(offsetStr);
                    offsetMap.put(topicAndPartition, offset);
                } catch (Exception e) {
                    logger.error("Error to get offset: " + topicAndPartition, e);
                }
            } else if (Errors.NONE == offsetAndMetadata.error()) {
                offsetMap.put(topicAndPartition, offsetAndMetadata.offset());
            } else {
                logger.error("Could'nt fetch offset fo {} due to {}.", topicAndPartition, offsetAndMetadata.error());
            }

        }
        Iterator<String> iterator2 = topicsSeq.iterator();
        while (iterator2.hasNext()) {
            String topic = iterator2.next();
            processTopic(zkUtils, group, topic);
        }

    }

    private void processTopic(ZkUtils zkUtils, String group, String topic) {
        logger.debug("Process topic: {} group: {}", topic, group);
        Option<Seq<Object>> seqOption = topicPartitionIdMap.get(topic);
        if (seqOption.isEmpty()) {
            return;
        }
        Seq<Object> objectSeq = seqOption.get();
        Iterator<Object> iterator = objectSeq.iterator();
        while (iterator.hasNext()) {
            Integer size = (Integer) iterator.next();
            processPartition(zkUtils, group, topic, size);
        }
    }

    private void processPartition(ZkUtils zkUtils, String group, String topic, Integer producerId) {
        logger.debug("Process partition, topic: {} group: {} producerId: {}", topic, group, producerId);
        TopicAndPartition topicPartition = TopicAndPartition.apply(topic, producerId);
        Option<Long> offsetOpt = offsetMap.get(topicPartition);
        ZKGroupTopicDirs groupDirs = new ZKGroupTopicDirs(group, topic);
        Option<String> owner = zkUtils.readDataMaybeNull(groupDirs.consumerOwnerDir() + "/" + producerId)._1;
        Option<Object> leaderForPartition = zkUtils.getLeaderForPartition(topic, producerId);
        if (leaderForPartition.isEmpty()) {
            logger.warn("Leader fo partition is empty! topic: {} group: {} producerId: {}", topic, group, producerId);
            return;
        } else {
            Integer bid = (Integer) leaderForPartition.get();
            Option<Option<SimpleConsumer>> consumerOps = consumerMap.get(bid);
            if (consumerOps.isEmpty()) {
                Option<SimpleConsumer> consumer = getConsumer(zkUtils, bid);
                consumerMap.put(bid, consumer);
                consumerOps = Option.apply(consumer);
            }

            Option<SimpleConsumer> simpleConsumerOption = consumerOps.get();
            if (simpleConsumerOption.isEmpty()) {
                return;
            }
            TopicAndPartition topicAndPartition = TopicAndPartition.apply(topic, producerId);
            SimpleConsumer consumer = simpleConsumerOption.get();
//                java.util.Map<TopicAndPartition, PartitionOffsetRequestInfo> map = ImmutableMap.of(topicAndPartition, PartitionOffsetRequestInfo.apply(OffsetRequest.LatestTime(), 1));
            ArrayList<Tuple2<TopicAndPartition, PartitionOffsetRequestInfo>> tuples = Lists.newArrayList(Tuple2.apply(topicAndPartition, PartitionOffsetRequestInfo.apply(OffsetRequest.LatestTime(), 1)));
            Seq<Tuple2<TopicAndPartition, PartitionOffsetRequestInfo>> seq = JavaConverters.asScalaIteratorConverter(tuples.iterator()).asScala().toSeq();

            scala.collection.immutable.Map<TopicAndPartition, PartitionOffsetRequestInfo> map = scala.collection.immutable.Map$.MODULE$.apply(seq);
            OffsetRequest request = OffsetRequest.apply(map, OffsetRequest.CurrentVersion(), 0, OffsetRequest.DefaultClientId(), Request.OrdinaryConsumerId());
            OffsetResponse offsetsBefore = consumer.getOffsetsBefore(request);
            scala.collection.immutable.Map<TopicAndPartition, PartitionOffsetsResponse> topicAndPartitionPartitionOffsetsResponseMap = offsetsBefore.partitionErrorAndOffsets();
            Long logSize = (Long) topicAndPartitionPartitionOffsetsResponseMap.get(topicAndPartition).get().offsets().head();

            Iterator<Long> iterator = offsetOpt.iterator();
            while (iterator.hasNext()) {
                Long offset = iterator.next();
                if (offset == -1) {
                    logger.info("topic: {} returns -1 offset!", topicAndPartition);
                } else {
                    long lag = logSize - offset;
                    logger.info("topic: {} lag: {} owner: {}", topicAndPartition, lag, owner.isEmpty() ? "none" : owner.get());
                    lagMap.put(topicAndPartition, lag);
                }
            }
        }

    }

    private Option<SimpleConsumer> getConsumer(ZkUtils zkUtils, Integer bid) {
        Option<Broker> brokerInfo = zkUtils.getBrokerInfo(bid);
        if (brokerInfo.isEmpty()) {
            return Option.empty();
        } else {
            try {
                Broker broker = brokerInfo.get();
                BrokerEndPoint brokerEndPoint = broker.getBrokerEndPoint(ListenerName.forSecurityProtocol(SecurityProtocol.PLAINTEXT));
                SimpleConsumer consumerOffsetChecker = new SimpleConsumer(brokerEndPoint.host(), brokerEndPoint.port(), timeout, 100000, "ConsumerOffsetChecker");
                return Option.apply(consumerOffsetChecker);
            } catch (Exception e) {
                throw new BrokerNotAvailableException("Broker id " + bid + " does not exist");
            }

        }
    }


    public static void main(String[] args) {
        String zookeeper = "127.0.0.1:2181";
        String topic = "testQueue";
        String group = "testQueue";
        KafkaMonitor kafkaMonitor = new KafkaMonitor(zookeeper, topic, group);
        kafkaMonitor.monitor();
        System.out.println(kafkaMonitor.getLagMap());
    }
}
