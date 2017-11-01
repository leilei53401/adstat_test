package com.voole.ad.file;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;

public class CountInterfaceSqlLogFile {
	
	public static Map<String,Long> oldOemidMap = new HashMap<String,Long>();
	public static Map<String,Long> newOemidMap = new HashMap<String,Long>();
	private static String path = "/opt/data/adstat/logs/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	long all = 0L;
	long exp =  0L;
	long notplaynum = 0L;
	long ok =  0L;
	long newVersion = 0L;
	long fail = 0L;
	
	File theFile = new File("/opt/data/adstat/file/20161218_playlog.txt");
	try {
		LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
		try {
		    while (it.hasNext()) {
		    	String line = it.nextLine();
		    	//数据样例
				//INSERT INTO rt_og_play_log5(oemid,oemname,hid,adverno,agentno,spid,provinceid,cityid,channelid,programid,movname,sid,adlength,coderate,resolution,starttime,endtime,playtime,speed,fullplay,amid,adname,fid,logid,adposid,sessionid,admt,uid,batchplanid,adorder) VALUES ('705','116.248.71.36','1CA770A37089','900005','900005','100001','83','8317','101','112','116.248.71.36','null','null','null','null','2016-12-15 00:00:06','2016-12-15 00:00:06','0', CAST('0'/1024 AS SIGNED ),'1','1050000508','161207夏有乔木-免费','179d4af67acc07faed896675571e07be','74f84724_2864423011','701','452377821076530841200000','5','126169642','0','1') ON DUPLICATE KEY UPDATE fullplay=values(fullplay);
//				String s = "INSERT INTO rt_og_play_log5(oemid,oemname,hid,adverno,agentno,spid,provinceid,cityid,channelid,programid,movname,sid,adlength,coderate,resolution,starttime,endtime,playtime,speed,fullplay,amid,adname,fid,logid,adposid,sessionid,admt,uid,batchplanid,adorder) VALUES ('705','116.248.71.36','1CA770A37089','900005','900005','100001','83','8317','101','112','116.248.71.36','null','null','null','null','2016-12-15 00:00:06','2016-12-15 00:00:06','0', CAST('0'/1024 AS SIGNED ),'1','1050000508','161207夏有乔木-免费','179d4af67acc07faed896675571e07be','74f84724_2864423011','701','452377821076530841200000','5','126169642','0','1') ON DUPLICATE KEY UPDATE fullplay=values(fullplay);";
				
				try {
						int result = parse(line);
						switch(result){
							case -1:
								exp++;
								break;
							case 0:
								notplaynum++;
								break;
							case 1:
								ok++;
								break;
							case 2:
								newVersion++;
								break;							
						}
				} catch (Exception e) {
					System.err.println(e);
					fail++;
				}
					all++;
					
					if(all%10000 == 0){
						System.out.println((new Date())+",allnum="+all);
					}
		    }
    
			} finally {
			    LineIterator.closeQuietly(it);
			}
		printOemidInfo();
		System.out.println("共处理数据["+all+"]条，老版本["+ok+"]条，新版本["+newVersion+"]条,非法数据["+exp+"]条,不是前贴["+notplaynum+"]条,失败["+fail+"]条");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	public static int parse(String data) throws Exception{	
		
		String values = StringUtils.substringBetween(data, "VALUES (", ") ON DUPLICATE KEY");
		
//		System.out.println("values = " + values);
		
		String [] arrays = values.split(",");
		
		if(arrays.length<30){
			System.out.println("数据["+data+"]处理异常!");
			return -1;
		}
		
	/*	System.out.println(arrays.length);
		
		for(String str:arrays){
		System.out.println(str);	
		}*/
		
		String oemid = arrays[0];
		
		String adposid = arrays[24];
		String adposidValue =StringUtils.substringBetween(adposid, "'", "'");
		
		if(!"701".equals(adposidValue)){
			System.out.println("数据["+data+"]不是前贴数据");
			return 0;
		}
		String logid =  arrays[23];
		String logidValue =StringUtils.substringBetween(logid, "'", "'");

		//取最后一位
		String lastPosValue = logidValue.substring(logidValue.length()-1,logidValue.length());
		if("1".equals(lastPosValue)){
			//老版本
			if(oldOemidMap.get(oemid)!=null){
				long tmpVal = oldOemidMap.get(oemid);
				tmpVal++;
				oldOemidMap.put(oemid,tmpVal);
			}else{
				oldOemidMap.put(oemid,0L);
			}
			
			return 1;
		}else{
			//新版本
			if(newOemidMap.get(oemid)!=null){
				long tmpVal = newOemidMap.get(oemid);
				tmpVal++;
				newOemidMap.put(oemid,tmpVal);
			}else{
				newOemidMap.put(oemid,0L);
			}
			return 2;
		}
		
		
	}
	
	public static void printOemidInfo(){
		
		Iterator<String> oldIt = oldOemidMap.keySet().iterator();
		
		while(oldIt.hasNext()){
			String key = oldIt.next();
			Long val = oldOemidMap.get(key);
			String line = key+","+val+"\n";
			try {
				FileUtils.writeStringToFile(new File(path+"20161218_old.txt"), line, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		

		Iterator<String> newIt = newOemidMap.keySet().iterator();
		
		while(newIt.hasNext()){
			String key = newIt.next();
			Long val = newOemidMap.get(key);
			String line = key+","+val+"\n";
			try {
				FileUtils.writeStringToFile(new File(path+"20161218_new.txt"), line, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
		
		
	}

}
