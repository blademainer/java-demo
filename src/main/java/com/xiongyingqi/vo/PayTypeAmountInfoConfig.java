package com.xiongyingqi.vo;

import com.fasterxml.jackson.dataformat.xml.XmlMapper;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * @author xiongyingqi
 * @version 2015-12-10 11:12
 */
public class PayTypeAmountInfoConfig {
    private Collection<PayTypeAmountInfo> payTypeAmountInfoConfigs;

    public Collection<PayTypeAmountInfo> getPayTypeAmountInfoConfigs() {
        return payTypeAmountInfoConfigs;
    }

    public void setPayTypeAmountInfoConfigs(
            Collection<PayTypeAmountInfo> payTypeAmountInfoConfigs) {
        this.payTypeAmountInfoConfigs = payTypeAmountInfoConfigs;
    }

    @Override
    public String toString() {
        return "PayTypeAmountInfoConfig{" +
                "payTypeAmountInfoConfigs=" + payTypeAmountInfoConfigs +
                '}';
    }

    public static void main(String[] args) throws IOException {
        PayTypeAmountInfoConfig config = new PayTypeAmountInfoConfig();
        List<PayTypeAmountInfo> payTypeAmountInfoConfigs = new ArrayList<PayTypeAmountInfo>();

        PayTypeAmountInfo info = new PayTypeAmountInfo();
        info.setAmount(10);
        info.setBizNo("100001053");
        info.setPayType("JD");

        payTypeAmountInfoConfigs.add(info);
        payTypeAmountInfoConfigs.add(info);

        config.setPayTypeAmountInfoConfigs(payTypeAmountInfoConfigs);

        XmlMapper mapper = new XmlMapper();
        String s = mapper.writeValueAsString(payTypeAmountInfoConfigs.toArray(new PayTypeAmountInfo[payTypeAmountInfoConfigs.size()]));
//        String s = mapper.writeValueAsString(payTypeAmountInfoConfigs);
        System.out.println(s);

        PayTypeAmountInfoConfig payTypeAmountInfoConfig = mapper
                .readValue("<payTypeAmountInfoConfig>\n"
                        + "    <payTypeAmountInfoConfigs>\n"
                        + "        <payTypeAmountInfo>\n"
                        + "            <amount>255</amount>\n"
                        + "            <bizNo>100001053</bizNo>\n"
                        + "            <payType>JD</payType>\n"
                        + "        </payTypeAmountInfo>\n"
                        + "        <payTypeAmountInfo>\n"
                        + "            <amount>25</amount>\n"
                        + "            <bizNo>100001054</bizNo>\n"
                        + "            <payType>JD</payType>\n"
                        + "        </payTypeAmountInfo>\n"
                        + "    </payTypeAmountInfoConfigs>\n"
                        + "</payTypeAmountInfoConfig>\n", PayTypeAmountInfoConfig.class);
        System.out.println(payTypeAmountInfoConfig);
    }
}
