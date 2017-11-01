package com.voole.ad.utils.cache;

import java.util.List;

import javax.annotation.Resource;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;

import org.springframework.cache.ehcache.EhCacheCacheManager;
import org.springframework.stereotype.Service;

@Service
public class CacheServiceImpl implements ICacheService {
	
	@Resource
	private EhCacheCacheManager cacheManager;

	protected CacheManager getCacheManager() throws Exception {
		return cacheManager.getCacheManager();
	}

	public  Cache getCache(String cacheName) throws Exception {
		CacheManager c = this.getCacheManager();
		if (c != null) {
			return c.getCache(cacheName);
		}
		return null;
	}
	
	

	public void put(String cacheName, String key, Object obj) throws Exception {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			Element element = new Element(key, obj);
			cache.put(element);
		}
	}

	public synchronized Object get(String cacheName, String key) throws Exception {
		Object obj = null;
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			Element element = cache.get(key);
			if (element != null)
				obj = element.getObjectValue();
		}
		return obj;
	}

	public synchronized Boolean remove(String cacheName, String key) throws Exception {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			return cache.remove(key);
		}
		return false;
	}

	public void removeAll(String cacheName) throws Exception {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			cache.removeAll();
		}
	}

	public Integer getSize(String cacheName) throws Exception {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			return cache.getSize();
		}
		return 0;
	}

	public Long getMemoryStoreSize(String cacheName, String key) throws Exception {
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			return cache.getMemoryStoreSize();
		}
		return 0L;
	}

	public String[] getCacheNames() throws Exception {
		CacheManager c = this.getCacheManager();
		if (c != null) {
			return this.getCacheManager().getCacheNames();
		}
		return null;
	}
	
	public synchronized List<String> getKeys(String cacheName) throws Exception {
		List<String> list = null;
		Cache cache = this.getCache(cacheName);
		if (null != cache) {
			list = cache.getKeys();
		}
		return list;
	}

	/**
	 * 批量删除key
	 */
	@Override
	public Boolean removeList(String cacheName, List<String> keys)
			throws Exception {
		for(String key:keys){
			remove(cacheName,key);
		}
		return null;
	}

	/**
	 * 当前cache取不到，取备份cache里的
	 */
	@Override
	public Object get(String cacheName, String key, String backupCacheName) throws Exception {
		Object obj = null;
		obj = get(cacheName,key);
		if(null==obj){
			obj = get(backupCacheName,key);
		}
		return obj;
	}

	/**
	 * 拷贝所有缓存到另一个缓存块中
	 */
	@Override
	public Boolean copyCacheToCache(String cacheName, String copyCacheName) {
		// TODO Auto-generated method stub
		
		try {
			removeAll(copyCacheName);
			List<String> keys = getKeys(cacheName);
			for(String key:keys){
				put(copyCacheName,key,get(cacheName,key));
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}
		return true;
	}
}
