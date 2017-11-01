package com.voole.ad.service.impl;


import java.text.ParseException;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Service;

import com.voole.ad.jms.AdPlayLogRTKakfaProducer;
import com.voole.ad.model.PlayLogBean;
import com.voole.ad.service.MonitorService;
import com.voole.ad.utils.AppContextAware;
import com.voole.ad.utils.GlobalProperties;

/**
 * 播放实时数据处理线程
 * @author Administrator
 *
 */
@Service
public class PlayLogRTDeelThread extends Thread{

	private Logger logger = Logger.getLogger(PlayLogRTDeelThread.class);
	private PlayLogQueue<PlayLogBean> playlogQueue;
	private AdPlayLogRTKakfaProducer kafkaProducer;
	private PlayLogThresholdService playlogService;
	private static final String sendAdapFlag = GlobalProperties.getProperties("sendAdapFlag");
	@Resource
	private MonitorService monitorService;
	
	public PlayLogRTDeelThread(){
		playlogQueue = new PlayLogQueue<PlayLogBean>();
		kafkaProducer = AppContextAware.getBean(AdPlayLogRTKakfaProducer.class);
		playlogService = AppContextAware.getBean(PlayLogThresholdService.class);
		this.start();
	}
	
	
	/**
	 * 播放数据放入队列
	 * 
	 * @param playlog
	 */
	public void put(PlayLogBean playlog) {
		playlogQueue.putQueue(playlog);
	}
	
	/**
	 * 数据处理
	 * @throws InterruptedException 
	 * @throws ParseException 
	 */
	public void handle() throws InterruptedException, ParseException {

		// 1.频次控制
		// 2.api对接
		PlayLogBean playlog = playlogQueue.getQueue();
		List<String> amidList = monitorService.getVaildAmid();
		String amid = playlog.getAmid() == null ? "0":playlog.getAmid().toString();
		if(sendAdapFlag.equals(playlog.getIsadap().trim()) && amidList.contains(amid) ){
			kafkaProducer.sendPlaylog(playlog);
		}
		playlogService.playlogThreshold(playlog);
	}
	
	/* (non-Javadoc)
	 * @see java.lang.Thread#run()
	 */
	public void run(){
		while(true){
			try {
				handle();
			} catch (Exception e) {
				logger.error("============= PlayLogRTDeelThread handle is exception,",e);
			}
		}
	}
}
