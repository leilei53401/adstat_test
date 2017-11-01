package com.voole.ad.rescuedata;

import java.util.List;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

public class ExecFileRunable extends Thread {
	private org.slf4j.Logger logger = LoggerFactory.getLogger(ParseDataService.class);
	@Autowired
	private ParseDataService parseDataService;
	
	List<String> datas;
	
	public ExecFileRunable(){
	}
	
	
	public ExecFileRunable(List<String> datas){
		this.datas = datas;
	}

	@Override
	public void run() {
		logger.info("====线程["+this.getName()+"]开始处理数据======");
		parseDataService.parseDataList(datas);
		logger.info("====线程["+this.getName()+"]结束处理数据["+datas.size()+"]条======");
	}

}
