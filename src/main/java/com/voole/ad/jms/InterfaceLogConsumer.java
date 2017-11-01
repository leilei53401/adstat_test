package com.voole.ad.jms;

import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.integration.channel.QueueChannel;
import org.springframework.messaging.Message;

public class InterfaceLogConsumer {
	
	private Logger logger =LoggerFactory.getLogger(InterfaceLogConsumer.class);
	
	private QueueChannel queueChannel;
	public void setQueueChannel(QueueChannel queueChannel) {
		this.queueChannel = queueChannel;
	}

	public void consumerLog() throws UnsupportedEncodingException{
		
		@SuppressWarnings("rawtypes")
		Message msg ;	
		while((msg = queueChannel.receive()) != null) {
			try{
				Map<String, Object> map = (Map<String, Object>)msg.getPayload();
				Set<Entry<String, Object>> set = map.entrySet();
				for (Map.Entry<String, Object> entry : set) {
					String topic = entry.getKey();
					ConcurrentHashMap<Integer,List<byte[]>> messages = (ConcurrentHashMap<Integer,List<byte[]>>)entry.getValue();
					Collection<List<byte[]>> values = messages.values();
					for (Iterator<List<byte[]>> iterator = values.iterator(); iterator.hasNext();) {
						List<byte[]> list = iterator.next();
						for (byte[] object : list) {
							String message = new String(object, "UTF-8");
							logger.info(message);
						}
					}
				}
			}catch(Exception ex){
				logger.info("===interfacelog consumer is exception,exception is" + ex.getMessage());
			}
		}
		
		logger.info("====interfacelog receive is interrupted====");
	}
}
