package com.voole.ad.utils.cache;

import java.util.Date;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.danga.MemCached.MemCachedClient;

@Service
public class MemcacheUtils {

	private Logger logger = LoggerFactory.getLogger(MemcacheUtils.class);
	
	@Resource(name="memcachedClient")
	private MemCachedClient memClient;
	
	/**
	 * 添加key（会覆盖已存在的）
	 * @param key
	 * @param value
	 * @return
	 */
	public synchronized boolean set(String key, String value){
		return memClient.set(key, value);
	}
	
	/**
     * 添加具有过期时间的key（会覆盖已存在的）
     * @param key key
     * @param value value
     * @param date expired time
     * @return
     */
    public synchronized boolean set(String key, String value,Date date){
        return memClient.set(key, value, date);
    }
	/**
	 * 添加key（已存在key，无法添加）
	 * @param key
	 * @param value
	 * @return
	 */
	public  synchronized boolean add(String key, String value){
		return memClient.add(key, value);
	}
	
	
	/**
	 * 添加key（已存在key，无法添加）
	 * @param key
	 * @param value
	 * @return
	 */
	public  synchronized boolean add(String key, String value, Date date){
		return memClient.add(key, value, date);
	}
	
	/**
	 * 获取key值
	 * @param key
	 * @return
	 */
	public synchronized Object get(String key){
		return memClient.get(key);
	}
	
	
	/**
	 * @param key
	 * @return
	 */
	public synchronized long addorincr(String key){
		return memClient.addOrIncr(key);
	}
	
	/**
	 * 自增 + 1
	 * @param key
	 * @return
	 */
	public synchronized long incr(String key){
		return memClient.incr(key);
	}
	
	/**
	 * 自增 + {@code value}
	 * @param key
	 * @return
	 */
	public synchronized long incr(String key, long value){
		return memClient.incr(key, value);
	}
	
}
