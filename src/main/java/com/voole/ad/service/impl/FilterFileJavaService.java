package com.voole.ad.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;

/**
 * @author jyc
 * 每个方法处理不同type目录下的文件
 * update jyc java版本 shell脚本有bug
 */
@Component("filterFileJavaService")
public class FilterFileJavaService {

	public static Log log = LogFactory.getLog(FilterFileJavaService.class);
	
	public static final String ROOT_SPLIT = "/opt/webapps/split/";
	public static final String HIVE = ".hive";
	public static final String O = ".o";
	public static final String F = ".f";

	/**
	 * 处理hive要的数据
	 * 处理类型 ：  20150720_1437377341757.hive
	 * 处理后文件类型 ： 20150720_1437377341757.hive.f 同时删除20150720_1437377341757.hive
	 * 处理方式 ： 脚本处理
	 * 传递参数 : 
	 * 后置条件 ： uploadFileByTypeOne()
	 */
	public void filterFileSh1(){
		filter("1");
	}
	public void filterFileSh2(){
		filter("2");
	}
	public void filterFileSh3(){
		filter("3");
	}
	public void filterFileSh4(){
		filter("4");
	}
	public void filterFileSh5(){
		filter("5");
	}
	public void filterFileSh6(){
		filter("6");
	}
	public void filterFileSh7(){
		filter("7");
	}
	public void filterFileSh8(){
		filter("8");
	}
	
	private void filter(String type) {
		try {
			String path = ROOT_SPLIT + type + File.separator;
			File f = new File(path);
			File[] listFiles = f.listFiles(new FilenameFilter() {
				@Override
				public boolean accept(File dir, String name) {
					if(name.endsWith(HIVE)){
						return true;
					}
					return false;
				}
			});
			for(File file : listFiles){
				String filename = file.getName();
				BufferedReader reader = new BufferedReader(new FileReader(file));
				Map<String,String> map = new HashMap<String,String>();
				String line = null;
				while((line = reader.readLine()) != null){
					String[] lineArr = line.split(",");
					String sessionid = lineArr[1];
					String hid = lineArr[4];
					String adposid = lineArr[9];
					String amid = lineArr[10];
					String key = sessionid + "_" + hid + "_" + adposid + "_" + amid;
					String value = map.get(key);
					if(value == null){
						//过滤数据
						map.put(key,line);
					}
				}
				Collection<String> values = map.values();
				List<String> valueList = new ArrayList<String>(values);
				if(valueList != null && valueList.size() > 0){
					File writeToFile = new File(path + filename + O);
					writeToFile.createNewFile();
					BufferedWriter bw = new BufferedWriter(new FileWriter(writeToFile));
					for(String value : valueList){
						bw.write(value);
						bw.newLine();
					}
					bw.flush();
					bw.close();
				}
				reader.close();
				//update jyc 2016-03-10 filter file service backup
				File origalFile = new File(path + filename);
				String backupFilterDate = filename.substring(0,8);
				File backupFolder_input = new File("/opt/webapps/backup/filterFileService/input/" + backupFilterDate + File.separator + type);
				File backupFolder_output = new File("/opt/webapps/backup/filterFileService/output/" + backupFilterDate + File.separator + type);
				if (!backupFolder_input.exists()) {
					backupFolder_input.mkdirs();
				}
				if (!backupFolder_output.exists()) {
					backupFolder_output.mkdirs();
				}
				try{
					FileUtils.copyFileToDirectory(origalFile,backupFolder_input);
				}catch(Exception e1){
					log.info(e1.getMessage());
				}
				origalFile.delete();
				//最终写入的文件,先进行备份，然后再重命名
				File oFile = new File(path + filename + O);
				try{
					FileUtils.copyFileToDirectory(oFile,backupFolder_output);
				}catch(Exception e1){
					log.info(e1.getMessage());
				}
				oFile.renameTo(new File(path + filename + F));
			}
		} catch (Exception e) {
			log.error("filterFile error:" + e.getMessage());
		}
	}
	
}
