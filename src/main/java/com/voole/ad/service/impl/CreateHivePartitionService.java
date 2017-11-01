package com.voole.ad.service.impl;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

/**
 * 头一天临时建立下一天的hive目录分区
 * 
 * @author Administrator
 *
 */
@Component("createHivePartitionService")
public class CreateHivePartitionService {

	private Logger logger = Logger.getLogger(CreateHivePartitionService.class);

	@Autowired
	public JdbcTemplate hiveJt;

	public void createHivePartition() {
		
		Calendar calendar0 = Calendar.getInstance();
		Date date0 = calendar0.getTime();
		
		Calendar calendar1 = Calendar.getInstance();
		calendar1.add(Calendar.DAY_OF_YEAR, 1);
		Date date1 = calendar1.getTime();

		Calendar calendar2 = Calendar.getInstance();
		calendar2.add(Calendar.DAY_OF_YEAR, 2);
		Date date2 = calendar2.getTime();

		Calendar calendar3 = Calendar.getInstance();
		calendar3.add(Calendar.DAY_OF_YEAR, 3);
		Date date3 = calendar3.getTime();

		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		String day_0 = sdf.format(date0);
		String day_1 = sdf.format(date1);
		String day_2 = sdf.format(date2);
		String day_3 = sdf.format(date3);
		
		for (int i = 1; i <= 8; i++) {
			String partition = "logdate=" + day_0 + ",bigtype=" + i;
			String sql = "alter table adstat add partition (" + partition + ")";
			try {
				hiveJt.execute(sql);
			} catch (Exception e) {
				logger.info("create hive partition fail,:" + e.getLocalizedMessage());
				continue;
			}
			logger.info("createHivePartition:" + sql);
		}
		
		for (int i = 1; i <= 8; i++) {
			String partition = "logdate=" + day_1 + ",bigtype=" + i;
			String sql = "alter table adstat add partition (" + partition + ")";
			try {
				hiveJt.execute(sql);
			} catch (Exception e) {
				logger.info("create hive partition fail,:" + e.getLocalizedMessage());
				continue;
			}
			logger.info("createHivePartition:" + sql);
		}
		for (int i = 1; i <= 8; i++) {
			String partition = "logdate=" + day_2 + ",bigtype=" + i;
			String sql = "alter table adstat add partition (" + partition + ")";
			try {
				hiveJt.execute(sql);
			} catch (Exception e) {
				logger.info("create hive partition fail,:" + e.getLocalizedMessage());
				continue;
			}
			logger.info("createHivePartition:" + sql);
		}
		for (int i = 1; i <= 8; i++) {
			String partition = "logdate=" + day_3 + ",bigtype=" + i;
			String sql = "alter table adstat add partition (" + partition + ")";
			try {
				hiveJt.execute(sql);
			} catch (Exception e) {
				logger.info("create hive partition fail,:" + e.getLocalizedMessage());
				continue;
			}
			logger.info("createHivePartition:" + sql);
		}
	}
}
