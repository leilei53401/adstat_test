package com.voole.ad.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import com.voole.ad.dao.AdapProgramDao;
import com.voole.ad.service.MonitorService;
import com.voole.ad.utils.cache.ICacheService;

@Service
public class MonitorServiceImpl implements MonitorService {
	
	private Logger logger = LoggerFactory.getLogger(MonitorServiceImpl.class);
	@Resource
	private ICacheService cacheService;
	private Object validAmidList = null;
	@Resource
	private AdapProgramDao adapProgramDao;
	@Override
	public List<String> getVaildAmid() {
		// TODO Auto-generated method stub
		//1、首先从缓存拿
		try {
			validAmidList = cacheService.get("valid_amid", "key_valid_amid");
		} catch (Exception e) {
			// TODO Auto-generated catch block
			logger.error("get amid from ehcache error "+e);
		}
		
		List<String> amids = null;
		//2、缓存有 直接解析
		if(validAmidList != null){
			amids = (List<String>) validAmidList;
		}else{
 		//3、缓存没有则查库，并放入缓存	
			try {
				amids = adapProgramDao.getValidAmidList();
				cacheService.put("valid_amid", "key_valid_amid", validAmidList);
				
			} catch (Exception e) {
				// TODO Auto-generated catch block
				logger.error("put ammid to ehcache error "+ e);
			}
		}
		return amids;
	}

}
