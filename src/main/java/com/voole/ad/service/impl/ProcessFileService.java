package com.voole.ad.service.impl;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;

import javax.annotation.Resource;

import org.apache.commons.io.FileUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import com.voole.ad.model.PlayLogBean;
import com.voole.ad.utils.GlobalProperties;

@Component("processFileService")
public class ProcessFileService {

	private Logger log = Logger.getLogger(ProcessFileService.class);
	@Resource
	public PlayLogRTDeelThread playLogRTDeelThread;

	public ProcessFileService() {
		//playLogRTDeelThread = new PlayLogRTDeelThread();
	}
	
	// inner properties
	public static final String FINISH = ".f";// 每次处理占用文件,占用文件处理后变成的新文件，用于upload
	public static final String HIVE = ".hive";// 每次处理占用文件,占用文件处理后变成的新文件，用于upload

	public static final String SPLITPATH1 = GlobalProperties.getProperties("splitPath1");
	public static final String SPLITPATH2 = GlobalProperties.getProperties("splitPath2");
	public static final String SPLITPATH3 = GlobalProperties.getProperties("splitPath3");
	public static final String SPLITPATH4 = GlobalProperties.getProperties("splitPath4");
	public static final String SPLITPATH5 = GlobalProperties.getProperties("splitPath5");
	public static final String SPLITPATH6 = GlobalProperties.getProperties("splitPath6");
	public static final String SPLITPATH7 = GlobalProperties.getProperties("splitPath7");
	public static final String SPLITPATH8 = GlobalProperties.getProperties("splitPath8");
	public static final long HIVEUPLOADSIZE = Long.valueOf(GlobalProperties.getProperties("hiveuploadsize"));

	/**
	 * 每隔2分钟处理一次(保证实时数据) 每次处理1个文件(可配置) 同时必须锁定这1个文件 防止本次没有处理完下次又重复处理这些文件
	 * 
	 * @throws Exception
	 *             split/1目录可能会存在的文件格式: 原始:
	 *             20150720111835_20150720.f(其中20150720代表这个文件第一条数据的日期) 占用:
	 *             20150720111835_20150720.f.o 完成(hive用): 大小未达到:20150720
	 *             大小已达到:20150720.(System.currentTimeStamps())
	 */
	
	private void process(String splitpath) {
		File f = new File(splitpath);
		String[] files = f.list(new FilenameFilter() {
			public boolean accept(File dir, String name) {
				if (name.length() == 25 && name.endsWith("f")) {
					return true;
				}
				return false;
			}
		});
		for (String processFile : files) {
			partFileIO(splitpath, processFile);
		}
	}
	
	public void processFile1() throws Exception {
		process(SPLITPATH1);
	}

	public void processFile2() throws Exception {
		process(SPLITPATH2);
	}

	public void processFile3() throws Exception {
		process(SPLITPATH3);
	}

	public void processFile4() throws Exception {
		process(SPLITPATH4);
	}

	public void processFile5() throws Exception {
		process(SPLITPATH5);
	}

	public void processFile6() throws Exception {
		process(SPLITPATH6);
	}

	public void processFile7() throws Exception {
		process(SPLITPATH7);
	}

	public void processFile8() throws Exception {
		process(SPLITPATH8);
	}

	/**
	 * 
	 * @param inputFile
	 *            20150720111835_20150720.f(代表该文件第一条的日期)
	 * @return outputFile like 20150720(未填满数据,保证每天执行一次) and
	 *         20150720_1437377400210.hive(供hive上传)
	 * update jyc 2016-03-08 spring quartz当前一次执行未成功的时候，会出现数据不完整的时候进行拷贝
	 */
	public /**synchronized**/ void partFileIO(String splitpath, String inputFile) {
		
		File input = new File(splitpath + inputFile);
		BufferedReader br = null;
		File f1 = null;
		File f2 = null;
		FileWriter fw1 = null;
		FileWriter fw2 = null;
		BufferedWriter bw1 = null;
		BufferedWriter bw2 = null;
		// update jyc 20160122 获取输入文件的文件名的日期，这个日期是通过文件中的第一行的starttime计算来的
		String firstDate = inputFile.substring(15, 23);
		try {
			br = new BufferedReader(new FileReader(input));
			String line = null;
			PlayLogBean playLogBean = null;
			while ((line = br.readLine()) != null) {
				if (line != null && line.contains("starttime") && line.contains("sessionid") && line.contains("uv") && line.contains("type") && line.contains("hid") && line.contains("oemid")
						&& line.contains("uid") && line.contains("provinceid") && line.contains("cityid") && line.contains("adposid") && line.contains("amid") && line.contains("channelid")
						&& line.contains("planid") && line.contains("admt") && line.contains("pv") && line.contains("vv")) {
					// update jyc 2016-01-22 move inside
					String date = line.substring(line.indexOf("starttime=") + 10, line.indexOf("starttime=") + 18);
					if (f1 == null && firstDate.equals(date)) {
						f1 = new File(splitpath + date);
						fw1 = new FileWriter(f1, true);
						bw1 = new BufferedWriter(fw1);
					} else if (f2 == null && !firstDate.equals(date)) {
						f2 = new File(splitpath + date);
						fw2 = new FileWriter(f2, true);
						bw2 = new BufferedWriter(fw2);
					}

					String starttime = line.substring(line.indexOf("starttime=") + 10, line.indexOf("starttime=") + 24);
					String sessionid = line.substring(line.indexOf("sessionid=") + 10, line.indexOf("uv=") - 1);
					String uv = line.substring(line.indexOf("uv=") + 3, line.indexOf("type=") - 1);
					//update jyc 2016-02-24 uv validate
					if(!"0".equals(uv) && !"1".equals(uv)){
						log.info("播放串uv有问题:" + line);
						continue;
					}
					String type = line.substring(line.indexOf("type=") + 5, line.indexOf("hid=") - 1);
					String hid = line.substring(line.indexOf("hid=") + 4, line.indexOf("oemid=") - 1);
					//shaoyl#20160516#导流位汇总触达值较大问题处理
					if(null!=hid&&!"".equals(hid)){
						if(hid.length()>12){
							hid = hid.substring(0,12).toLowerCase();
						}else{
							hid = hid.toLowerCase();
						}
					}
					
					String oemid = line.substring(line.indexOf("oemid=") + 6, line.indexOf("uid=") - 1);
					String uid = line.substring(line.indexOf("uid=") + 4, line.indexOf("provinceid=") - 1);
					String provinceid = line.substring(line.indexOf("provinceid=") + 11, line.indexOf("cityid=") - 1);
					String cityid = line.substring(line.indexOf("cityid=") + 7, line.indexOf("adposid=") - 1);
					String adposid = line.substring(line.indexOf("adposid=") + 8, line.indexOf("amid=") - 1);
					String amid = line.substring(line.indexOf("amid=") + 5, line.indexOf("channelid=") - 1);
					// 2015-08-28 于国慧让加的 有的汇报数据没有广告节目
					if (amid == null || "".equals(amid)) {
						amid = "0";
					}
					String channelid = line.substring(line.indexOf("channelid=") + 10, line.indexOf("planid=") - 1);
					//update jyc 2016-04-08 112 as others
					if("112".equals(channelid)){
						channelid = "0";
					}
					String planid = line.substring(line.indexOf("planid=") + 7, line.indexOf("admt=") - 1);
					String admt = line.substring(line.indexOf("admt=") + 5, line.indexOf("pv=") - 1);
					String pv = line.substring(line.indexOf("pv=") + 3, line.indexOf("vv=") - 1);
					String vv = line.substring(line.indexOf("vv=") + 3, line.indexOf("vv=") + 4);
					// 是否发送广告联盟 0:不发送 (1或者没有这个字段):发送
					String isadap = "";
					if(line.contains("isadap")){
						isadap = line.substring(line.indexOf("isadap=") + 7, line.indexOf("isadap=") + 8);
						if("0".equals(isadap)){
							isadap = "0";
						}else{
							isadap = "1";
						}
					}else{
						isadap = "1";
					}
					//update jyc add 2016-04-29 distributeid(渠道id) exists or not
					String distributeid = "";
					if(line.contains("distributeid")){
						try {
							String endLine = line.substring(line.indexOf("distribute=") + 11);
							if(endLine.contains("ip")){
								distributeid = line.substring(line.indexOf("distribute=") + 11,line.indexOf("ip=") - 1);
							}else{
								distributeid = endLine.split("\\s+")[0];
							}
						} catch (Exception e) {
							log.info("distributeid error:" + line);
							continue;
						}
					}
					String ip = "";
					if(line.contains("ip")){
						try{
							String tempIP = line.substring(line.indexOf("ip=") + 3);
							String[] splitIP = tempIP.split("\\s+");
							ip = splitIP[0];
						}catch(Exception e){
							log.info("ip error:" + line);
							continue;
						}
					}
					// 用于rt_report_time_sum_hive的按照时间段统计
					String timequantum = starttime.substring(8, 10);
					playLogBean = new PlayLogBean();
					playLogBean.setStarttime(starttime);
					playLogBean.setSessionid(sessionid);
					playLogBean.setUv(uv);
					playLogBean.setType(type);
					playLogBean.setHid(hid);
					playLogBean.setOemid(oemid);
					playLogBean.setUid(uid);
					playLogBean.setIp(ip);
					playLogBean.setProvinceid(provinceid);
					playLogBean.setCityid(cityid);
					playLogBean.setAdposid(adposid);
					playLogBean.setAmid(amid);
					playLogBean.setChannelid(channelid);
					playLogBean.setPlanid(planid);
					playLogBean.setAdmt(admt);
					playLogBean.setPv(pv);
					playLogBean.setVv(vv);
					playLogBean.setIsadap(isadap);
					playLogRTDeelThread.put(playLogBean);
					StringBuffer sb = new StringBuffer();
					String newline = sb.append(starttime).append(",").append(sessionid).append(",").append(uv).append(",").append(type).append(",").append(hid).append(",").append(oemid).append(",")
							.append(uid).append(",")
							.append(provinceid)
							// update jyc 2015-12-24
							// 修改城市代码为0，统计时候好分组,否则和现有平台mysql数据对不上
							// update jyc 2016-01-14 修改城市代码为正常，统计时没有关于cityid的分组
							.append(",").append(cityid).append(",").append(adposid).append(",").append(amid).append(",").append(channelid).append(",").append(planid).append(",").append(admt)
							.append(",").append(pv).append(",").append(vv).append(",").append(distributeid).append(",").append(ip).append(",").append(timequantum).toString();
					if (date.equals(firstDate)) {
						bw1.write(newline);
						bw1.newLine();
					} else {
						bw2.write(newline);
						bw2.newLine();
					}
				} else {
					log.info("播放串有问题:" + line);
					continue;
				}
			}
			// f1 name like 20150717 重命名为20150717_(System.currentTimeMillis())
			if (f1 != null && f1.length() > 0) {
				if (f1.length() > HIVEUPLOADSIZE) {
					String name = f1.getName() + "_" + System.currentTimeMillis() + HIVE;
					f1.renameTo(new File(splitpath + name));
				}
			}
			if (f2 != null && f2.length() > 0) {
				if (f2.length() > HIVEUPLOADSIZE) {
					String name = f2.getName() + "_" + System.currentTimeMillis() + HIVE;
					f2.renameTo(new File(splitpath + name));
				}
			}
		} catch (IOException ex) {
			ex.printStackTrace();
			log.info("prcessFileService error" + ex.getMessage());
		} finally {
			try {
				if (input != null && input.exists()) {
					// update jyc todo must copy to save one todo
					// 20150720111835_20150720.f.o
					File f = new File("/opt/webapps/backup/processFileService/" + firstDate);
					if (!f.exists()) {
						f.mkdir();
					}
					try{
						FileUtils.copyFileToDirectory(input, f);
					}catch(Exception e1){
						//log.info(e1.getMessage());
					}
					if (input != null && input.exists()) {
						input.delete();
					}
				}
				if (br != null)
					br.close();
				if (bw1 != null) {
					bw1.flush();
					bw1.close();
				}
				if (bw2 != null) {
					bw2.flush();
					bw2.close();
				}
			} catch (IOException e) {
				log.info("流关闭异常", e);
			}
		}
	}
}
