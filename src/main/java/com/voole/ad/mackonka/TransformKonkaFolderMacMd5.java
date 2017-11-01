package com.voole.ad.mackonka;

import com.voole.ad.utils.AgentUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.io.IOException;
import java.util.Date;

public class TransformKonkaFolderMacMd5 {

	private static String path = "/opt/data/konkamac/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
	long all = 0L;
	long fail = 0L;
	StringBuilder sb = new StringBuilder();
	File theFile = new File("/opt/data/konkamac/BJ_BJ_01.txt");
	String ven="900103";
	
	try {
		LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
		String line = "";
		try {
		    while (it.hasNext()) {
		    	
				try {
					 line = it.nextLine();
					 if(StringUtils.isBlank(line)){
						 continue;
					 }
					//去冒号md5
					 String out =  line.replaceAll(":", "");
					 out = AgentUtils.toMD5(out.toUpperCase());

					//不去冒号md5
					String out2 = AgentUtils.toMD5(line.toUpperCase());

//					 String out = AgentUtils.md5Encode(line.toUpperCase(), "");
					 if(all<10){
						 System.out.println("out == ["+out+"] , out2 == [" + out2 + "]");
					 }
//					 out = AgentUtils.guva32BitMd5Encode(out.toUpperCase(),"123456").toLowerCase();
					 //test
//					 out = AgentUtils.md5Encode(out, "123456");
					 String newLine = line+","+out+","+out2;
					 FileUtils.writeStringToFile(new File(path+"BJ_BJ_01.out"), newLine+"\n", true);
					
				} catch (Exception e) {
					System.err.println("处理数据["+line+"]出错："+e);
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
		
		System.out.println("共处理数据["+all+"]条,失败["+fail+"]条");
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	}
	


}
