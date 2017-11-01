package com.voole.ad.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileStatus;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IOUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;
/**
 * run MR jar must after DataCollectorService
 * @author Administrator
 * fatjar's jar name must be ad_o.jar and ad_f.jar and create different main class in META-INF add 
 * Main-Class: com.voole.FMR or com.voole.OMR
 * *** must run on on machine others not run ****
 */
@Component("dataAnalyzeService")
public class DataAnalyzeService {
	
	/*public void createAnalyzeData() throws IOException{
		Runtime.getRuntime().exec("java -jar ad_o.jar");
		Runtime.getRuntime().exec("java -jar ad_f.jar");
	}*/
	
	public static final String HDFSADDR = GlobalProperties.getProperties("hdfsAddr");
	public static final String HIVE_ADSTAT_ROOT = GlobalProperties.getProperties("hive_adstat_root");
	public static final SimpleDateFormat SDF = new SimpleDateFormat("yyyyMMdd");
	public static final String LOCAL_TMP_FOLDER = GlobalProperties.getProperties("local_tmp_folder");
	public static final String O = "o";
	public static final String F = "f";
	private Logger log = Logger.getLogger(DataCollectorService.class);
	
	public void createAnalyzeData() throws Exception{

		try{
			Calendar calendar = Calendar.getInstance();
			calendar.add(Calendar.DAY_OF_YEAR, -1);
			final Date date = calendar.getTime();
			System.setProperty("HADOOP_USER_NAME", "root");
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", HDFSADDR);
			String o_HDFS_BasePath_input = HIVE_ADSTAT_ROOT + O + "/" + SDF.format(date) + "/input/";
			String o_HDFS_BasePath_output = HIVE_ADSTAT_ROOT + O + "/" + SDF.format(date) + "/output/";
			String f_HDFS_BasePath_input = HIVE_ADSTAT_ROOT + F + "/" + SDF.format(date) + "/input/";
			String f_HDFS_BasePath_output = HIVE_ADSTAT_ROOT + F + "/" + SDF.format(date) + "/output/";
			FileSystem fs = FileSystem.get(conf);
			{
				InputStream in_o = null;
				FileWriter out_o = null;
				BufferedReader buff_o = null;
				Map<String,Integer> map_o = new HashMap<String,Integer>();
				fs.delete(new Path(o_HDFS_BasePath_output), true);
				fs.mkdirs(new Path(o_HDFS_BasePath_output));
				FileStatus[] status = fs.listStatus(new Path(o_HDFS_BasePath_input));
				//目前两台服务器,每个日期下有两个文件
				for(FileStatus fss : status){
					Path p = fss.getPath();
					in_o = fs.open(p);
					buff_o = new BufferedReader(new InputStreamReader(in_o));
					String str = null;
					while((str = buff_o.readLine()) != null){
						String[] strArr = str.split(",");
						String dateSplit = strArr[0];
						String bigtypeSplit = strArr[1];
						Integer countSplit = Integer.valueOf(strArr[2]);
						Integer key = map_o.get(dateSplit + "," + bigtypeSplit);
						if(key == null){
							map_o.put(dateSplit + "," + bigtypeSplit, countSplit);
						}else{
							map_o.put(dateSplit + "," + bigtypeSplit, key + countSplit);
						}
					}
					IOUtils.closeStream(in_o);
				}
				//写入文件o到本地目录
				String tmp_o_name = System.currentTimeMillis() + ".txt";
				out_o = new FileWriter(LOCAL_TMP_FOLDER + tmp_o_name);
				Set<String> keySet_o = map_o.keySet();
				for(String key : keySet_o){
					Integer count = map_o.get(key);
					out_o.write(key + "," + count + "\r\n");
				}
				IOUtils.closeStream(out_o);
				//上传hive目录
				fs.copyFromLocalFile(new Path(LOCAL_TMP_FOLDER + tmp_o_name), new Path(o_HDFS_BasePath_output + tmp_o_name));
				//删除本地临时文件
				FileUtils.deleteQuietly(new File(LOCAL_TMP_FOLDER + tmp_o_name));
			}
			
			{
				InputStream in_f = null;
				FileWriter out_f = null;
				BufferedReader buff_f = null;
				Map<String,Integer> map_f = new HashMap<String,Integer>();
				fs.delete(new Path(f_HDFS_BasePath_output), true);
				fs.mkdirs(new Path(f_HDFS_BasePath_output));
				FileStatus[] status = fs.listStatus(new Path(f_HDFS_BasePath_input));
				//目前两台服务器,每个日期下有两个文件
				for(FileStatus fss : status){
					Path p = fss.getPath();
					in_f = fs.open(p);
					buff_f = new BufferedReader(new InputStreamReader(in_f));
					String str = null;
					while((str = buff_f.readLine()) != null){
						String[] strArr = str.split(",");
						String dateSplit = strArr[0];
						String bigtypeSplit = strArr[1];
						Integer countSplit = Integer.valueOf(strArr[2]);
						Integer key = map_f.get(dateSplit + "," + bigtypeSplit);
						if(key == null){
							map_f.put(dateSplit + "," + bigtypeSplit, countSplit);
						}else{
							map_f.put(dateSplit + "," + bigtypeSplit, key + countSplit);
						}
					}
					IOUtils.closeStream(in_f);
				}
				//写入文件f到本地目录
				String tmp_f_name = System.currentTimeMillis() + ".txt";
				out_f = new FileWriter(LOCAL_TMP_FOLDER + tmp_f_name);
				Set<String> keySet_f = map_f.keySet();
				for(String key : keySet_f){
					Integer count = map_f.get(key);
					out_f.write(key + "," + count + "\r\n");
				}
				IOUtils.closeStream(out_f);
				//上传hive目录
				fs.copyFromLocalFile(new Path(LOCAL_TMP_FOLDER + tmp_f_name), new Path(f_HDFS_BasePath_output + tmp_f_name));
				//删除本地临时文件
				FileUtils.deleteQuietly(new File(LOCAL_TMP_FOLDER + tmp_f_name));
			}
			
		}catch (Exception e) {
			log.error("createCollectorData error:" + e.getMessage());
		}
	
	}

	
}
