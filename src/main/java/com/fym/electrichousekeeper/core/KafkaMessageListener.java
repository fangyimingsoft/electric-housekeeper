package com.fym.electrichousekeeper.core;

import com.alibaba.fastjson.JSONObject;
import com.fym.electrichousekeeper.dao.DataRepository;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.entiry.po.Device;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Optional;

public class KafkaMessageListener {

    private Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private MessageParser_Old parser = new MessageParser_Old();
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DataRepository dataRepository;


    @KafkaListener(topics = "${config.topic.data}")
    public void consumer(ConsumerRecord<String,String> record){
        String message = record.value();
        long offset = record.offset();
        try {
            /*JSONObject parse = (JSONObject)JSONObject.parse(message);
            Long timestamp = parse.getLong("timestamp");
            JSONObject eventPayload = (JSONObject)parse.get("eventPayload");
            String data = eventPayload.getString("rawdata");*/
            String data = message;
            long timestamp = new Date().getTime();
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
            Date time = new Date(timestamp);
            parsedData.setTime(time);
            parsedData.setTopicOffset(new Long(offset).intValue());
            //插入
            dataRepository.save(parsedData);
            String deviceCode = parsedData.getCode();
            Optional<Device> deviceOpt = deviceRepository.findById(deviceCode);
            if(deviceOpt.isPresent()){
                Device device = deviceOpt.get();
                device.setUpdateTime(new Date());
                updateDeviceValue(device,parsedData);
                deviceRepository.save(device);
            }else{
                Device device = new Device();
                device.setName("未知设备");
                device.setCode(deviceCode);
                device.setStatus(Device.STATUS_RUNNING);
                deviceRepository.save(device);
            }
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

    private void updateDeviceValue(Device device,Data data){
        device.setCurrentA(data.getCurrentA());
        device.setCurrentB(data.getCurrentB());
        device.setCurrentC(data.getCurrentC());
        device.setVoltageA(data.getVoltageA());
        device.setVoltageB(data.getVoltageB());
        device.setVoltageC(data.getVoltageC());
        device.setTemperHA(data.getTemperHa());
        device.setTemperHB(data.getTemperHb());
        device.setTemperHC(data.getTemperHc());
        device.setTemperLA(data.getTemperLa());
        device.setTemperLB(data.getTemperLb());
        device.setTemperLC(data.getTemperLc());
        device.setTemperN(data.getTemperN());
        device.setFreq(data.getFreq());
        device.setActivePowerA(data.getActivePowerA());
        device.setActivePowerB(data.getActivePowerB());
        device.setActivePowerC(data.getActivePowerC());
        device.setActivePower(data.getActivePower());
        device.setReactivePowerA(data.getReactivePowerA());
        device.setReactivePowerB(data.getReactivePowerB());
        device.setReactivePowerC(data.getReactivePowerC());
        device.setReactivePower(data.getReactivePower());
        device.setApparentPowerA(data.getApparentPowerA());
        device.setApparentPowerB(data.getApparentPowerB());
        device.setApparentPowerC(data.getApparentPowerC());
        device.setApparentPower(data.getApparentPower());
        device.setPowerFactorA(data.getPowerFactorA());
        device.setPowerFactorB(data.getPowerFactorB());
        device.setPowerFactorC(data.getPowerFactorC());
        device.setPowerFactor(data.getPowerFactor());
        device.setVoltageHarmA(data.getVoltageHarmA());
        device.setVoltageHarmB(data.getVoltageHarmB());
        device.setVoltageHarmC(data.getVoltageHarmC());
        device.setCurrentHarmA(data.getCurrentHarmA());
        device.setCurrentHarmB(data.getCurrentHarmB());
        device.setCurrentHarmC(data.getCurrentHarmC());
    }
}
