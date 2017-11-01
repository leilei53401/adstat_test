package com.voole.ad.jms;

import java.util.Properties;

import kafka.javaapi.producer.Producer;
import kafka.producer.ProducerConfig;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;


/**
 * kafka api 的生产者初始类
 * @author Administrator
 *
 */
@Component
public class KafkaProducer {

	private Logger logger = Logger.getLogger(KafkaProducer.class);
	private Producer<byte[], byte[]> producer;
	public KafkaProducer(){
	    this.init();
	}
	
	public void init() {
		try {
				Properties props = new Properties();
				props.put("metadata.broker.list", GlobalProperties.getProperties("api.metadata.broker.list"));
				props.put("request.required.acks",  GlobalProperties.getProperties("api.request.required.acks"));
				props.put("request.timeout.ms", GlobalProperties.getProperties("api.request.timeout.ms"));
				props.put("producer.type", GlobalProperties.getProperties("api.producer.type"));
				props.put("message.send.max.retries", GlobalProperties.getProperties("api.message.send.max.retries"));
				props.put("queue.buffering.max.ms", GlobalProperties.getProperties("api.queue.buffering.max.ms"));
				props.put("batch.num.messages", GlobalProperties.getProperties("api.batch.num.messages"));
				//props.put("serializer.class", GlobalProperties.getProperties("api.serializer.class"));
				//props.put("key.serializer.class", GlobalProperties.getProperties("api.key.serializer.class"));
				
				ProducerConfig config = new ProducerConfig(props);
				if (producer == null) {
				    producer = new Producer<byte[], byte[]>(config);
					logger.debug("Producer have been produced!");
				}
		} catch (Exception e) {
			logger.error(e);
			e.printStackTrace();
		}
	}


	public Producer<byte[], byte[]> getProducer() {
		return producer;
	}

	public void close() {
		if (producer != null) {
			producer.close();
			logger.debug("Producer have been closed!");
		}
	}
}
