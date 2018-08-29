package com.donwait.kafka;

import java.lang.Thread.UncaughtExceptionHandler;
import java.util.HashMap;
import java.util.Map;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.KStreamBuilder;

/**
 * 传递信息从producer->topic中间过程中处理的处理,可以转发至其他topic
 * @author Administrator
 *
 */
public class MyKafkaStreams {
	public static void main(String[] args) {
		Map<String, Object> props = new HashMap<>();
	    props.put(StreamsConfig.APPLICATION_ID_CONFIG, "my-stream-processing-application");
	    props.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
	    props.put(StreamsConfig.KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    props.put(StreamsConfig.VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass().getName());
	    StreamsConfig config = new StreamsConfig(props);
	    KStreamBuilder builder = new KStreamBuilder();

	    builder.stream("my-topic1").foreach((k,v)->{//处理过程
	    	System.out.println(k+"----"+v);
	    	
	    	
	    	
	    });
	    
	    KafkaStreams streams = new KafkaStreams(builder, config);
	    streams.start();
	    streams.setUncaughtExceptionHandler(new UncaughtExceptionHandler() {
			
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				e.printStackTrace();	
			}
		});

	    
	}
}
