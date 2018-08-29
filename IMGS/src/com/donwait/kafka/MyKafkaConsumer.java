package com.donwait.kafka;

import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.errors.WakeupException;
import java.util.Arrays;
import java.util.Properties;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Consumer;

public class MyKafkaConsumer implements Runnable{
    private final AtomicBoolean closed = new AtomicBoolean(false);
	private final KafkaConsumer<String,String> consumer;
	private Consumer<ConsumerRecords<String,String>> recordsHandler;
	/**
	 * 
	 * @param hostsAndPorts ip和端口
	 * @param groupId 消费者所在的组,可为空
	 * @param recordsHandler 对消息的处理
	 * @param topics 订阅的主题
	 */
	public MyKafkaConsumer(String hostsAndPorts,String groupId,Consumer<ConsumerRecords<String,String>> recordsHandler,String[] topics) {
		this.recordsHandler=recordsHandler;
		Properties props = new Properties();
		props.put("bootstrap.servers", hostsAndPorts);
		//设置group.id
		props.put("group.id", groupId);
		//设置自动提交偏移量
		props.put("enable.auto.commit", "true");
		//设置自动提交偏移量频率
		props.put("auto.commit.interval.ms", "1000");
		//设置使用最开始的offset偏移量为该group.id的最早。如果不设置，则会是latest即该topic最新一个消息的offset
		//如果采用latest，消费者只能得道其启动后，生产者生产的消息
		props.put("auto.offset.reset", "earliest");
		//设置心跳时间
		props.put("session.timeout.ms", "30000");
		//设置key以及value的解析（反序列）类
		props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
		
		consumer = new KafkaConsumer<String,String>(props);
		//不指定分区订阅topic
		consumer.subscribe(Arrays.asList(topics));
			
//		指定分区消费消息
//		TopicPartition partition1 = new TopicPartition("my-topic1",2);
//	    consumer.assign(Arrays.asList(partition1));
	}
	@Override
	public void run() {

		while (true) {
			 try {
	             while (!closed.get()) {
	                 ConsumerRecords<String, String> records = consumer.poll(10000);
	                 recordsHandler.accept(records);              
	             }
	         } catch (WakeupException e) {
	             // Ignore exception if closing
	             if (!closed.get()) throw e;
	         } finally {
	             consumer.close();
	         }
		}
		
	}
	 // Shutdown hook which can be called from a separate thread
    public void shutdown() {
        closed.set(true);
        consumer.wakeup();
    }
    
    
    public static void main(String[] args) {
    	String[] topics={"my-topic1","my-topic2","my-topic3"};
		MyKafkaConsumer consumer=new MyKafkaConsumer("127.0.0.1:9092", "groups1",(records)->{
			records.forEach((record)->{
				System.out.println(record.offset() + ": " + record.value()+":"+record.partition()+"区"+":topic--"+record.topic());
			});
		},topics);
		new java.lang.Thread(consumer).start();
	}
}
