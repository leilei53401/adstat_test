package com.voole.ad.ipcache;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.google.common.collect.Range;
import com.google.common.collect.RangeMap;
import com.google.common.collect.TreeRangeMap;

/**
 * IP缓存DAO
 * 
 * @author shaoyl
 * 
 */
@Component("IpCacheDao")
public class IpCacheDao {
	@Resource(name = "xdataJt")
	private JdbcTemplate xdataJt;
	private static Logger logger = Logger.getLogger(IpCacheDao.class);

	/**
	 * 缓存默认广协IP地址库
	 * 
	 * @return
	 */
	public RangeMap<Long, Integer[]> loadDefaultIpAndArea() {
		final RangeMap<Long, Integer[]> rangeMap = TreeRangeMap.create();
		
		final AtomicLong at=new AtomicLong(0l);
		
		try {
			String sql = "SELECT a.provinceid,a.provincename,a.areaid,a.netseg1dec,a.netseg2dec,ar.name cityname,ar.code cityid FROM "
					+ "	(SELECT pr.code provinceid,pr.name provincename,ip.areaid,netseg1dec,netseg2dec"
					+ "	 FROM ad_ip_topology ip,ad_ip_province pr"
					+ "	 WHERE ip.provinceid = pr.provinceid )"
					+ " a LEFT JOIN ad_ip_area ar ON a.areaid=ar.areaid ";
			
			
			long start = System.currentTimeMillis();

			xdataJt.query(sql, new RowMapper<Object>() {
				@Override
				public Object mapRow(ResultSet rs, int rownum) {
					try {
						Integer[] data = { rs.getInt("provinceid"),
								rs.getInt("cityid") };
						long netseg1dec = rs.getLong("netseg1dec");
						long netseg2dec = rs.getLong("netseg2dec");
						if (netseg1dec < netseg2dec) {
							rangeMap.put(Range.closed(netseg1dec, netseg2dec), data);
						}
						
						at.getAndIncrement();
						
					} catch (SQLException e) {
						logger.error("处理第["+at.get()+"]条出错：",e);
					}

					return null;
				}
			});
			
			long end = System.currentTimeMillis();
			
			logger.info("加载广协IP地址库【"+at.get()+"】条!耗时["+(end-start)+"]毫秒!");
		} catch (Exception e) {
			logger.error("加载ip区域异常", e);
		}
		return rangeMap;
	}
	
	/**
	 * 获取自定义IP地址库的厂家 
	 * @param ven
	 * @return
	 */
	public List<String> loadCustomIpVen() {
		
		List<String>  venList = new ArrayList<String>();
		
		try {
			String sql = "Select ven_no from adap_vendor t where custom_ip_database=1";

			venList = xdataJt.query(sql, new RowMapper<String>() {
				@Override
				public String mapRow(ResultSet rs, int rownum)
						throws SQLException {
					int ven = rs.getInt("ven_no");
					return ven + "";
				}
			});
		} catch (Exception e) {
			logger.error("加载自定义ip库B2B厂家出错", e);
		}
		
		return venList;

	}

	
	/**
	 * 缓存自定义厂家的IP地址库
	 * @param ven
	 * @return
	 */
	public RangeMap<Long, Integer[]> loadCustomIpAndAreaByVen(String ven) {
		
		final RangeMap<Long, Integer[]> rangeMap = TreeRangeMap.create();
		final AtomicLong at=new AtomicLong(0l);
		try {
			
			String sql = "Select * from adap_b2b_ip_topology t where t.ven = " + ven ;

			xdataJt.query(sql, new RowMapper<Object>() {
				@Override
				public Object mapRow(ResultSet rs, int rownum)
						throws SQLException {

					Integer[] data = { rs.getInt("provincecode"),
							rs.getInt("areacode") };
					long netseg1dec = rs.getLong("netseg1dec");
					long netseg2dec = rs.getLong("netseg2dec");
					if (netseg1dec < netseg2dec) {
						rangeMap.put(Range.closed(netseg1dec, netseg2dec), data);
					}
					//计数
					at.getAndIncrement();

					return null;
				}
			});
			
		logger.info("加载【"+ven+"】IP地址库【"+at.get()+"】条!");
		} catch (Exception e) {
			logger.error("加载ip区域异常", e);
		}
		
		return rangeMap;

	}
   
	/**
	 * 缓存自定义区域编码的厂家
	 * @param ven
	 * @return
	 */
	public List<String> loadCustomAreaCodeVen() {
		List<String> venno=new ArrayList<>();
		String sql="Select ven_no from adap_vendor t where custom_ip_database=2";
		venno=xdataJt.query(sql, new RowMapper<String>(){
			
			@Override
			public String mapRow(ResultSet rs, int rowNum)
					throws SQLException {
				int ven=rs.getInt("ven_no");
				return ven+"";
			}
			});
		return venno;
	}


	/**
	 * 缓存自定义厂家的区域编码
	 * @param ven
	 * @return
	 */
	public Map<String,String[]> loadCustomAreaCodeByVen(String ven) {
		final Map<String, String[]> areaCodeMap=new HashMap<>();
		String sql="Select voole_provinceid,voole_cityid,ven_cityid from adap_b2b_areacode_topology where "+
		           "ven="+ven;
		xdataJt.query(sql, new RowMapper<Object>(){

			@Override
			public Object mapRow(ResultSet rs, int rowNum) throws SQLException {
				String ven_cityid=rs.getString("ven_cityid");
				String[] vooleid={rs.getString("voole_provinceid"),rs.getString("voole_cityid")};
				areaCodeMap.put(ven_cityid, vooleid);
				return null;
			}
			
		});
		return areaCodeMap;
	}
}
