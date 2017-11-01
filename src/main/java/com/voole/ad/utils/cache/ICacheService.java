package com.voole.ad.utils.cache;

import java.util.List;


public interface ICacheService {

	public void put(String cacheName, String key, Object obj) throws Exception;

	public Object get(String cacheName, String key) throws Exception;

	public Boolean remove(String cacheName, String key) throws Exception;
	
	public Boolean removeList(String cacheName, List<String> keys) throws Exception;

	public void removeAll(String cacheName) throws Exception;

	public Integer getSize(String cacheName) throws Exception;

	public Long getMemoryStoreSize(String cacheName, String key) throws Exception;

	public String[] getCacheNames() throws Exception;
	
	public List getKeys(String cacheName) throws Exception;
	
	public Object get(String cacheName,String key,String backupCacheName) throws Exception;
	
	public Boolean copyCacheToCache(String cacheName,String copyCacheName);
	
}
