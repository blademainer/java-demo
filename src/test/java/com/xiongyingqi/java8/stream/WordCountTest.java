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
            + "Before Getting Started\n"
            + "The minimum requirements to run the examples which are introduced in this chapter are only two; the latest version of Netty and JDK 1.6 or above. The latest version of Netty is available in the project download page. To download the right version of JDK, please refer to your preferred JDK vendor's web site.\n"
            + "\n"
            + "As you read, you might have more questions about the classes introduced in this chapter. Please refer to the API reference whenever you want to know more about them. All class names in this document are linked to the online API reference for your convenience. Also, please don't hesitate to contact the Netty project community and let us know if there's any incorrect information, errors in grammar and typo, and if you have a good idea to improve the documentation.\n"
            + "\n"
            + "Writing a Discard Server\n"
            + "The most simplistic protocol in the world is not 'Hello, World!' but DISCARD. It's a protocol which discards any received data without any response.\n"
            + "\n"
            + "To implement the DISCARD protocol, the only thing you need to do is to ignore all received data. Let us start straight from the handler implementation, which handles I/O events generated by Netty.\n"
            + "\n"
            + "package io.netty.example.discard;\n"
            + "\n"
            + "import io.netty.buffer.ByteBuf;\n"
            + "\n"
            + "import io.netty.channel.ChannelHandlerContext;\n"
            + "import io.netty.channel.ChannelHandlerAdapter;\n"
            + "\n"
            + "/**\n"
            + " * Handles a server-side channel.\n"
            + " */\n"
            + "public class DiscardServerHandler extends ChannelHandlerAdapter { // (1)\n"
            + "\n"
            + "    @Override\n"
            + "    public void channelRead(ChannelHandlerContext ctx, Object msg) { // (2)\n"
            + "        // Discard the received data silently.\n"
            + "        ((ByteBuf) msg).release(); // (3)\n"
            + "    }\n"
            + "\n"
            + "    @Override\n"
            + "    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) { // (4)\n"
            + "        // Close the connection when an exception is raised.\n"
            + "        cause.printStackTrace();\n"
            + "        ctx.close();\n"
            + "    }\n"
            + "}\n"
            + "DiscardServerHandler extends ChannelHandlerAdapter, which is an implementation of ChannelHandler. ChannelHandler provides various event handler methods that you can override. For now, it is just enough to extend ChannelHandlerAdapter rather than to implement the handler interface by yourself.\n"
            + "We override the channelRead() event handler method here. This method is called with the received message, whenever new data is received from a client. In this example, the type of the received message is ByteBuf.\n"
            + "To implement the DISCARD protocol, the handler has to ignore the received message. ByteBuf is a reference-counted object which has to be released explicitly via the release() method. Please keep in mind that it is the handler's responsibility to release any reference-counted object passed to the handler. Usually, channelRead() handler method is implemented like the following:\n"
            + "\n"
            + "@Override\n"
            + "public void channelRead(ChannelHandlerContext ctx, Object msg) {\n"
            + "    try {\n"
            + "        // Do something with msg\n"
            + "    } finally {\n"
            + "        ReferenceCountUtil.release(msg);\n"
            + "    }\n"
            + "}\n"
            + "The exceptionCaught() event handler method is called with a Throwable when an exception was raised by Netty due to an I/O error or by a handler implementation due to the exception thrown while processing events. In most cases, the caught exception should be logged and its associated channel should be closed here, although the implementation of this method can be different depending on what you want to do to deal with an exceptional situation. For example, you might want to send a response message with an error code before closing the connection.\n"
            + "\n"
            + "So far so good. We have implemented the first half of the DISCARD server. What's left now is to write the main() method which starts the server with the DiscardServerHandler.\n"
            + "\n";

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
