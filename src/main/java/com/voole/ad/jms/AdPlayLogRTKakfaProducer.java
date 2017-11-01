package com.voole.ad.jms;

import javax.annotation.Resource;

import kafka.javaapi.producer.Producer;
import kafka.producer.KeyedMessage;
import net.sf.json.JSONObject;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.format.DateTimeFormat;
import org.joda.time.format.DateTimeFormatter;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import com.voole.ad.model.AdInfo;
import com.voole.ad.model.PlayLogBean;
import com.voole.ad.utils.BeanAvroUtils;
import com.voole.ad.utils.GlobalProperties;
import com.voole.ad.utils.ThreadPool;



@Component
public class AdPlayLogRTKakfaProducer {
    private Logger logger = Logger.getLogger(AdPlayLogRTKakfaProducer.class);
    private static final String adap_topic = GlobalProperties.getProperties("adap_adst_topic");
    
    private ThreadPool threadPool;//线程池

    @Resource
    private KafkaProducer kafkaproducer;


    public AdPlayLogRTKakfaProducer() {
        this.threadPool = new ThreadPool(5, 10, 1000, 1000);
    }
    
    
    /**
     *播放日志实时发送
     * @param playlog
     */
    public void sendPlaylog(PlayLogBean playlog){
        if(playlog != null){
            AdInfo adInfo = new AdInfo();
            final JSONObject jsonData = new JSONObject();
            adInfo.setHid(playlog.getHid());
            DateTimeFormatter format = DateTimeFormat.forPattern("yyyyMMddHHmmss");  
            adInfo.setTs(DateTime.parse(playlog.getStarttime(),format).getMillis());
            adInfo.setStamp(System.currentTimeMillis());
            adInfo.setVen(900100L);
            adInfo.setIp(playlog.getIp() == null ? "" : playlog.getIp());
            adInfo.setAmid(Long.parseLong(playlog.getAmid()==null?"0":playlog.getAmid()));
            adInfo.setMac(playlog.getHid()==null?"": playlog.getHid());
            adInfo.setD(5L);
            adInfo.setDevid(playlog.getHid()==null?"": playlog.getHid());
            adInfo.setSize(40L);
            if(playlog.getChannelid() != null && StringUtils.isNumeric(playlog.getChannelid())){
            	adInfo.setChannelid(Integer.parseInt(playlog.getChannelid().toString()));
            } else {
            	adInfo.setChannelid(-1);
            }
            if(playlog.getProgramid() != null && StringUtils.isNumeric(playlog.getProgramid())){
            	adInfo.setProgramid(Integer.parseInt(playlog.getProgramid().toString()));
            }else{
            	adInfo.setProgramid(-1);
            }
            //2016-07-13 by mali add adpos and te
            adInfo.setTe(0);
            adInfo.setAreatype(1);
            if(playlog.getCityid() != null && StringUtils.isNumeric(playlog.getCityid())){
            	adInfo.setCityid(Integer.parseInt(playlog.getCityid()));
            }else{
            	adInfo.setCityid(0);
            }
            if(playlog.getProvinceid() != null && StringUtils.isNumeric(playlog.getProvinceid())){
            	adInfo.setProvinceid(Integer.parseInt(playlog.getProvinceid()));
            }else{
            	adInfo.setProvinceid(0);
            }
            adInfo.setPos(playlog.getAdposid());
            final byte[] serializedObj = BeanAvroUtils.toAvro(adInfo);
            threadPool.execute(new Runnable() {
                @Override
                public void run() {
                    //kafka发送
                    try {
                        sendMsg(adap_topic,serializedObj);
                    } catch (Exception e) {
                        logger.error("AdPlayLogNielsenKakfaProducer send playlog to third kakfa exception,exception is ==", e);
                    }
                }
            });
        }
            
    }
    
    
    /**
     * 发送消息
     * 
     * @param topic
     * @param msg
     * @param msgtoSchema
     * @throws Exception 
     */
    public <T> void sendMsg(String topic, byte[] serializedObj) throws Exception {
        //发送需重新实现
        try {
            Producer<byte[], byte[]> producer = kafkaproducer.getProducer();
            if (producer != null) {
                //KeyedMessage<String,String> data = new KeyedMessage<String,String>(topic, info,info);
                KeyedMessage<byte[], byte[]> data = new KeyedMessage<byte[], byte[]>(topic, null, serializedObj);
                producer.send(data);
                if(logger.isDebugEnabled()){
                    logger.debug("=======kafka message is sended==============");
                }
            }
        } catch (Exception e) {
            logger.error(e,e.fillInStackTrace());
            throw e;
        }
    }

}
