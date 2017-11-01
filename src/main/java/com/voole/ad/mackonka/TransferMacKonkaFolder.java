package com.voole.ad.mackonka;

import com.voole.ad.utils.AgentUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.LineIterator;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicLong;

@Component("TransferMacKonkaFolder")
public class TransferMacKonkaFolder {
	final Logger logger = Logger.getLogger(TransferMacKonkaFolder.class);
	/*private String macinputpath = GlobalProperties.getProperties("macinputpath");//文件路径
	private String macoutputpath = GlobalProperties.getProperties("macoutputpath");//文件路径*/

	private String macinputpath = "/opt/data/konkamac/all_org";
	private String macoutputpath = "/opt/data/konkamac/all_out";


	public void startTransfer() {
		logger.info("===============start transfer path ["+macinputpath+"] 下 文件==============");

		File inputFolder = new File(macinputpath);
		if(null==inputFolder || !inputFolder.isDirectory()){
			logger.error("macinputpath :["+macinputpath+"] is not Directory!");
			return;
		}
		
		
		
		File[] inputFies  = inputFolder.listFiles();
		
		if(null!=inputFies && inputFies.length>0){
			for(File theFile:inputFies){
				long start = System.currentTimeMillis();
				String name = theFile.getName();
				logger.info("===============start transfer file ["+name+"] ==============");
				final AtomicLong at=new AtomicLong(0l);
				String resultName = name.replaceAll(".txt", ".out");

				LineIterator it = null;
				try {
					it = FileUtils.lineIterator(theFile, "UTF-8");
									
					while (it.hasNext()) {

						   String line="";
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

							   String newLine = line+","+out+","+out2;

							   	writeLine(resultName,newLine);

                               if(at.incrementAndGet()%10000==0){
									logger.info(" get write datas : "+at.get());							

								}
							   if(at.get()<10){
								   System.out.println("out == ["+out+"] , out2 == [" + out2 + "]");
							   }

								
							} catch (Exception e) {
								logger.error("line:["+line+"]解析错误：",e);
							}	
					    }
					
				} catch (IOException e) {
					logger.error("解析文件["+name+"]出错：",e);
				} finally {
				    LineIterator.closeQuietly(it);
				}				
				long end  = System.currentTimeMillis();
				logger.info("==========结束处理文件["+name+"],处理后文件为["+resultName+"],耗时["+(end-start)+"]毫秒!");	
			}
		
		}
		
		logger.info("===============end transfer path ["+macinputpath+"] 下 文件==============");

	}
	
	
	//数据文件
	private void writeLine(String fileName,String line){
		String wholePath ="";
		try {
			wholePath = macoutputpath+File.separator+fileName;
			FileUtils.writeStringToFile(new File(wholePath), line+"\n", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.warn("写入文件["+wholePath+"]数据["+line+"]出错", e);
		}
	}

	

}
