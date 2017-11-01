package com.voole.ad.service.impl;

import java.io.File;
import java.io.FileFilter;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;
/**
 * 收集备份目录o和f目录下的数据到HDFS上，供以后进行数据量比较计算
 * @author jyc
 *
 */
@Component("dataCollectorService")
public class DataCollectorService{
	
	public static final String HDFSADDR = GlobalProperties.getProperties("hdfsAddr");
	public static final String HIVE_ADSTAT_ROOT = GlobalProperties.getProperties("hive_adstat_root");
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	public static final String LOCAL_TMP_FOLDER = GlobalProperties.getProperties("local_tmp_folder");
	public static final String O = "o";
	public static final String F = "f";
	//private Logger logMonitor = Logger.getLogger("adstatMonitor");
	private org.slf4j.Logger logMonitor = LoggerFactory.getLogger("adstatMonitor");
	public static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	
	public void monitorAdstat(){
		logMonitor.info("adstat is running");
	}
	/**
	 * create collector data and update data to HDFS to execute MR
	 */
	public void createCollectorData(){
		try{
			String[] path = {GlobalProperties.getProperties("collectorMonitorSh")};
			Process process = Runtime.getRuntime().exec(path);
			int result = process.waitFor();
			if(result == 0){
				Calendar calendar = Calendar.getInstance();
				calendar.add(Calendar.DAY_OF_YEAR, -1);
				final Date date = calendar.getTime();
				File[] f = new File(LOCAL_TMP_FOLDER).listFiles(new FileFilter() {
					@Override
					public boolean accept(File pathname) {
						if(pathname.getName().contains(SDF.format(date))){
							return true;
						}
						return false;
					}
				});
				System.setProperty("HADOOP_USER_NAME", "root");
				Configuration conf = new Configuration();
				conf.set("fs.defaultFS", HDFSADDR);
				String o_HDFS_BasePath_input = HIVE_ADSTAT_ROOT + O + "/" + SDF.format(date) + "/input/";
				String f_HDFS_BasePath_input = HIVE_ADSTAT_ROOT + F + "/" + SDF.format(date) + "/input/";
				String o_HDFS_Path_input = "";
				String f_HDFS_Path_input = "";
				
				FileSystem fs = FileSystem.get(conf);
				for(File tmpFile : f){
					String tmpName = tmpFile.getName();
					if(tmpName.contains(O)){
						fs.mkdirs(new Path(o_HDFS_BasePath_input));
						o_HDFS_Path_input = o_HDFS_BasePath_input + tmpName;
						fs.copyFromLocalFile(new Path(LOCAL_TMP_FOLDER + tmpName), new Path(o_HDFS_Path_input));
					}else{
						fs.mkdirs(new Path(f_HDFS_BasePath_input));
						f_HDFS_Path_input = f_HDFS_BasePath_input + tmpName;
						fs.copyFromLocalFile(new Path(LOCAL_TMP_FOLDER + tmpName), new Path(f_HDFS_Path_input));
					}
				}
				File deleteLocalFile = new File(LOCAL_TMP_FOLDER);
				File[] deleteFiles = deleteLocalFile.listFiles();
				for(File df : deleteFiles){
					df.delete();
				}
				//防止服务器之间传输时间有细微区别，在其他时间段进行汇总
			}
		}catch (Exception e) {
			//log.error("createCollectorData error:" + e.getMessage());
		}
	}
}
