package com.donwait.netty;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.CharsetUtil;
import io.netty.util.ReferenceCountUtil;

/**
 * 处理服务器通道
 * @author Administrator
 *
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter{
	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		// TODO Auto-generated method stub
		ByteBuf byteBuf=(ByteBuf) msg;
		try {
			/*while(byteBuf.isReadable()){
				System.out.println((char)byteBuf.readByte());
				System.out.flush();
			}*/
			String str = byteBuf.toString(CharsetUtil.UTF_8);
			System.out.println(str);			
		} catch (Exception e) {
			e.printStackTrace();
		}finally{
			//抛充数据,若服务器不用近回数据
			ReferenceCountUtil.release(msg);
		}
	}
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		// 出现异常时关闭连接
		cause.printStackTrace();
		ctx.close();
	}	
}
