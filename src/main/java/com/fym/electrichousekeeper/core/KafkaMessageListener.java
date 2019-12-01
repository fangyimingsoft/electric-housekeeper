package com.fym.electrichousekeeper.core;

import com.alibaba.fastjson.JSONObject;
import com.fym.electrichousekeeper.dao.DataRepository;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.entiry.po.Data;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class KafkaMessageListener {

    private Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private MessageParser_Old parser = new MessageParser_Old();

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private DeviceRepository deviceRepository;

    @KafkaListener(topics = "iot_data_liaoyang")
    public void consumer(ConsumerRecord<String,String> record){
        String message = record.value();
        long offset = record.offset();
        try {
            JSONObject parse = (JSONObject)JSONObject.parse(message);
            Long timestamp = parse.getLong("timestamp");
            JSONObject eventPayload = (JSONObject)parse.get("eventPayload");
            String data = eventPayload.getString("rawdata");
            if(data.length() != 574){
                logger.error("消息长度异常：offset : " + offset + ",消息长度：" + data.length());
                return;
            }
            StringBuilder str = new StringBuilder();
            for(int i = 0;i < MessageParser_Old.MESSAGE_LENGTH * 2;i+=2){
                String bit = data.substring(i,i+2);
                str.append(toAsciiChar(bit));
            }
            String parsedMessage = str.toString();
            Data parsedData = parser.parse(parsedMessage);
            Date time = new Date(timestamp * 1000L);
            parsedData.setTime(time);
            parsedData.setTopicOffset(new Long(offset).intValue());
            //插入
            dataRepository.save(parsedData);
        } catch (Exception e) {
            logger.error(e.toString());
        }
    }

    private char toAsciiChar(String hexStr){
        byte[] bytes = HexUtils.fromHexString(hexStr);
        byte aByte = bytes[0];
        int number = aByte > 0 ? aByte : (127 + 129 - Math.abs(aByte));
        return (char) number;
    }
}
