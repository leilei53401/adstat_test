package com.voole.ad.file;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;

//比较前贴和缓冲hid曝光差异
public class AdstatPlayLogImgHidDiff {
	
	public static Map<String,Long> playHidMap = new HashMap<String,Long>();
	public static Map<String,Long> imgHidMap = new HashMap<String,Long>();

	private static String path = "/opt/data/adstat/logs/";
	/**
	 * @param args
	 */
	public static void main(String[] args) {
			//初始前贴数据
			File theFile = new File("/opt/data/adstat/file/20161226_368_6_hid_sort.txt");
			try {
				LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
				try {
					    while (it.hasNext()) {
					    	String line = it.nextLine();
					    	String[] array =  line.split(",");
					    	playHidMap.put(array[0],Long.valueOf(array[1]));
					    }	    
					} finally {
					    LineIterator.closeQuietly(it);
					}
		
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//图片数据
			File imgFile = new File("/opt/data/adstat/file/20161226_368_8_hid_sort.txt");
			try {
				LineIterator itImg = FileUtils.lineIterator(imgFile, "UTF-8");
				try {
					    while (itImg.hasNext()) {
					    	String line = itImg.nextLine();
					    	String[] array =  line.split(",");
					    	imgHidMap.put(array[0],Long.valueOf(array[1]));
					    }	    
					} finally {
					    LineIterator.closeQuietly(itImg);
					}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//前贴 有，缓冲没有的终端
			HashMap<String,Long> playOutOfHid  = new HashMap<String,Long>();
			
			Iterator<String> playIt = playHidMap.keySet().iterator();
			while(playIt.hasNext()){
				String key = playIt.next();
				if(imgHidMap.get(key)==null){
					playOutOfHid.put(key, playHidMap.get(key));
				}
			}
			
			printHidInfo(playOutOfHid,"playOutOfHid");
			
			//缓冲有，前贴没有的终端
			HashMap<String,Long> imtOutOfHid  = new HashMap<String,Long>();
			//缓冲比前贴数据差异的终端。
			HashMap<String,Long> diffHid  = new HashMap<String,Long>();
			
			Iterator<String> imgIt = imgHidMap.keySet().iterator();
			while(imgIt.hasNext()){
				String key = imgIt.next();
				if(playHidMap.get(key)==null){
					imtOutOfHid.put(key, imgHidMap.get(key));
				}else{
					long imgvalue = imgHidMap.get(key);
					long playvalue = playHidMap.get(key);
					long diff  = imgvalue-playvalue;
					diffHid.put(key,  diff);
				}
			}
			
			printHidInfo(imtOutOfHid,"imtOutOfHid");
			printHidInfo(diffHid,"diffHid");
			
			System.out.println("done");
			
	}

	
	
	public static void printHidInfo(HashMap<String,Long> hidMap,String name){
		
		Iterator<String> newIt = hidMap.keySet().iterator();
		while(newIt.hasNext()){
			String key = newIt.next();
			Long val = hidMap.get(key);
			String line = key+","+val+"\n";
			try {
				FileUtils.writeStringToFile(new File(path+"20161226_368_"+name+".txt"), line, true);
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
