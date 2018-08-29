package com.donwait.netty;

import java.util.Scanner;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;

public class EchoClientHandler extends ChannelInboundHandlerAdapter{

	 @Override  
     public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {  
         cause.printStackTrace();  
         ctx.close();  
     }  
	 
     @Override  
     public void channelActive(ChannelHandlerContext ctx) throws Exception {  
    	 // 连接成功....
         new Thread(new Hander(ctx)).start();  
     }  

     @Override  
     public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {  
         try {  
             System.out.println(msg);  
         } finally {  
             // 读完消息记得释放，那写消息为什么不这样操作呢，因为写完消息netty自动释放。  
             // 其操作见：DefaultChannelHandlerInvoker L331-332,不过有这个注释-> promise cancelled  
             // 是不少netty5正式发布的时候会取消呢。  
             // 我们可以使用SimpleChannelInboundHandler作为父类，因为释放操作已实现。  
             ReferenceCountUtil.release(msg);  
         }
     }  

     private static class Hander implements Runnable {  
         private ChannelHandlerContext ctx = null;  
         private Scanner scanner = new Scanner(System.in);  
   
         public Hander(ChannelHandlerContext ctx) {  
             this.ctx = ctx;  
         }  
   
         @Override  
         public void run() {  
             while (true) {  
                 String nextLine = scanner.nextLine();  
                 ctx.writeAndFlush(NettyUtil.appenEndOfLine(nextLine));  
             }
         }  
     }  
}
