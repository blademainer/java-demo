package com.xiongyingqi.java8.stream;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * @author <a href="http://xiongyingqi.com">qi</a>
 * @version 2015-09-28 11:22
 */
public class WordCountTest {
    public static final String WORDS = "User guide for 5.x\n"
            + "Did you know this page is automatically generated from a Github Wiki page? You can improve it by yourself here!\n"
            + "3rd-party translations\n"
            + "Simplified Chinese\n"
            + "Preface\n"
            + "The Problem\n"
            + "Nowadays we use general purpose applications or libraries to communicate with each other. For example, we often use an HTTP client library to retrieve information from a web server and to invoke a remote procedure call via web services.\n"
            + "\n"
            + "However, a general purpose protocol or its implementation sometimes does not scale very well. It is like we don't use a general purpose HTTP server to exchange huge files, e-mail messages, and near-realtime messages such as financial information and multiplayer game data. What's required is a highly optimized protocol implementation which is dedicated to a special purpose. For example, you might want to implement an HTTP server which is optimized for AJAX-based chat application, media streaming, or large file transfer. You could even want to design and implement a whole new protocol which is precisely tailored to your need.\n"
            + "\n"
            + "Another inevitable case is when you have to deal with a legacy proprietary protocol to ensure the interoperability with an old system. What matters in this case is how quickly we can implement that protocol while not sacrificing the stability and performance of the resulting application.\n"
            + "\n"
            + "The Solution\n"
            + "The Netty project is an effort to provide an asynchronous event-driven network application framework and tooling for the rapid development of maintainable high-performance · high-scalability protocol servers and clients.\n"
            + "\n"
            + "In other words, Netty is a NIO client server framework which enables quick and easy development of network applications such as protocol servers and clients. It greatly simplifies and streamlines network programming such as TCP and UDP socket server development.\n"
            + "\n"
            + "'Quick and easy' does not mean that a resulting application will suffer from a maintainability or a performance issue. Netty has been designed carefully with the experiences earned from the implementation of a lot of protocols such as FTP, SMTP, HTTP, and various binary and text-based legacy protocols. As a result, Netty has succeeded to find a way to achieve ease of development, performance, stability, and flexibility without a compromise.\n"
            + "\n"
            + "Some users might already have found other network application framework that claims to have the same advantage, and you might want to ask what makes Netty so different from them. The answer is the philosophy where it is built on. Netty is designed to give you the most comfortable experience both in terms of the API and the implementation from the day one. It is not something tangible but you will realize that this philosophy will make your life much easier as you read this guide and play with Netty.\n"
            + "\n"
            + "Getting Started\n"
            + "This chapter tours around the core constructs of Netty with simple examples to let you get started quickly. You will be able to write a client and a server on top of Netty right away when you are at the end of this chapter.\n"
            + "\n"
            + "If you prefer top-down approach in learning something, you might want to start from Chapter 2, Architectural Overview and get back here.\n"
            + "\n"
            + "Before Getting Started\n";

    @Test
    public void testCount() throws Exception {
        WordCount wordCount = new WordCount();
        Map<String, Long> stringLongMap = wordCount.wordCount("hello world!", "");
        System.out.println(stringLongMap);
        Map<String, Long> expected = simpleWordCount("hello world!", "");
        Assert.assertEquals(expected, stringLongMap);
    }

    @Test
    public void testCount2() throws Exception {
        WordCount wordCount = new WordCount();
        Map<String, Long> count = wordCount.wordCount(WORDS, "");
        System.out.println(count);
        Map<String, Long> expected = simpleWordCount(WORDS, "");
        Assert.assertEquals(expected, count);
    }

    @Test
    public void testCount3() throws Exception {
        WordCount wordCount = new WordCount();
        Map<String, Long> count = wordCount.wordCount(WORDS, " ");
        System.out.println(count);
        Map<String, Long> expected = simpleWordCount(WORDS, " ");
        Assert.assertEquals(expected, count);
    }

    private Map<String, Long> simpleWordCount(String word, String delimiter) {
        String[] split = word.split(delimiter);
        Map<String, Long> map = new HashMap<>();
        for (String s : split) {
            map.put(s, map.getOrDefault(s, 0L) + 1L);
        }
        return map;
    }
}
