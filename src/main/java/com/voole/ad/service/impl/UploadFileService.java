package com.voole.ad.service.impl;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

import org.apache.commons.io.FileUtils;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import com.voole.ad.utils.GlobalProperties;

@Component("uploadFileService")
public class UploadFileService {

	private Logger logger = Logger.getLogger(UploadFileService.class);

	public static final String SPLITPATH1 = GlobalProperties.getProperties("splitPath1");
	public static final String SPLITPATH2 = GlobalProperties.getProperties("splitPath2");
	public static final String SPLITPATH3 = GlobalProperties.getProperties("splitPath3");
	public static final String SPLITPATH4 = GlobalProperties.getProperties("splitPath4");
	public static final String SPLITPATH5 = GlobalProperties.getProperties("splitPath5");
	public static final String SPLITPATH6 = GlobalProperties.getProperties("splitPath6");
	public static final String SPLITPATH7 = GlobalProperties.getProperties("splitPath7");
	public static final String SPLITPATH8 = GlobalProperties.getProperties("splitPath8");
	public static final String BACKUPFSPLITFOLDER = GlobalProperties.getProperties("backupFSplitFolder");

	public static final String HIVEFINISH = ".hive.f";// 每次处理占用文件,占用文件处理后变成的新文件，用于upload
	public static final int HIVEUPLOADNUM = GlobalProperties.getInteger("hiveuploadnum");
	public static final String HDFSADDR = GlobalProperties.getProperties("hdfsAddr");
	public static final String HIVE_ADSTAT_ROOT = GlobalProperties.getProperties("hive_adstat_root");

	@Autowired
	public JdbcTemplate hiveJt;

	public JdbcTemplate getHiveJt() {
		return hiveJt;
	}

	public void setHiveJt(JdbcTemplate hiveJt) {
		this.hiveJt = hiveJt;
	}

	/**
	 * 处理这样的处理 : 20150720_1437377341757.hive.f 处理后文件类型 : 处理上传hive后删除
	 * 20150720_1437377341757.hive.f 前置条件 ： filterFileByTypeOne() 每十分钟处理一批
	 */
	public void uploadFile1() {
		File f = new File(SPLITPATH1);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH1);
			}
		}
	}

	public void uploadFile2() {
		File f = new File(SPLITPATH2);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH2);
			}
		}
	}

	public void uploadFile3() {
		File f = new File(SPLITPATH3);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH3);
			}
		}
	}

	public void uploadFile4() {
		File f = new File(SPLITPATH4);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH4);
			}
		}
	}

	public void uploadFile5() {
		File f = new File(SPLITPATH5);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH5);
			}
		}
	}

	public void uploadFile6() {
		File f = new File(SPLITPATH6);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH6);
			}
		}
	}

	public void uploadFile7() {
		File f = new File(SPLITPATH7);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH7);
			}
		}
	}

	public void uploadFile8() {
		File f = new File(SPLITPATH8);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.endsWith(HIVEFINISH)) {
					return true;
				}
				return false;
			}
		});
		String[] processFiles = null;
		// 保证每次最多处理5个文件 前提条件有文件需要处理
		if (files != null && files.length > 0) {
			if (files.length > HIVEUPLOADNUM) {
				processFiles = new String[HIVEUPLOADNUM];
				for (int i = 0; i < HIVEUPLOADNUM; i++) {
					processFiles[i] = files[i];
				}
			} else {
				processFiles = new String[files.length];
				for (int i = 0; i < files.length; i++) {
					processFiles[i] = files[i];
				}
			}
			for (String file : processFiles) {
				// update jyc 20151125 add backup partition
				uploadDelete(file,SPLITPATH8);
			}
		}
	}

	private void uploadDelete(String file,String splitPath) {
		String bigtype_partition = splitPath.substring(splitPath.length() - 2, splitPath.length() - 1);
		String date_partition = file.substring(0, 8);
		loadToHiveMachine(splitPath, file, bigtype_partition,date_partition);
		deleteUploadFile(splitPath + file, bigtype_partition,date_partition);
	}

	/**
	 * 
	 * @param splitpathFolder
	 *            /data/log/nginx/split/1/
	 * @param filename
	 * @param tablename
	 * @param partition
	 */
	private void loadToHiveMachine(String splitpathFolder, String filename, String bigtype_partition,String date_partition) {
		//start check if exists backup file jyc 20151225
		File f = new File(BACKUPFSPLITFOLDER + date_partition + File.separator + bigtype_partition + filename);
		//判断备份文件是否存在
		if(f.exists()){
			File t = new File(splitpathFolder + filename);
			t.delete();
		}
		//end check if exists backup file
		
		File t = new File(splitpathFolder + filename);
		if(t.exists() && t.length() > 0){
			logger.info("***************loadToHiveMachine:start ************");
			System.setProperty("HADOOP_USER_NAME", "root");
			Configuration conf = new Configuration();
			conf.set("fs.defaultFS", HDFSADDR);
			String hiveFilePath = HIVE_ADSTAT_ROOT + "logdate=" + date_partition + "/bigtype=" + bigtype_partition + "/" + filename;
			try {
				FileSystem fs = FileSystem.get(conf);
				fs.copyFromLocalFile(new Path(splitpathFolder + filename), new Path(hiveFilePath));
			} catch (Exception e) {
				logger.info("upload to hive fail:from " + splitpathFolder + filename + ",to:" + hiveFilePath + "********error:" + e.getMessage());
			}
			logger.info("***************loadToHiveMachine:end ************");
		}
	}

	private void deleteUploadFile(String hivename,String bigtype_partition,String date_partition) {
		// update jyc 20151225 如果设置开关为0:not backup 1:backup
		File hivefile = new File(hivename);
		if (hivefile.exists() && hivefile.length() > 0) {
			try {
				//update jyc 2016-01-28  moveFileToDirectory have a bug
				File f = new File(BACKUPFSPLITFOLDER + date_partition + File.separator + bigtype_partition);
				if(!f.exists()){
					f.mkdirs();
				}
				FileUtils.copyFileToDirectory(hivefile, f);
				hivefile.delete();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 暂时还没有使用,作用从hive备份中重新恢复hive数据到hive数据库
	 * @param rootDir  默认传递 /opt/webapps/backup/f/ 
	 * @param day  format:yyyyMMdd
	 * @param bigtype 传递格式1,2,3,4,5,6,7,8(或者其中的一个或者几个)
	 */
	/*public void reloadHiveDayData(String rootDir,String dayPartition,String bigtypePartition) {
		//1)删除hive数据(取决于dayPartition和bigtype)
		//String dropSql = "alter table adstat drop partition(bigtype<=8);";
		//hiveJt.execute("");
		//2)新增hive数据
		System.setProperty("HADOOP_USER_NAME", "root");
		Configuration conf = new Configuration();
		conf.set("fs.defaultFS", HDFSADDR);
		FileSystem fs;
		String[] bigtypeArr = {"1","2","3","4","5","6","7","8"};
		if(bigtypePartition != null && !"".equals(bigtypePartition)){
			bigtypeArr = bigtypePartition.split(",");
		}
		File f = new File(rootDir + dayPartition);
		if(f.exists() && f.isDirectory()){
			File typeFolder = null;
			for(String typePartition : bigtypeArr){
				String typePath = rootDir + dayPartition + File.separator + typePartition + File.separator;
				typeFolder = new File(typePath);
				if(typeFolder.exists() && typeFolder.isDirectory()){
					File[] lists = typeFolder.listFiles();
					for(File file : lists){
						//某个日期某个分区的所有数据
						try {
							fs = FileSystem.get(conf);
							String hiveFilePathTemp = HDFSTEMPFOLDER + file.getName();
							fs.copyFromLocalFile(new Path(typePath + file.getName()), new Path(hiveFilePathTemp));
							StringBuffer sqlBuf = new StringBuffer("LOAD DATA INPATH '").append(hiveFilePathTemp).append("'  INTO TABLE adstat").append(" PARTITION (bigtype='" + typePartition + "',logdate='")
									.append(dayPartition).append("')");
							hiveJt.execute(sqlBuf.toString());
						} catch (Exception e) {
							logger.info(e);
						}
					}
				}else{
					logger.info("在" +dayPartition+ "无"+typePartition+"备份分区,跳过恢复");
				}
			}
		}else{
			logger.info("在" +dayPartition+ "无此备份日期"+dayPartition+",跳过恢复");
		}
	}*/
}
