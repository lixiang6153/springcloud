package com.donwait.kafka;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import java.util.Properties;
import java.util.UUID;

public class MyKafkaProducer{
	private Properties props;
	public MyKafkaProducer(String clientId,String hostAndPort) {
        props = new Properties();
        if(clientId!=null&&!clientId.trim().equals("")){
        	 props.put("client.id", clientId);//标识消费者组
        }
        props.put("bootstrap.servers", hostAndPort);
        props.put("acks", "all");//判断请求是否完整的条件,all会将消息阻塞，性能低但可靠
        props.put("retries", 0);//如果发送失败，则生产者自动重试
        props.put("batch.size", 16384);//缓冲区大小
        props.put("linger.ms", 1);
        props.put("buffer.memory", 33554432);//生产者可用于缓冲的存储器的总量。
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
       // props.put("partitioner.class", "com.donwait.kafka.DTSKafkaPartitioner");//如果需要分区
        
	}
	public void send(String topicName,String message) throws InterruptedException{
		String key = UUID.randomUUID().toString();
		Producer<String, String> producer = new KafkaProducer<String,String>(props);
        // 构建消息体,异步发送请求
		 producer.send(new ProducerRecord<String, String>(topicName, key, message));
       //发送到指定分区
       //producer.send(new ProducerRecord<String, String>("my-topic-1", 2, key, str));
	   producer.close();
	}
    public static void main(String[] args) throws InterruptedException{
    	MyKafkaProducer dtsKafkaProducer=new MyKafkaProducer(null,"127.0.0.1:9092");
    	dtsKafkaProducer.send("my-topic1","我不喜欢你");
    	dtsKafkaProducer.send("my-topic2","你是老板");
    	dtsKafkaProducer.send("my-topic3","我是BOSS");
    	
    }
}
