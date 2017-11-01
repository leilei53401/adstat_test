package com.voole.ad.utils.cache;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;
/**
 * 跨机房复制工具类
 * @author shaoyl
 * 2016-08-25
 */
@Service
public class MemcacheTools {

	private Logger logger = LoggerFactory.getLogger(MemcacheTools.class);
	
	@Resource(name="memcachedClient")
	private MemCachedClient memClient;//天津机房
	
	@Resource(name="beijingMemcachedClient")
	private MemCachedClient beijingMemClient;//北京机房
		
	/**
	 * 添加key（会覆盖已存在的）
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized boolean copy2Beijing(String key){
		Object valueObj = memClient.get(key);
		return beijingMemClient.set(key, valueObj != null ? valueObj.toString() : "0");
	}
	
	/**
	 * 添加key（会覆盖已存在的）
	 * @param key
	 * @param value
	 * @param date
	 * @return
	 */
	public synchronized boolean copy2Beijing(String key, Date date){
		Object valueObj = memClient.get(key);
		return beijingMemClient.set(key, valueObj != null ? valueObj.toString() : "0", date);
	}
	
}
