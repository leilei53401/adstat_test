package com.voole.ad.main;

import com.voole.ad.mackonka.TransferMacKonkaFolderFlowOpt;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

//import com.voole.ad.service.impl.CreateHivePartitionService;

public class StartUp {
	public static void main(String[] args) throws IOException {
		final Logger log = Logger.getLogger(StartUp.class);
		log.info("程序开始启动...");
		long beginTime = System.currentTimeMillis();
		try {
			final ApplicationContext ac = new  ClassPathXmlApplicationContext("applicationContext.xml");
			/*CreateHivePartitionService bean = ac.getBean(CreateHivePartitionService.class);
			bean.createHivePartition();*/

			log.info("==========开始 处理文件 ============");
			
//			ParseInterfaceSqlFile parseInterfaceSqlFile = ac.getBean(ParseInterfaceSqlFile.class);
//			parseInterfaceSqlFile.startParseSqlFile();
//			parseInterfaceSqlFile.parseIpTest();
			
//			parseInterfaceSqlFile.startParseFilePool();
/*			IdeaTest ideaTest = ac.getBean(IdeaTest.class);
			ideaTest.test();*/

		/*	TransferMacKonkaFolder transferMacKonkaFolder = ac.getBean(TransferMacKonkaFolder.class);
			transferMacKonkaFolder.startTransfer();*/

            TransferMacKonkaFolderFlowOpt transferMacKonkaFolderFlowOpt = ac.getBean(TransferMacKonkaFolderFlowOpt.class);
            transferMacKonkaFolderFlowOpt.startTransfer();

			log.info("==========结束处理文件 ============");
		} catch (Exception e) {
			e.printStackTrace();
			log.error("加载spring配置文件失败！");
			System.exit(0);
		}
		
		long startUpTime = System.currentTimeMillis() - beginTime;
		log.info("程序启动成功,耗时" + startUpTime + "ms.");
		
	
	}
}
