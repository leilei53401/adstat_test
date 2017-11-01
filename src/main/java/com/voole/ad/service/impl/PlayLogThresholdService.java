package com.voole.ad.service.impl;

import java.text.ParseException;
import java.util.Date;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.stereotype.Service;

import com.voole.ad.model.PlayLogBean;
import com.voole.ad.utils.ConvertUtil;
import com.voole.ad.utils.cache.MemcacheTools;
import com.voole.ad.utils.cache.MemcacheUtils;

/**
 * 
 * 播放日志频次控制处理
 * 
 * @author Administrator
 *
 */
@Service
public class PlayLogThresholdService {

	/***
	 * Modified By gaoyd 2016-07-19 原因是：SimpleDateFormat
	 * 是非线程安全类。在多线程环境下使用数据会出现异常。线程安全封装类见：DateFormatTools
	 */
	// public static SimpleDateFormat sdf = new
	// SimpleDateFormat("yyyyMMddHHmmss");
	// public static SimpleDateFormat sdfYmd = new SimpleDateFormat("yyyyMMdd");
	// public static SimpleDateFormat sdfMm = new SimpleDateFormat("MM");
	/**
	 * Modified By gaoyd 2016-07-19 end
	 */
	private Logger logger = Logger.getLogger(PlayLogThresholdService.class);
	@Resource
	private MemcacheUtils memUtils;
	@Resource
	private MemcacheTools memcacheTools;//跨机房复制工具类
	


	/**
	 * 
	 * @param playlog
	 * @throws ParseException
	 */
	public void playlogThreshold(PlayLogBean playlog) throws ParseException {

		// playlog.toString());
		if (!("0".equals(playlog.getAmid()) || "-1".equals(playlog.getAmid()))) {// 节目ID为0或者-1不存入memcached中

			String sta = playlog.getStarttime();
			String localtime = sta; // 14位 yyyyMMddHHmmss
			DateTimeFormatter localfromat = DateTimeFormat.forPattern("yyyyMMddHHmmss");
			DateTime localdate = DateTime.parse(localtime, localfromat);
			String localmonth = localdate.toString("MM"); // 月日
			String localyearDay = localdate.toString("yyyyMMdd");// 年月日
			String hid = ConvertUtil.getHid(playlog.getHid());
			int week = localdate.getWeekOfWeekyear();

			// 排期的cpm（排期总播放量）
			long pln_cpm = memUtils.incr(playlog.getPlanid() + "");
			// logger.info("pln===============" +playlog.getPlanid() + "");
			if (pln_cpm < 0) {
				memUtils.set(playlog.getPlanid() + "", "1");
			}
			
			memcacheTools.copy2Beijing(playlog.getPlanid() + "");

			// 排期的cpmd（排期日总播放量）
			Date expDayTime = new Date(1000*60*60*24);
			long pln_cpmd = memUtils.incr(playlog.getPlanid() + "-" + localyearDay);
			// logger.info("pln_localyearDay===============" +
			// playlog.getPlanid() + "-" + localyearDay);
			if (pln_cpmd < 0) {
				memUtils.set(playlog.getPlanid() + "-" + localyearDay, "1",expDayTime);//增加过期时间
			}
			
			memcacheTools.copy2Beijing(playlog.getPlanid() + "-" + localyearDay,expDayTime);
			
			
			// 排期 + hid（排期内单用户播放次数）
			long pln_hid = memUtils.incr(playlog.getPlanid() + "-" + hid);
			// logger.info("pln_hid==============="+playlog.getPlanid() + "-" +
			// hid);
			if (pln_hid < 0) {
				memUtils.set(playlog.getPlanid() + "-" + hid, "1");
			}
			
			memcacheTools.copy2Beijing(playlog.getPlanid() + "-" + hid);
			
			// 排期+hid+day(日频次控制)
			long pln_day = memUtils.incr(playlog.getPlanid() + "-" + hid + "-" + localyearDay);
			// logger.info("pln_hid_localyearDay==============="+playlog.getPlanid()
			// + "-" + hid + "-" + localyearDay);
			if (pln_day < 0) {
				memUtils.set(playlog.getPlanid() + "-" + hid + "-" + localyearDay, "1",expDayTime);
			}
			
			memcacheTools.copy2Beijing(playlog.getPlanid() + "-" + hid + "-" + localyearDay,expDayTime);
			
			// 排期+hid+month(月频次控制)
			long pln_month = memUtils.incr(playlog.getPlanid() + "-" + hid + "-" + localmonth);
			// logger.info("pln_hid_localmonth==============="+playlog.getPlanid()
			// + "-" + hid + "-" + localmonth);
			if (pln_month < 0) {
				memUtils.set(playlog.getPlanid() + "-" + hid + "-" + localmonth, "1");
			}
			
			memcacheTools.copy2Beijing(playlog.getPlanid() + "-" + hid + "-" + localmonth);
			
			// 排期+hid+weekday(周频次控制)
			Date expWeekTime = new Date(1000*60*60*24*7);
			// Calendar calWeek = Calendar.getInstance();
			// calWeek.setTime(DateFormatTools.YYYYMMDDHHMMSS.get().parse(sta));
			long pln_weekday = memUtils.incr(playlog.getPlanid() + "-" + hid + "-" + week);
			// logger.info("pln_hid_weekday==============="+playlog.getPlanid()
			// + "-" + hid + "-" + week);
			if (pln_weekday < 0) {
				memUtils.set(playlog.getPlanid() + "-" + hid + "-" + week, "1",expWeekTime);
			}			
			memcacheTools.copy2Beijing(playlog.getPlanid() + "-" + hid + "-" + week,expWeekTime);

			// 流量均衡频次put到memecached---------------mali 04.25-----------------
			String startTime = sta; // 14位 yyyyMMddHHmmss
			DateTimeFormatter fromat = DateTimeFormat.forPattern("yyyyMMddHHmmss");
			DateTime date = DateTime.parse(startTime, fromat);
			String year = date.toString("yyyy"); // 年
			String day = date.toString("MMdd"); // 月日
			String hour = date.toString("HH"); // 时
			String yearDay = date.toString("yyyyMMdd");// 年月日
			String planid = playlog.getPlanid();
			String provinceid = playlog.getProvinceid();
			// logger.info();
			String tomorrowDayStr = "";
			String nextDay = Integer.parseInt(day) + 1 + ""; // 0525 --> 526
			if (nextDay.length() < 4) {
				tomorrowDayStr = "0" + nextDay;
			} else {
				tomorrowDayStr = nextDay;
			}
			String nextHourStr = "";
			int nextHour = Integer.parseInt(hour) + 1; // 01 --> 2
			if (nextHour < 10) {
				nextHourStr = "0" + nextHour;
			} else {
				nextHourStr = nextHour + "";
			}

			String tomorrowStart = year + tomorrowDayStr + "000000";
			long expiredDay = fromat.parseMillis(tomorrowStart);
			String nexHourStart = yearDay + nextHourStr + "0000";
			long expiredHour = fromat.parseMillis(nexHourStart);

			// 日统计
			String dayKey = (planid + "-" + day).trim();
			long pln_day_balance_cal = memUtils.incr(dayKey);
			if (pln_day_balance_cal < 0) {
				boolean b = memUtils.set(dayKey, "1", new Date(expiredDay));
				if (b == false) {
					logger.info("===================put to memcache error! " + dayKey + "===================");
				}
			}
			
			memcacheTools.copy2Beijing(dayKey,new Date(expiredDay));
			
			// 日-分时统计
			String hourKey = (planid + "-" + day + "-h-" + hour).trim();
			long plan_hour_balance_cal = memUtils.incr(hourKey);
			if (plan_hour_balance_cal < 0) {
				boolean b = memUtils.set(hourKey, "1", new Date(expiredHour));
				if (b == false) {
					logger.info("===================put to memcache error! " + hourKey + "===================");
				}
			}
			
			memcacheTools.copy2Beijing(hourKey,new Date(expiredHour));
			
			// 日-分省统计
			String prKey = (planid + "-" + day + "-" + provinceid).trim();
			long plan_province_balance_cal = memUtils.incr(prKey);
			if (plan_province_balance_cal < 0) {
				boolean b = memUtils.set(prKey, "1", new Date(expiredDay));
				if (b == false) {
					logger.info("===================put to memcache error! " + prKey + "===================");
				}
			}
			
			memcacheTools.copy2Beijing(prKey, new Date(expiredDay));

			// -----------------------------------------mali
			// 04.25------------------
		}
		// logger.info("end playlogThreshold*******");
	}

	public static void main(String[] arg) throws ParseException {
		// Calendar calWeek = Calendar.getInstance();
		// calWeek.setTime(DateFormatTools.YYYYMMDDHHMMSS.get().parse("20160101144345"));
		// System.out.println(calWeek.get(Calendar.WEEK_OF_YEAR));
		DateTimeFormatter localfromat = DateTimeFormat.forPattern("yyyyMMddHHmmss");
		DateTime localdate = DateTime.parse("20160101144345", localfromat);
		System.out.println(localdate.getWeekOfWeekyear());

	}
}
