package com.donwait.netty;

import java.util.Iterator;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Stream;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

// ChannelHandler被添加到一个ChannelPipeline
public class EchoServerHandler extends ChannelInboundHandlerAdapter {
	private static Map<Integer, ChannelHandlerContext> clients = new ConcurrentHashMap<Integer, ChannelHandlerContext>();

	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		cause.printStackTrace();
		ctx.close();
	}

	// Channel中读数据时被调用
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		System.out.println(ctx.hashCode() + ":" + msg);
		Iterator<Integer> iterator = clients.keySet().iterator();
		while (iterator.hasNext()) {
			Integer key = iterator.next();
			ChannelHandlerContext channelHandlerContext = clients.get(key);
			channelHandlerContext.writeAndFlush(NettyUtil.appenEndOfLine(ctx.hashCode() + "：", (String) msg));
		}
	}

	// 当Channel的可写状态改变时被调用。通过这个方法，用户可以确保写操作不会进行地太快
	// 避免OutOfMemoryError-或者当Channel又变成可写时继续写操作
	@Override
	public void channelWritabilityChanged(ChannelHandlerContext ctx) throws Exception {
		// TODO Auto-generated method stub
		super.channelWritabilityChanged(ctx);
	}

	// 某个读操作完成时被调用
	@Override
	public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {

	}

	// 连接到某个远端，可以收发数据
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		clients.put(ctx.hashCode(), ctx);
		System.out.println(ctx.hashCode() + "连上服务器...");
	}
}

class NettyUtil {
	public static final String END_OF_LINE = "\n";

	public static String appenEndOfLine(String... message) {
		StringBuilder stringBuilder = new StringBuilder(256);
		Stream.of(message).forEachOrdered(stringBuilder::append);
		stringBuilder.append(END_OF_LINE);
		return stringBuilder.toString();
	}
}
