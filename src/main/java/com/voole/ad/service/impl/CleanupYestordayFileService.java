package com.voole.ad.service.impl;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;

@Component("cleanupYestordayFileService")
public class CleanupYestordayFileService {

	private Logger logger = Logger.getLogger(CleanupYestordayFileService.class);
	
	public static SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
	public static final String HIVE = ".hive";// 每次处理占用文件,占用文件处理后变成的新文件，用于upload
	public static final String SPLITPATH1 = GlobalProperties.getProperties("splitPath1");
	public static final String SPLITPATH2 = GlobalProperties.getProperties("splitPath2");
	public static final String SPLITPATH3 = GlobalProperties.getProperties("splitPath3");
	public static final String SPLITPATH4 = GlobalProperties.getProperties("splitPath4");
	public static final String SPLITPATH5 = GlobalProperties.getProperties("splitPath5");
	public static final String SPLITPATH6 = GlobalProperties.getProperties("splitPath6");
	public static final String SPLITPATH7 = GlobalProperties.getProperties("splitPath7");
	public static final String SPLITPATH8 = GlobalProperties.getProperties("splitPath8");
	/**
	 * 每天半夜1点定时清理上一天的数据 数据名为 20150720
	 * 写在一起有问题 分开执行
	 */
	
	public void cleanupFile1(){
		logger.info("enter cleanupFile1 method start");
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH1 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			boolean flag = f.renameTo(new File(SPLITPATH1 + name + HIVE));
			if(flag){
				logger.info("cleanupFile1:success!" + "oname:" + f.getName() + "|fname:" + SPLITPATH1 + name + HIVE);
				//判断真的是重命名失败了
				File fexist = new File(SPLITPATH1 + dayBefore);
				if(fexist.exists()){
					logger.info("cleanupFile1:" + fexist.getName() + " exists error");
				}
			}else{
				logger.info("cleanupFile1:error!" + "oname:" + f.getName() + "|fname:" + SPLITPATH1 + name + HIVE);
			}
		}
		logger.info("enter cleanupFile1 method end");
	}
	
	public void cleanupFile2(){
		logger.info("enter cleanupFile2 method start");
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH2 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			boolean flag = f.renameTo(new File(SPLITPATH2 + name + HIVE));
			if(flag){
				logger.info("cleanupFile2:success!" + "oname:" + f.getName() + "|fname:" + SPLITPATH2 + name + HIVE);
				//判断真的是重命名失败了
				File fexist = new File(SPLITPATH2 + dayBefore);
				if(fexist.exists()){
					logger.info("cleanupFile2:" + fexist.getName() + " exists error");
				}
			}else{
				logger.info("cleanupFile2:error!" + "oname:" + f.getName() + "|fname:" + SPLITPATH2 + name + HIVE);
			}
		}
		logger.info("enter cleanupFile2 method end");
	}
	
	public void cleanupFile3(){
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH3 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			f.renameTo(new File(SPLITPATH3 + name + HIVE));
		}
	}
	
	public void cleanupFile4(){
		logger.info("enter cleanupFile4 method start");
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH4 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			boolean flag = f.renameTo(new File(SPLITPATH4 + name + HIVE));
			if(flag){
				logger.info("cleanupFile4:success!" + "oname:" + f.getName() + "|fname:" + SPLITPATH4 + name + HIVE);
				//判断真的是重命名失败了
				File fexist = new File(SPLITPATH4 + dayBefore);
				if(fexist.exists()){
					logger.info("cleanupFile4:" + fexist.getName() + " exists error");
				}
			}else{
				logger.info("cleanupFile4:error!" + "oname:" + f.getName() + "|fname:" + SPLITPATH4 + name + HIVE);
			}
		}
		logger.info("enter cleanupFile4 method end");
	}
	
	public void cleanupFile5(){
		logger.info("enter cleanupFile5 method start");
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH5 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			boolean flag = f.renameTo(new File(SPLITPATH5 + name + HIVE));
			if(flag){
				logger.info("cleanupFile5:success!" + "oname:" + f.getName() + "|fname:" + SPLITPATH5 + name + HIVE);
				//判断真的是重命名失败了
				File fexist = new File(SPLITPATH5 + dayBefore);
				if(fexist.exists()){
					logger.info("cleanupFile5:" + fexist.getName() + " exists error");
				}
			}else{
				logger.info("cleanupFile5:error!" + "oname:" + f.getName() + "|fname:" + SPLITPATH5 + name + HIVE);
			}
		}
		logger.info("enter cleanupFile5 method end");
	}
	
	public void cleanupFile6(){
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH6 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			f.renameTo(new File(SPLITPATH6 + name + HIVE));
		}
	}
	
	public void cleanupFile7(){
		logger.info("enter cleanupFile7 method start");
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH7 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			boolean flag = f.renameTo(new File(SPLITPATH7 + name + HIVE));
			if(flag){
				logger.info("cleanupFile7:success!" + "oname:" + f.getName() + "|fname:" + SPLITPATH7 + name + HIVE);
				//判断真的是重命名失败了
				File fexist = new File(SPLITPATH7 + dayBefore);
				if(fexist.exists()){
					logger.info("cleanupFile7:" + fexist.getName() + " exists error");
				}
			}else{
				logger.info("cleanupFile7:error!" + "oname:" + f.getName() + "|fname:" + SPLITPATH7 + name + HIVE);
			}
		}
		logger.info("enter cleanupFile7 method end");
	}
	
	public void cleanupFile8(){
		logger.info("enter cleanupFile8 method start");
		Calendar c = Calendar.getInstance();
        Date date= new Date();  
        c.setTime(date);  
        c.add(Calendar.DATE,-1);  
        String dayBefore=sdf.format(c.getTime());  
		File f = new File(SPLITPATH8 + dayBefore);
		if(f.exists()){
			String name = f.getName() +"_"+ System.currentTimeMillis();
			boolean flag = f.renameTo(new File(SPLITPATH8 + name + HIVE));
			if(flag){
				logger.info("cleanupFile8:success!" + "oname:" + f.getName() + "|fname:" + SPLITPATH8 + name + HIVE);
				//判断真的是重命名失败了
				File fexist = new File(SPLITPATH8 + dayBefore);
				if(fexist.exists()){
					logger.info("cleanupFile8:" + fexist.getName() + " exists error");
				}
			}else{
				logger.info("cleanupFile8:error!" + "oname:" + f.getName() + "|fname:" + SPLITPATH8 + name + HIVE);
			}
		}
		logger.info("enter cleanupFile8 method end");
	}
	
	
}
