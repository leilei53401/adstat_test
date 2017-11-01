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

@Component("TransferMacKonkaFolderFlowOpt")
public class TransferMacKonkaFolderFlowOpt {
	final Logger logger = Logger.getLogger(TransferMacKonkaFolderFlowOpt.class);
	/*private String macinputpath = GlobalProperties.getProperties("macinputpath");//文件路径
	private String macoutputpath = GlobalProperties.getProperties("macoutputpath");//文件路径*/

	private String macinputpath = "/opt/data/flowoptmac/mac_input";
	private String macoutputpath = "/opt/data/flowoptmac/mac_output";


	public void startTransfer() {
		logger.info("===============start transfer path ["+macinputpath+"] 下 文件==============");

		File inputFolder = new File(macinputpath);
		if(null==inputFolder || !inputFolder.isDirectory()){
			logger.error("macinputpath :["+macinputpath+"] is not Directory!");
			return;
		}
		
		
		//一级目录，媒体
		File[] inputFies  = inputFolder.listFiles();
		
		if(null!=inputFies && inputFies.length>0){

		    //循环媒体目录
			for(File mediaFolder:inputFies){

				String mediaid = mediaFolder.getName();

                logger.info("===============start prcess mediaid ["+mediaid+"] ==============");
                //省份目录
                File[] provinceFiles   =   mediaFolder.listFiles();
                //循环省份目录
                for(File provinceFolder:provinceFiles){

                    String provinceFolderName = provinceFolder.getName();
                    String provinceid = provinceFolderName.split("_")[1];

                    logger.info("===============start prcess province ["+provinceFolderName+"] ==============");

                    //各地市文件
                    File[] cityFiles =   provinceFolder.listFiles();
                    //循环地市目录
                    for(File cityFile : cityFiles){
                        long start = System.currentTimeMillis();
                        String cityfileName = cityFile.getName();

                        logger.info("===============start prcess cityfile ["+cityfileName+"] ==============");

                        final AtomicLong at=new AtomicLong(0l);
                        String resultName = cityfileName.replaceAll(".txt", ".out");

                        LineIterator it = null;
                        try {
                            it = FileUtils.lineIterator(cityFile, "UTF-8");

                            while (it.hasNext()) {

                                String line="";
                                try {

                                    line = it.nextLine();
                                    String [] array = line.split("\\|");
                                    String mac = array[0];

                                    if(StringUtils.isBlank(mac)){
                                        continue;
                                    }


                                    //去冒号md5
                                    String md5Mac =  mac.replaceAll(":", "");

                                    if(md5Mac.length()<=12){
                                        md5Mac = AgentUtils.toMD5(md5Mac.toUpperCase());
                                    }else {
                                        md5Mac = mac;
                                    }

                                    String outMac = mac+"|"+md5Mac;

                                    array[0] = outMac;

                                    String newLine = StringUtils.join(array,"|");

                                    writeLine(mediaid, provinceid, resultName, newLine);

                                    if(at.incrementAndGet()%10000==0){
                                        logger.info(" get write datas : "+at.get());
                                    }

                                    if(at.get()<10){
                                        System.out.println("newLine = ["+newLine+"]");
                                    }

                                } catch (Exception e) {
                                    logger.error("line:["+line+"]解析错误：",e);
                                }
                            }


                        } catch (IOException e) {
                            logger.error("解析文件["+cityfileName+"]出错：",e);
                        } finally {
                            LineIterator.closeQuietly(it);
                        }
                        long end  = System.currentTimeMillis();
                        logger.info("==========结束处理文件["+cityfileName+"],处理后文件为["+resultName+"],耗时["+(end-start)+"]毫秒!");



                    }//cityfile for end


                    logger.info("===============start prcess province ["+provinceFolderName+"] ==============");


                }//province for end

                logger.info("===============start prcess mediaid ["+mediaid+"] ==============");
			}//media for end
		
		}
		
		logger.info("===============end transfer path ["+macinputpath+"] 下 文件==============");

	}
	
	
	//数据文件
	private void writeLine(String mediaid,String provinceid,String fileName,String line){
		String wholePath ="";
		try {
            wholePath = macoutputpath+
            File.separator + mediaid + File.separator + provinceid + File.separator + fileName;
            FileUtils.writeStringToFile(new File(wholePath), line+"\n", true);
		} catch (IOException e) {
			// TODO Auto-generated catch block
//			e.printStackTrace();
			logger.warn("写入文件["+wholePath+"]数据["+line+"]出错", e);
		}
	}

	

}
