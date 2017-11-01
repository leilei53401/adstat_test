package com.voole.ad.rescuedata;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicLong;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.google.common.collect.RangeMap;
import com.voole.ad.ipcache.IpCacheDao;
import com.voole.ad.main.StartUp;
import com.voole.ad.utils.GlobalProperties;
import com.voole.ad.utils.ThreadPool;



@Component("parseInterfaceSqlFile")
public class ParseInterfaceSqlFile {
	final Logger logger = Logger.getLogger(StartUp.class);
	@Autowired
	private IpCacheDao ipCacheDao;
	@Autowired
	private ParseDataService parseDataService;
	
	private RangeMap<Long, Integer[]> defaultIpRangeMap;
	
//	String path = "E:\\tmp\\interfile\\log5_test2.sql";
//	String path = "/opt/data/adstat/file/inter_log5_20w-40W.sql";
	
	private String path = GlobalProperties.getProperties("sqlfilepath");//接收原始sql文件
	
	//线程池相关
	private ThreadPool playlogpool;
	private ArrayList<String> playlogbacklist = new ArrayList<String>();
	private int pushPlayLogNum = GlobalProperties.getInteger("playlog.pushNum");//批处理数量
	private int playlogInsertInterval = GlobalProperties.getInteger("playloginsertinterval");//发送memcache时间间隔上限(ms)
	private long beforePushTime = System.currentTimeMillis(); //上次推送时间
	
	//导流位转化
	/*adapter.paster.pos = 15101010,15101011,17101110,17101310,17101210,17101410
			match.adapter.paster.pos = 701,702,704,705,706,707*/
	//"GET /15/1.gif?sessionid=1481525279433252749&uv=0&type=1&hid=0a0adb04b5e5&oemid=10000&uid=148053832&provinceid=10&cityid=1009&adposid=15101010&amid=1050000464&channelid=105&planid=1010000865&admt=5&pv=1&vv=1&isadap=1&ip=119.57.155.125&mid=56457628 HTTP/1.1&starttime=20161212144759"
  	/*public ParseInterfaceSqlFile(){
	
	}*/
  	
  	//初始化信息
  	public void initData(){
  	 
  	initAreaInfo();
  		
  	 playlogpool = new ThreadPool(GlobalProperties.getInteger("pool.corepoolsize"),
			   GlobalProperties.getInteger("pool.maxpoolsize"),
			   GlobalProperties.getInteger("pool.queuesize"),
			  GlobalProperties.getInteger("pool.keepalivetime")
			  );
  	 
  	}
  	
  	public void initAreaInfo(){
  		 defaultIpRangeMap =  ipCacheDao.loadDefaultIpAndArea();
  	}
	
/*	public  void parseIpTest() {
		//init areainfo;
		
		String ip = "123.125.114.144";
		Integer[] areaCode = getAreaInfo(ip);
		int provinceid = (areaCode != null ? (areaCode.length > 1 ? areaCode[0] : areaCode[0]) : 0);
		int cityid = (areaCode != null ? (areaCode.length > 1 ? areaCode[1] : areaCode[0]) : 0);
		System.out.println("provinceid=["+provinceid+"],cityid=["+cityid+"]");
	}*/
	

	public  void startParseSqlFile() {
		
		initData();
		
		String path = "E:\\tmp\\interfile\\interlogtail5.txt";
		File theFile = new File(path);
		AtomicLong num = new AtomicLong(0);
		try {
			LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
			try {
			    while (it.hasNext()) {
			    	String line ="";
			        try {
						line = it.nextLine();
						
						parseDataService.parse(line);
						//response
					/*	String response=StringUtils.substringBetween(line, "<?xml", "</response>");
						
						System.out.println(response);
						//转化为xml解析。
						parseXml("<?xml "+response+" </response>");*/
						
						if(num.incrementAndGet()%100 == 0){
							System.out.println("playlog date: " + num.get());
						}
//						System.out.println(line);
					} catch (Exception e) {
						System.err.println("处理出错："+e);
					}
			     
			    }
			} finally {
			    LineIterator.closeQuietly(it);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	/**
	 * @param args
	 */
//	public static void main(String[] args) {
	
	public  void startParseFilePool() {
		initData();		
		File theFile = new File(path);
		AtomicLong num = new AtomicLong(0);
		try {
			LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
			try {
			    while (it.hasNext()) {
			    	String line ="";
			        try {
						line = it.nextLine();

						//加入线程处理
//						insertDataToPool(line);
						
						if(num.incrementAndGet()%100 == 0){
							logger.info("playlog date size : " + num.get());
							   try {
							    	//线程等待5秒，防止加入数据过多。
									Thread.sleep(1000L);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
						System.out.println(line);
					} catch (Exception e) {
						System.err.println("处理出错："+e);
					}
			     
			    }
			    
			    try {
			    	//一分钟后再入一套条空数据，触发线程处理最后剩余数据。
					Thread.sleep(1000L*60);
//					insertDataToPool("");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			    logger.info("====end===");
			    
			} finally {
			    LineIterator.closeQuietly(it);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
	public  void startParseFromDB() {
		initData();	   
		String path = "E:\\tmp\\interfile\\interlogtail100.txt";
		File theFile = new File(path);
		AtomicLong num = new AtomicLong(0);
		try {
			LineIterator it = FileUtils.lineIterator(theFile, "UTF-8");
			try {
			    while (it.hasNext()) {
			    	String line ="";
			        try {
						line = it.nextLine();
						//加入线程处理
						insertDataToPool(line);
						
						if(num.incrementAndGet()%100 == 0){
							logger.info("playlog date size : " + num.get());
							   try {
							    	//线程等待5秒，防止加入数据过多。
									Thread.sleep(1000L);
								} catch (InterruptedException e) {
									// TODO Auto-generated catch block
									e.printStackTrace();
								}
						}
//						System.out.println(line);
					} catch (Exception e) {
						System.err.println("处理出错："+e);
					}
			     
			    }
			    
			    try {
			    	//一分钟后再入一套条空数据，触发线程处理最后剩余数据。
					Thread.sleep(1000L*60);
					insertDataToPool("");
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			    
			} finally {
			    LineIterator.closeQuietly(it);
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	
    public void insertDataToPool(String data){
					final ArrayList<String> datalistfile = new ArrayList<String>();
					synchronized (playlogbacklist) {
						if ( playlogbacklist.size() >= pushPlayLogNum
								|| (System.currentTimeMillis() - beforePushTime) >= playlogInsertInterval) {
							datalistfile.addAll(playlogbacklist);
							playlogbacklist.clear();
							beforePushTime = System.currentTimeMillis(); // 上次推送时间
						}
						if(StringUtils.isNotBlank(data)){
							playlogbacklist.add(data);
						}
					}
					
					if (datalistfile.size() > 0) {
						// 记文件线程
						playlogpool.execute(new Runnable() {
							@Override
							public void run() {
								parseDataService.parseDataList(datalistfile);
								logger.info("线程加入数据["+datalistfile.size()+"]条!");
							}
						});
						/*Thread t = new Thread(new ExecFileRunable(datalistfile));
						playlogpool.execute(t);*/
//						playlogpool.execute(new ExecFileRunable(datalistfile));
					}
	}

	
	
	public static void main(String[] args) {
//		parseXml("");
		ParseInterfaceSqlFile pf = new ParseInterfaceSqlFile();
		pf.startParseSqlFile();
	}
	
}
