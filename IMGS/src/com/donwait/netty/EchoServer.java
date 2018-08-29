package com.donwait.netty;

import java.nio.charset.StandardCharsets;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.codec.LineBasedFrameDecoder;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;

public class EchoServer {
	 private final int port;  
	  
	    public EchoServer() {  
	        this(8989);  
	    }  
	  
	    public EchoServer(int port) {  
	        this.port = port;  
	    }  

	    public void start() throws InterruptedException {
	    	// 配置服务端的NIO线程组
	        ServerBootstrap bootstrap = new ServerBootstrap();  
	        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1);  
	        NioEventLoopGroup workerGroup = new NioEventLoopGroup();  
	  
	        try {
	        	// 设置线程组及Socket参数
	            bootstrap.group(bossGroup, workerGroup)  				
	                    .channel(NioServerSocketChannel.class)  
	                    .localAddress(port)  
	                    .childHandler(new ChannelInitializer<SocketChannel>() {	  
	                        @Override  
	                        protected void initChannel(SocketChannel ch) throws Exception { 
	                        	// 以下2行代码为了解决半包读问题  
	                            ch.pipeline().addLast(new LineBasedFrameDecoder(2048));  
	                            ch.pipeline().addLast(new StringDecoder(StandardCharsets.UTF_8));  
	                            ch.pipeline().addLast(new StringEncoder(StandardCharsets.UTF_8));  
	                            ch.pipeline().addLast(new EchoServerHandler());  
	                        }  
	                    });  
	            // 绑定端口，同步等待成功  
	            ChannelFuture channelFuture = bootstrap.bind().sync();  
	            System.out.println(EchoServer.class.getName() + " started and listen on port:" + channelFuture.channel().localAddress());  
	            channelFuture.channel().closeFuture().sync();  
	        } finally {
	        	// 退出释放线程池资源  
	            workerGroup.shutdownGracefully();  
	            bossGroup.shutdownGracefully();  
	        }  
	    }  

    public static void main(String[] args) throws Exception {
    	// 启动
        new EchoServer(9004).start(); 
    }
    
}
