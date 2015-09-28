package com.xiongyingqi.java8.stream;

import com.xiongyingqi.util.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * java8 stream demo: word count<p></p>
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-09-28 11:08
 */
public class WordCount {
    private static final Logger logger = LoggerFactory.getLogger(WordCount.class);

    public Map<String, Long> wordCount(String word, String delimiter) {
        Assert.hasText("word must has text!", word);
        Assert.notNull("delimiter must has text!", delimiter);
        String[] words = word.split(delimiter);
        List<String> strings = Arrays.asList(words);
        Optional<Map<String, Long>> reduce = strings.parallelStream()
                .map(x -> {
                    Map<String, Long> entry = new HashMap<>();
                    entry.put(x, 1L);
                    if (logger.isDebugEnabled()) {
                        logger.debug("wordCount... init map: {}", entry);
                    }
                    return entry;
                })
                .reduce((x, y) -> {
                    Map<String, Long> xMap = null;
                    if (logger.isDebugEnabled()) {
                        xMap = new HashMap<>();
                        xMap.putAll(x);
                    }
                    x.forEach((k, v) -> x.put(k, v + y.getOrDefault(k, 0L)));
                    y.forEach((k, v) -> {
                        if (!x.containsKey(k)) {
                            x.put(k, v);
                        }
                    });
                    if (logger.isDebugEnabled()) {
                        logger.debug("wordCount reduce... map one: {}, map two: {}, merge: {}",
                                     xMap, y, x);
                    }
                    return x;
                });
        if (logger.isDebugEnabled()) {
            logger.debug("reduce... word: {}, delimiter: {}, result: {}", word, delimiter,
                         reduce.get());
        }
        return reduce.get();

    }

    public Map<String, Long> wordCount(String word) {
        Assert.hasText("word must has text!", word);
        return wordCount(word, "");
    }
}
