package com.donwait.log;

import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.springframework.context.ApplicationContext;
import com.donwait.actionHandler.ImageActionHandler;
import com.donwait.model.Image;

public class ImgRecordHandler implements Runnable {
	//public static Map<String, Image> images = new ConcurrentHashMap<>();
	public static ImageActionHandler handler;
	public static List<Image> images = new LinkedList<Image>();
	public static Lock imgLock = new ReentrantLock(); 	
	
	@SuppressWarnings("static-access")
	public ImgRecordHandler(ApplicationContext ctx){
		this.handler = ctx.getBean(ImageActionHandler.class);
	}
	public static void addRecord(Image img){
		imgLock.lock();
		try {
			images.add(img);
		} finally {
			imgLock.unlock();
		}
	}
	
	@Override
	public void run() {
		while(true)
		{
			if(images.isEmpty()){
				try {
					Thread.sleep(20);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
				continue;
			}else{
				imgLock.lock();
				try {
					Image image = images.get(0);
					if(!ImgRecordHandler.handler.add(image)){
						System.out.println("图片入库失败：" + image.getUrl());
					}
					else{
						images.remove(0);
					}
				} finally {
					imgLock.unlock();
				}
			}
		}
	}
}
