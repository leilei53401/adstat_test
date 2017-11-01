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

public class AdstatPlayLogImgDiff {
	
	public static Map<String,Long> hidMap = new HashMap<String,Long>();

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
	
	File theFile = new File("/opt/data/adstat/file/368_20161226_8.txt");
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
		printHidInfo();
		System.out.println("共处理数据["+all+"]条，老版本["+ok+"]条，新版本["+newVersion+"]条,非法数据["+exp+"]条,不是前贴["+notplaynum+"]条,失败["+fail+"]条");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}

	
	public static int parse(String data) throws Exception{	
		//截取request串。
		String param = StringUtils.substringBetween(data,"?","HTTP");
		param = StringUtils.trimToEmpty(param);
		
		Map<String, String> lineMap = lineMap(param);
		
		if(lineMap == null){
			System.out.println("播放串["+data+"]解析异常,lineMap==null!");
			return -1;
		}
		
		String hid = lineMap.get("hid");
		//新版本
		if(hidMap.get(hid)!=null){
			long tmpVal = hidMap.get(hid);
			tmpVal++;
			hidMap.put(hid,tmpVal);
		}else{
			hidMap.put(hid,0L);
		}
		
		return 1;
		
	}
	
	public static void printHidInfo(){
		
		Iterator<String> newIt = hidMap.keySet().iterator();
		
		while(newIt.hasNext()){
			String key = newIt.next();
			Long val = hidMap.get(key);
			String line = key+","+val+"\n";
			try {
				FileUtils.writeStringToFile(new File(path+"20161226_368_8_hid.txt"), line, true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}
	

	public static Map<String,String> lineMap(String line){
		String currStr = "";
		try {
			String[] arr = line.split("\\&");
			Map<String,String> map = new HashMap<String,String>();
			//倒序输出,防止starttime多个的时候取串前面出现的starttime
			for(String str : arr){
			//for(int i=arr.length-1;i>=0;i--){
				//String str = arr[i];
				currStr = str;
				if(str.contains("HTTP")){
					str = str.split("\\s+")[0];
				}else if(str.contains("GET ")){
					str = str.split("\\?")[1];
				}
				String[] kv = str.split("\\=");
				if(kv.length>1){
					String k = kv[0];
					String v = kv[1];
					map.put(k, v);
				}else{
					//遇到值为空情况
					String k = kv[0];
					map.put(k, "");
				}
			}
			return map;
		} catch (Exception e) {
			System.err.println("解析串["+line+"]中["+currStr+"]时报错："+e);
			return null;
		}
	}

}
