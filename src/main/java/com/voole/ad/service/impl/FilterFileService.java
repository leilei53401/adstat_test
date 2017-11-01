package com.voole.ad.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;

/**
 * @author Administrator
 * 每个方法处理不同type目录下的文件
 */
@Component("filterFileService")
public class FilterFileService {

	public static Log log = LogFactory.getLog(FilterFileService.class);

	/**
	 * 处理hive要的数据
	 * 处理类型 ：  20150720_1437377341757.hive
	 * 处理后文件类型 ： 20150720_1437377341757.hive.f 同时删除20150720_1437377341757.hive
	 * 处理方式 ： 脚本处理
	 * 传递参数 : 
	 * 后置条件 ： uploadFileByTypeOne()
	 */
	public void filterFileSh1(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"1"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh2(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"2"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh3(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"3"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh4(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"4"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh5(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"5"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh6(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"6"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh7(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"7"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	public void filterFileSh8(){
		try {
			String[] path = {GlobalProperties.getProperties("filterFileSh"),"8"};
			Runtime.getRuntime().exec(path);
		} catch (java.io.IOException e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
}
