package com.voole.ad.rescuedata;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;
import com.voole.ad.ipcache.IpCacheDao;

@Service
public class DictCacheTools implements InitializingBean {
	private static Logger logger = Logger.getLogger(DictCacheTools.class);
	private final ReentrantReadWriteLock readWriteLock;
	private final Lock read;
	private final Lock write;

	//默认广协IP库缓存
	private  final RangeMap<Long, Integer[]> defaultIpRangeMap;
	
	@Autowired
	private IpCacheDao ipCacheDao;
	
	//频道和mtype对应字典表
	private HashMap<String,String> channelMtypeMap =  new HashMap<String,String>();
	//新老adpos对应关系
	private HashMap<String,String> adposMap =  new HashMap<String,String>();
		
		
	public DictCacheTools() {
		defaultIpRangeMap =  TreeRangeMap.create();;
		// lock
		readWriteLock = new ReentrantReadWriteLock();
		read = readWriteLock.readLock();
		write = readWriteLock.writeLock();
		
		//初始化频道字典表
		initChannelDic();
		//初始化导流位字典表
		initAdposMap();		
	}
	
	
	
	
	
	/**
	 * 将IP转化为十进制
	 * @param ip
	 * @return
	 */
	public  long ipToDecimal(String ip) {
		long ipDec = 0;
		if (ip != null) {
			String[] ipArr = ip.split("\\.");
			if (ipArr != null && ipArr.length == 4) {
				for (int i = 3; i >= 0; i--) {
					ipDec += (Long.valueOf(ipArr[i].trim()) * Math.pow(256, 3 - i));
				}
			}
		}
		return ipDec;
	}
	
	
	/**
	 * 缓存IP地址库
	 */
	public void doRefresh() {
		try {
			//缓存默认广协IP库
			RangeMap<Long, Integer[]> tmpDefaultIpRangeMap = ipCacheDao.loadDefaultIpAndArea();
			
			//更新缓存
			write.lock();
			this.defaultIpRangeMap.clear();
			this.defaultIpRangeMap.putAll(tmpDefaultIpRangeMap);
			
		} catch (Exception e) {
			logger.warn("ip database cache doRefresh error", e);
		} finally {
			write.unlock();
		}
	}
	
	
	@Override
	public void afterPropertiesSet() {
		logger.info("ip database cache init....");
		doRefresh();
		logger.info("ip database cache init end....");
	}
	
	/**
	 * 获取区域编码
	 * @param ip
	 * @return
	 */
	public Integer[] getAreaInfo(String ip) {
		Integer[] areaData = new Integer[2];
		long longIp = ipToDecimal(ip);
		read.lock();
		try {
			areaData = defaultIpRangeMap.get(longIp);
		} catch (Exception e) {
			logger.warn("get ip area error:", e);
		} finally {
			read.unlock();
		}
		return areaData;
	}
	//初始化频道字典表
	public void initChannelDic(){
	/*	100	0
		101	1
		102	8
		103	6
		104	10
		105	7
		106	11
		107	9
		108	28
		109	14
		110	29
		111	30*/

		channelMtypeMap.put("0",  "100");
		channelMtypeMap.put("1",  "101");
		channelMtypeMap.put("8",  "102");
		channelMtypeMap.put("6",  "103");
		channelMtypeMap.put("10", "104");
		channelMtypeMap.put("7",  "105");
		channelMtypeMap.put("11", "106");
		channelMtypeMap.put("9",  "107");
		channelMtypeMap.put("28", "108");
		channelMtypeMap.put("14", "109");
		channelMtypeMap.put("29", "110");
		channelMtypeMap.put("30", "111");
	}
	//频道字典表
	public String getChannelIdByMtype(String mtype) {
		String result = "";
		if(null!=channelMtypeMap.get(mtype)){
			result = channelMtypeMap.get(mtype);
		}
		return	 result;
	}
	
	
	//初始化导流位字典表表
	/*adapter.paster.pos = 15101010,15101011,17101110,17101310,17101210,17101410
	match.adapter.paster.pos = 701,702,704,705,706,707*/
	public void initAdposMap(){
		adposMap.put("701", "15101010");
		adposMap.put("702", "15101011");
		adposMap.put("704", "17101110");
		adposMap.put("705", "17101310");
		adposMap.put("706", "17101210");
		adposMap.put("707", "17101410");
	}
	//导流位字典
	public String getAdposNew(String old) {
		return	 adposMap.get(old);
	}
	
	public static void main(String[] args) {
		String ip = "123.125.114.144";
		DictCacheTools dt = new DictCacheTools();
		Integer[] areaCode = dt.getAreaInfo(ip);
		int provinceid = (areaCode != null ? (areaCode.length > 1 ? areaCode[0] : areaCode[0]) : 0);
		int cityid = (areaCode != null ? (areaCode.length > 1 ? areaCode[1] : areaCode[0]) : 0);
		System.out.println("provinceid=["+provinceid+"],cityid=["+cityid+"]");
	}
	
}
