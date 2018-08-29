package com.donwait.netty;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
public class EchoClient {
    private final String host;
    private final int port;

    public EchoClient() {
        this(0);
    }

    public EchoClient(int port) {
        this("localhost", port);
    }

    public EchoClient(String host, int port) {
        this.host = host;
        this.port = port;
    }

    public void start() throws Exception {
    	NioEventLoopGroup workersGroup = new NioEventLoopGroup(1);  
    	  
        try {  
            Bootstrap bootstrap = new Bootstrap();  
            bootstrap.group(workersGroup)  
                    .channel(NioSocketChannel.class)  
                    .remoteAddress(host, port)  
                    .handler(new ChannelInitializer<SocketChannel>() {  
                        @Override  
                        protected void initChannel(SocketChannel ch) throws Exception { 
                        	// 以下两行代码为了解决半包读问题 
                            ch.pipeline().addLast(new LineBasedFrameDecoder(2048));  
                            ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));  
                            ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));  
                            ch.pipeline().addLast(new EchoClientHandler());  
                        }  
                    });  
            // 发起异步连接操作  
            ChannelFuture channelFuture = bootstrap.connect().sync(); 
            // 等待链路关闭  
            channelFuture.channel().closeFuture().sync();  
        } finally {
        	// 退出，释放NIO线程组 
            workersGroup.shutdownGracefully();  
        }  
    }

    public static void main(String[] args) throws Exception {
    	// 连接127.0.0.1/65535，并启动
        new EchoClient("127.0.0.1", 9004).start(); 
    }
}
