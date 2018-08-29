package com.donwait.netty;

import io.netty.bootstrap.ServerBootstrap;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * 服务器
 * @author Administrator
 *
 */
public class DiscardServer {
	private Integer port;
	public DiscardServer(Integer port) {
		this.port = port;
	}

	public void run() throws Exception {
		// 1.获取EventLoopGroup
		// bossGroup用来处理接收进来的连接
		EventLoopGroup bossGroup = new NioEventLoopGroup();
		// workerGroup用来处理已经接收进来的连接
		EventLoopGroup workerGroup = new NioEventLoopGroup();
		try {
			// 2.获取ServerBootstrap
			ServerBootstrap b = new ServerBootstrap();
			b.group(bossGroup, workerGroup)
			 .channel(NioServerSocketChannel.class)
			 .childHandler(new ChannelInitializer<SocketChannel>() {
						@Override
						protected void initChannel(SocketChannel ch) throws Exception {
							// 添加对信息的处理器
							ch.pipeline().addLast(new DiscardServerHandler());
						}
			})
			// 标识当服务器请求处理线程全满时，用于临时存放已完成三次握手的请求的队列的最大长度
			.option(ChannelOption.SO_BACKLOG, 128)
			// 是否使用tcp的保活机制
			.childOption(ChannelOption.SO_KEEPALIVE, true);
			//绑定端口,开始接收进来的连接
			ChannelFuture f = b.bind(port).sync();
			System.out.println("服务器"+this.hashCode()+"端口"+port+"启动成功!");
			//关闭服务器
			f.channel().closeFuture().sync();
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			workerGroup.shutdownGracefully();
			bossGroup.shutdownGracefully();
		}
	}
    public static void main(String[] args) throws Exception {
    	new DiscardServer(9000).run();      
    }
}
