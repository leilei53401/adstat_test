package com.voole.ad.rescuedata;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import parquet.Log;

import com.voole.ad.utils.GlobalProperties;
import com.voole.ad.utils.ThreadPool;

@Service
public class ParseDataService {
	
	private org.slf4j.Logger logger = LoggerFactory.getLogger(ParseDataService.class);
	private org.slf4j.Logger logMonitor = LoggerFactory.getLogger("adstatMonitor");
	@Autowired
	DictCacheTools dictCacheTools;
	//记录日志线程相关参数
	private ThreadPool recordpool;
	private ArrayList<String> recordlist = new ArrayList<String>();
	private int pushRecordNum = GlobalProperties.getInteger("playlog.recordNum");//批处理数量
	private int recordInsertInterval = GlobalProperties.getInteger("recordinsertinterval");//发送memcache时间间隔上限(ms)
	private long beforeRecordTime = System.currentTimeMillis(); //上次推送时间
	
//	private String path = "/opt/adstat/logs/";
	private String path = "/opt/data/adstat/logs/";
	
	DateTimeFormatter localfromat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	DateTimeFormatter strDateFormat = DateTimeFormat.forPattern("yyyy-MM-dd HH:mm:ss");
	DateTime localdate = DateTime.parse("2016-12-08 00:00:00", localfromat);
	long eightBegin = localdate.getMillis();
	
	public ParseDataService(){
		 logger.info("---------ParseDataService-----------------");
		/* recordpool = new ThreadPool(GlobalProperties.getInteger("hivepool.corepoolsize"),
				   GlobalProperties.getInteger("hivepool.maxpoolsize"),
				   GlobalProperties.getInteger("hivepool.queuesize"),
				  GlobalProperties.getInteger("hivepool.keepalivetime")
				  );*/
	}
	
	public void parseDataList(List<String> datas){
		int err = 0;
		int ok = 0 ;
		for(String data:datas){
			try {
				parse(data);
				ok++;
			} catch (Exception e) {
				logger.info("处理数据["+data+"]异常:",e);
				err++;
				continue;
			}
		}
		logger.info("线程处理数据["+datas.size()+"]条，正常["+ok+"]条，错误["+err+"]条!");
	}

	public void parse(String data) throws Exception{	
		logger.info("开始解析数据["+data+"]");
		if(!data.startsWith("insert")){
			logger.info("data ["+data+"] is not start with insert!");
			return;
		}
		
		data = data.replaceAll("NULL", "'NULL'");
		
		String values = StringUtils.substringBetween(data, "values('", ");");
		
		String [] arrays = values.split("','");
		
		String startTime="";
		String oemid="";
		String hid="";
		String uid="";
		String ip="";
		String mid="";
		String param="";
//		String mtype="";
		try {
			startTime = arrays[0];
			oemid = arrays[2];
			hid = arrays[3];
			uid = arrays[6];
			ip = arrays[7];
			mid = arrays[9];
//			mtype = arrays[11];
			param = arrays[13];
		} catch (Exception e) {
			logger.error("处理["+data+"]异常:",e);
			return;
		}
		
		//处理starttime
//		DateTime startdate = DateTime.parse(startTime, strDateFormat);
		String strStartDate = startTime.replaceAll("\\-|\\:|\\s", "");
		String hour = strStartDate.substring(8,10);
		
		String planid="999999";
				
//		String mtype = StringUtils.substringBetween(param, "mtype=", "&catcode");
		String mtype = StringUtils.substringBefore(StringUtils.substringAfter(param, "mtype="), "&");
		String channelid = dictCacheTools.getChannelIdByMtype(mtype);
		
		Integer[] areaCode = dictCacheTools.getAreaInfo(ip);
		
		int provinceid = (areaCode != null ? (areaCode.length > 1 ? areaCode[0] : areaCode[0]) : 0);
		int cityid = (areaCode != null ? (areaCode.length > 1 ? areaCode[1] : areaCode[0]) : 0);
		
//		logger.info("provinceid=["+provinceid+"],cityid=["+cityid+"]");
		
//		StringBuffer sbOrg = new StringBuffer();
		
		String  response = arrays[16];
		//解析广告xml
		List<HashMap<String,String>> adList = parseXml(response);
		
		for(HashMap adMap:adList){
			
			//拼原始串
			StringBuffer sbOrg = new StringBuffer();
			/*map.put("sessionid",serialNo);
			map.put("amid",amid);
			map.put("pos",newPos);
			map.put("index",index+"");*/
			sbOrg.append("\"GET /15/1.gif?sessionid=").append(adMap.get("sessionid"))
			.append("&uv=0&type=0&hid=").append(hid)
			.append("&oemid=").append(oemid)
			.append("&uid=").append(uid)
			.append("&provinceid=").append(provinceid).append("&cityid=").append(cityid)
			.append("&adposid=").append(adMap.get("pos"))
			.append("&amid=").append(adMap.get("amid"))
			.append("&channelid=").append(channelid)
			.append("&planid=999999&admt=9&pv=").append(adMap.get("pv")).append("vv=1").append("isadap=0")
			.append("ip=").append(ip).append("&mid=").append(mid)
			.append(" HTTP/1.1&starttime=").append(strStartDate).append("\"");
			//备份原始串
			recordHttpString(sbOrg.toString(), startTime);
			
			//拼接hive格式串
			StringBuffer sbHive = new StringBuffer();
			sbHive.append(strStartDate).append(",").append(adMap.get("sessionid")).append(",").append("0").append(",").append("0")
			.append(",").append(hid).append(",").append(oemid).append(",").append(uid).append(",")
			.append(provinceid).append(",").append(cityid).append(",").append(adMap.get("pos")).append(",").append(adMap.get("amid"))
			.append(",").append(channelid).append(",").append(planid).append(",").append("9")
			.append(",").append(adMap.get("pv")).append(",").append("1").append(",").append("1").append(",").append(ip).append(",").append(hour).append(",").append(mid).append(",").append(strStartDate);
			
			recordHiveString(sbHive.toString(),startTime);
			
		}

		
		//"GET /15/1.gif?sessionid=1481525279433252749&uv=0&type=1&hid=0a0adb04b5e5&oemid=10000&uid=148053832
		//&provinceid=10&cityid=1009&adposid=15101010&amid=1050000464&channelid=105&planid=1010000865&admt=5
		//&pv=1&vv=1&isadap=1&ip=119.57.155.125&mid=56457628 HTTP/1.1&starttime=20161212144759"
//		logMonitor.info(data);
		
		/*StringBuffer sbHive = new StringBuffer();
		String newline = sbHive.append(starttime).append(",").append(sessionid).append(",").append(uv).append(",").append(type).append(",").append(hid).append(",").append(oemid).append(",")
				.append(uid).append(",")
				.append(provinceid)
				// update jyc 2015-12-24
				// 修改城市代码为0，统计时候好分组,否则和现有平台mysql数据对不上
				// update jyc 2016-01-14 修改城市代码为正常，统计时没有关于cityid的分组
				.append(",").append(cityid).append(",").append(adposid).append(",").append(amid).append(",").append(channelid).append(",").append(planid).append(",").append(admt)
				.append(",").append(pv).append(",").append(vv).append(",").append(distributeid).append(",").append(ip).append(",").append(timequantum).append(",").append(mid).append(",").append(occurtime).toString();*/
		
//		log.debug("newline==="+newline);
		
		//star recode data
		/*recordHttpString(data,startTime);
		recordHiveString(data,startTime);*/
		
//		recordHiveData(data);
	}
	//解析xml字符串。
	private  List<HashMap<String,String>> parseXml(String responseStr){
		     List<HashMap<String,String>> list = new ArrayList<HashMap<String,String>>();
			
			responseStr =  responseStr.replaceAll("\\\\r\\\\n","");
			responseStr =  responseStr.replaceAll("\\\\","");
			System.out.println("===== responseStr start ===========");
			System.out.println(responseStr);
			System.out.println("===== responseStr end ===========");
			try {
				SAXReader saxReader = new SAXReader();
//				Document document = saxReader.read("E:\\tmp\\interfile\\ad3.xml");
//				Document document = saxReader.read(responseStr);
//				Document document = saxReader.read(new ByteArrayInputStream(responseStr.getBytes()));
				Document document = DocumentHelper.parseText(responseStr);  
				Element responseEle=document.getRootElement();
			/*	Element paramsEle = responseEle.element("params");
				String params = paramsEle.getTextTrim();
				System.out.println(params);*/
				
				//处理adinfo节点
				Element adinfoEle = responseEle.element("adinfo");
				Iterator<Element> adposIt = adinfoEle.elements("adpos").iterator();
				//多个adpos
				while(adposIt.hasNext()){
					Element adposEle = adposIt.next();
//					logger.info(adposEle.getName() + ":" + adposEle.getText());  
					//多个mediainfo
					Iterator<Element> mediainfoIt = adposEle.elements("mediainfo").iterator();
					int index = 0;
					while(mediainfoIt.hasNext()){
						Element mediainfoEle = mediainfoIt.next();
//						logger.info(mediainfoEle.getName() + ":" + mediainfoEle.getText());

						try {
							String serialNo="0";
							if(mediainfoEle.attribute("serialNo")!=null){
								serialNo = mediainfoEle.attribute("serialNo").getText();
							}else{
								serialNo = System.currentTimeMillis()+"";
							}
							
//							logger.info("serialNo:" + serialNo); 
							String amid ="0";
							if( mediainfoEle.attribute("amid")!=null){
								amid = mediainfoEle.attribute("amid").getText();
							}
							
							String pos="701";
							if(mediainfoEle.attribute("pos")!=null){
								 pos = mediainfoEle.attribute("pos").getText();
							}
							
							String newPos = dictCacheTools.getAdposNew(pos);
							
							HashMap<String,String> map = new HashMap<String,String>();
							map.put("sessionid",serialNo);
							map.put("amid",amid);
							map.put("pos",newPos);
							map.put("pv",index>0?"0":"1");
							list.add(map);
							index++;//index>0 pv=0
						} catch (Exception e) {
							logger.error("处理mediainfo出错：",e);
							continue;
						}
					}
				}
			} catch (DocumentException e) {
				e.printStackTrace();
			}
			logger.info("解析XMl结果信息："+list.toString());
			return list;
		}
	
	
	public void recordHttpString(String data,String startTime){
		logMonitor.info("start insert org data : ["+data+"]");
		 DateTime startDate = DateTime.parse(startTime, localfromat);
		 if(startDate.getMillis()<eightBegin){
			 //写到7号文件
			 try {
				FileUtils.writeStringToFile(new File(path+"20161207_http.txt"), data+"\n", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//写8号文件
			try {
				FileUtils.writeStringToFile(new File(path+"20161208_http.txt"), data+"\n", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
//	写hive格式文件
	public void recordHiveString(String data,String startTime){
		logMonitor.info("start insert hive data : ["+data+"]");
		 DateTime startDate = DateTime.parse(startTime, localfromat);
		 if(startDate.getMillis()<eightBegin){
			 //写到7号文件
			 try {
				FileUtils.writeStringToFile(new File(path+"20161207_hive.txt"), data+"\n", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}else{
			//写8号文件
			try {
				FileUtils.writeStringToFile(new File(path+"20161208_hive.txt"), data+"\n", true);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
		
	}
	
	/**
	 * 记录hive结果数据
	 */
	public void recordHiveData(String data){
				// 记录原始数据到文件
					final ArrayList<String> datalistfile = new ArrayList<String>();
					synchronized (recordlist) {
						if ( recordlist.size() >= pushRecordNum
								|| (System.currentTimeMillis() - beforeRecordTime) >= recordInsertInterval) {
							datalistfile.addAll(recordlist);
							recordlist.clear();
							beforeRecordTime = System.currentTimeMillis(); // 上次推送时间
						}
						recordlist.add(data);
					}
					
					if (datalistfile.size() > 0) {
						final String cataloguename = "/opt/adstat/logs";
						final File playfile = new File("/opt/adstat/logs/20161207_08_hive.txt");
						// 记文件线程
						recordpool.execute(new Runnable() {
							@Override
							public void run() {
								OutputStream outfile = null;
								try {
									File file = new File(cataloguename);
									if (!file.isDirectory()) {
										file.mkdirs();
									}
									outfile = new FileOutputStream(playfile, true);
									IOUtils.writeLines(datalistfile, null, outfile, "UTF-8");
									logger.info("写入数据:["+datalistfile.size()+"]条!");
								} catch (IOException e) {
									logger.error("写文件异常," + e.getMessage());
									e.printStackTrace();
								} finally {
									IOUtils.closeQuietly(outfile);
								}
							}
						});
					}
	}
	
	
}
