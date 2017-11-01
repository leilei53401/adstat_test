package com.voole.ad.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 
 * 播放日志实体bean
 * @author Administrator
 *
 */
public class PlayLogBean implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -2253273248717335673L;
	private String logid;
	// sessionid
	private String sessionid;

	// 广告节目id
	private String amid;
	private String ip;
	// 广告主编号
	private String adverno;
	// 代理商编号
	private String agentno;
	// 运营商编号
	private String spid;
	// oem编号
	private String oemid;
	// 是否发送广告联盟 0:不发送 (1或者没有这个字段):发送
	private String isadap;
	// 终端编号
	private String hid;
	// 投放区域
	private String provinceid;
	// 投放区域
	private String cityid;
	// 投放地段
	// 播放频道id
	private String channelid;
	//播放栏目id
	private String programid;
	// 播放开始时间
	private String starttime;
	// 播放结束时间
	private String endtime;
	// 广告介质fid
	private String fid;
	//广告位置id
	private String adposid;
	//请求用户id
	private String uid;
	
	private String type;
	//mergertype
	private String admt;
	//排期id
	private String planid;
	private String pv;
	private String uv;
	private String vv;
	public String getAdmt() {
		return admt;
	}
	public String getAdposid() {
		return adposid;
	}
	public String getAdverno() {
		return adverno;
	}
	public String getAgentno() {
		return agentno;
	}
	public String getAmid() {
		return amid;
	}
	public String getChannelid() {
		return channelid;
	}
	public String getCityid() {
		return cityid;
	}
	public String getEndtime() {
		return endtime;
	}
	public String getFid() {
		return fid;
	}
	public String getHid() {
		return hid;
	}
	public String getIp() {
		return ip;
	}
	public String getIsadap() {
		return isadap;
	}
	public String getLogid() {
		return logid;
	}
	public String getOemid() {
		return oemid;
	}
	public String getPlanid() {
		return planid;
	}
	public String getProgramid() {
		return programid;
	}
	public String getProvinceid() {
		return provinceid;
	}
	public String getPv() {
		return pv;
	}
	public String getSessionid() {
		return sessionid;
	}
	public String getSpid() {
		return spid;
	}
	public String getStarttime() {
		return starttime;
	}
	public String getType() {
		return type;
	}
	public String getUid() {
		return uid;
	}
	public String getUv() {
		return uv;
	}
	public String getVv() {
		return vv;
	}
	public void setAdmt(String admt) {
		this.admt = admt;
	}
	public void setAdposid(String adposid) {
		this.adposid = adposid;
	}
	public void setAdverno(String adverno) {
		this.adverno = adverno;
	}
	public void setAgentno(String agentno) {
		this.agentno = agentno;
	}
	public void setAmid(String amid) {
		this.amid = amid;
	}
	public void setChannelid(String channelid) {
		this.channelid = channelid;
	}
	public void setCityid(String cityid) {
		this.cityid = cityid;
	}
	public void setEndtime(String endtime) {
		this.endtime = endtime;
	}
	public void setFid(String fid) {
		this.fid = fid;
	}
	public void setHid(String hid) {
		this.hid = hid;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public void setIsadap(String isadap) {
		this.isadap = isadap;
	}
	public void setLogid(String logid) {
		this.logid = logid;
	}
	public void setOemid(String oemid) {
		this.oemid = oemid;
	}
	public void setPlanid(String planid) {
		this.planid = planid;
	}
	public void setProgramid(String programid) {
		this.programid = programid;
	}
	public void setProvinceid(String provinceid) {
		this.provinceid = provinceid;
	}
	public void setPv(String pv) {
		this.pv = pv;
	}
	public void setSessionid(String sessionid) {
		this.sessionid = sessionid;
	}
	public void setSpid(String spid) {
		this.spid = spid;
	}
	public void setStarttime(String starttime) {
		this.starttime = starttime;
	}
	public void setType(String type) {
		this.type = type;
	}
	public void setUid(String uid) {
		this.uid = uid;
	}
	public void setUv(String uv) {
		this.uv = uv;
	}
	public void setVv(String vv) {
		this.vv = vv;
	}
	
	@Override
	public String toString() {
		return "PlayLogBean [logid=" + logid + ", sessionid=" + sessionid + ", amid=" + amid + ", adverno=" + adverno + ", agentno=" + agentno + ", spid=" + spid + ", oemid=" + oemid + ", hid=" + hid
				+ ", provinceid=" + provinceid + ", cityid=" + cityid + ", channelid=" + channelid + ", programid=" + programid + ", starttime=" + starttime + ", endtime=" + endtime + ", fid=" + fid
				+ ", adposid=" + adposid + ", uid=" + uid + ", type=" + type + ", admt=" + admt + ", planid=" + planid + ", pv=" + pv + ", uv=" + uv + ", vv=" + vv + "]";
	}

}
