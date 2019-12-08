package com.fym.electrichousekeeper.core;

import com.alibaba.fastjson.JSONObject;
import com.fym.electrichousekeeper.dao.DataRepository;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.dao.WarningRepository;
import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.entiry.po.Device;
import com.fym.electrichousekeeper.entiry.po.Warning;
import com.fym.electrichousekeeper.service.ThresholdService;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.tomcat.util.buf.HexUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KafkaMessageListener {

    private Logger logger = LoggerFactory.getLogger(KafkaMessageListener.class);

    DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    private MessageParser_Old parser = new MessageParser_Old();

    private  static WarningAlgorithm algorithm = new WarningAlgorithm();
    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private ThresholdService thresholdService;

    @Autowired
    private WarningRepository warningRepository;


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
            String deviceCode = parsedData.getCode();
            Optional<Device> deviceOpt = deviceRepository.findById(deviceCode);
            if(deviceOpt.isPresent()){
                Device device = deviceOpt.get();
                device.setUpdateTime(new Date());
                updateDeviceValue(device,parsedData);
                deviceRepository.save(device);
                parsedData.setDeviceName(device.getName());
            }else{
                Device device = new Device();
                device.setName("未知设备");
                device.setCode(deviceCode);
                device.setStatus(Device.RUNNING);
                deviceRepository.save(device);
                parsedData.setDeviceName("未知设备");
            }
            dataRepository.save(parsedData);
            List<Warning> warning = warning(parsedData);
            if(warning.size() > 0){
                warningRepository.saveAll(warning);
            }
        } catch (Exception e) {
            e.printStackTrace();
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
        device.setTemperHa(data.getTemperHa());
        device.setTemperHb(data.getTemperHb());
        device.setTemperHc(data.getTemperHc());
        device.setTemperLa(data.getTemperLa());
        device.setTemperLb(data.getTemperLb());
        device.setTemperLc(data.getTemperLc());
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

    private List<Warning> warning(Data parsedData){
        String deviceCode = parsedData.getCode();
        int capacity = getCapacity(deviceCode);
        List<String> type = new ArrayList<>();
        //异常研判
        double overLoadPercent = thresholdService.getCurrentOverLoadPercent(deviceCode);
        double lowVoltageStandard = thresholdService.getLowVoltageStandard(deviceCode);
        double threePhaseUnbalance = thresholdService.getThreePhaseUnbalance(deviceCode);

        boolean overLoadA = algorithm.currentOverLoad(parsedData.getCurrentA(), capacity, overLoadPercent);//电流过载
        boolean overLoadB = algorithm.currentOverLoad(parsedData.getCurrentB(),capacity,overLoadPercent);//电流过载
        boolean overLoadC = algorithm.currentOverLoad(parsedData.getCurrentC(),capacity,overLoadPercent);//电流过载
        List<String> phaseCurrent = algorithm.lackPhase(parsedData.getCurrentA(), parsedData.getCurrentB(), parsedData.getCurrentC());//缺相报警
        List<String> phaseVoltage = algorithm.lackPhase(parsedData.getVoltageA(),parsedData.getVoltageB(),parsedData.getVoltageC());//缺相报警
        boolean lowA = algorithm.lowVoltage(parsedData.getVoltageA(),lowVoltageStandard);//低电压
        boolean lowB = algorithm.lowVoltage(parsedData.getVoltageB(),lowVoltageStandard);//低电压
        boolean lowC = algorithm.lowVoltage(parsedData.getVoltageC(), lowVoltageStandard);//低电压
        boolean unbalance = algorithm.threePhaseUnbalance(parsedData.getCurrentA(), parsedData.getCurrentB(), parsedData.getCurrentC(), threePhaseUnbalance);//三项不平衡
        List<String> overTemper = algorithm.overTemperature(parsedData.getTemperHa(), parsedData.getTemperHb(), parsedData.getTemperHc(),
                parsedData.getTemperLa(), parsedData.getTemperLb(), parsedData.getTemperLc(), parsedData.getTemperN(),
                thresholdService.getOverTemperStandard(deviceCode));
        if(overLoadA && capacity > 0){
            type.add("A相过负荷");
        }
        if(overLoadB && capacity > 0){
            type.add("B相过负荷");
        }
        if(overLoadC && capacity > 0){
            type.add("C相过负荷");
        }
        phaseCurrent.forEach(item->type.add("电流缺" + item));
        phaseVoltage.forEach(item->type.add("电压缺" + item));
        if(lowA){
            type.add("A相低电压");
        }
        if(lowB){
            type.add("B相低电压");
        }
        if(lowC){
            type.add("C相低电压");
        }
        if(unbalance){
            type.add("三相平衡");
        }
        if(overTemper.size() > 0){
            overTemper.forEach(desc->type.add(desc + "超温"));
        }
        return type.stream().map(typeStr->{
            Warning warning = new Warning();
            warning.setDataId(parsedData.getId());
            warning.setDeviceCode(deviceCode);
            warning.setStatus(Warning.STATUS_CREATED);
            warning.setTime(parsedData.getTime());
            warning.setWarningType(typeStr);
            warning.setDeviceName(parsedData.getDeviceName());
            return warning;
        }).collect(Collectors.toList());
    }

    private int getCapacity(String code){
        return 0;
    }
}
