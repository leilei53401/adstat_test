package com.voole.ad.service.impl;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

import org.apache.log4j.Logger;

import com.voole.ad.utils.GlobalProperties;

/**
 * 
 * 播放日志实时数据队列
 * 
 * @author Administrator
 *
 * @param <T>
 */
public class PlayLogQueue<T> {

	private Logger logger = Logger.getLogger(PlayLogQueue.class);
	private BlockingQueue<T> queue;
	private int queue_max = GlobalProperties.getInteger("playlog.rt.queue.max");
	private int clearcnt = GlobalProperties.getInteger("playlog.rt.queue.clear");
	
	public PlayLogQueue(){
		queue = new ArrayBlockingQueue<T>(queue_max);
	}
	
	/**
	 * 数据添加到队列
	 * @param t
	 */
	public boolean putQueue(T t){
		if(t == null){
			//logger.warn("===========PlayLogQueue putQueue data is null");
			return false;
		}
		this.checkQueueFull();//检查队列，满载时清除
		return queue.add(t);
	}
	
	/**
	 * 检查队列是否已达到上限
	 * @return
	 */
	public synchronized boolean  checkQueueFull(){
		if(queue.size() >= queue_max){
			for(int i = 0; i < clearcnt;i++){
				logger.info("====================PlayLogQueue is full," + queue.poll());
				queue.poll();
			}
		}
		return true;
	}
	
	/**
	 * 拉取队列数据，队列空时阻塞等待
	 * @return
	 * @throws InterruptedException
	 */
	public T getQueue() throws InterruptedException{
		return queue.take();
	}
	
}
