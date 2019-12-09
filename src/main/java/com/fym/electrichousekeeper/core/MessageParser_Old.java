package com.fym.electrichousekeeper.core;

import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.service.DeviceService;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.StringJoiner;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Stream;

/**
 * kafka报文解析
 * 报文长度：287byte,均为ascii码
 * 1：#
 * 2~12：设备编号
 * 13~14:
 * 15~49:
 */
public class MessageParser_Old {

    /**
     * 消息长度
     */
    public static final int MESSAGE_LENGTH = 287;

    /**
     * 存在问题：
     * 1.电流-》没有乘以CT
     * 2.电压-》
     * @param message
     * @return
     */
    public Data parse(String message){
        if(message == null){
            throw new NullPointerException();
        }
        if(message.length() != MESSAGE_LENGTH){
            throw new IllegalArgumentException("消息长度错误：" + message.length() + ",长度应为：" + MESSAGE_LENGTH);
        }
        AtomicInteger strPos = new AtomicInteger(0);
        Consumer<Integer> posConsumer = strPos::set;
        Data data = new Data();
        //1  起始符#
        String startFrame = substrWithLength(message, strPos.get(), 1, posConsumer);
        data.setStartChar(startFrame);

        //2~12  设备编号
        String deviceCode = substrWithLength(message, strPos.get(), 11, posConsumer);
        data.setCode(deviceCode);

        Double ct = getCT(deviceCode);

        //13~14  帧类型，默认是00
        String frameType = substrWithLength(message, strPos.get(), 2, posConsumer);
        data.setFrameType(frameType);

        //15~49  7路温度(每种温度长度为5；01123代表+112.3度)
        String temperStr7 = substrWithLength(message, strPos.get(), 35, posConsumer);
        List<Double> tempers = new ArrayList<>(7);
        /*for(int i = 0;i < 7;i++){
            String temperStr = temperStr7.substring(i * 5, (i + 1) * 5);
            Double firstBit = parseToDouble(temperStr.substring(0, 1));
            Double middleBits = parseToDouble(temperStr.substring(1,4));
            Double lastBit = parseToDouble(temperStr.substring(4,5));
            boolean isPositive = firstBit != 0D;
            Double temper =  (isPositive ? 1d : -1d) * middleBits + (lastBit / 10d);
            tempers.add(temper);
        }*/

        for(int i = 0;i < 7;i++){
            String temperStr = temperStr7.substring(i * 5, (i + 1) * 5);
            Double firstBit = parseToDouble(temperStr.substring(0, 1));
            double temper;
            if (firstBit >0){
                int w = (Integer.valueOf(temperStr.charAt(1))-48)*1000;
                temper=(w+parseToDouble(temperStr.substring(2,5))-32768)/10;
            }else{
                temper= parseToDouble(temperStr.substring(1,5))/10;
                if (temper >3200) temper=0.0; // 数据文档不清楚，按0处理
            }
            //Double middleBits = parseToDouble(temperStr.substring(1,4));
            //Double lastBit = parseToDouble(temperStr.substring(4,5));
            //boolean isPositive = firstBit != 0D;
            //Double temper =  (isPositive ? 1d : -1d) * middleBits + (lastBit / 10d);
            tempers.add(temper);
        }
        //------------------------------------------------------------
        data.setTemperHa(tempers.get(0));
        data.setTemperHb(tempers.get(1));
        data.setTemperHc(tempers.get(2));
        data.setTemperLa(tempers.get(3));
        data.setTemperLb(tempers.get(4));
        data.setTemperLc(tempers.get(5));
        data.setTemperN(tempers.get(6));
        //50~54     电力系数
        String powerFactorStr = substrWithLength(message, strPos.get(), 3, posConsumer);
        //功率的正负,暂时不计算
        String factorPole = substrWithLength(message,strPos.get(),2,posConsumer);
        Double voltageFactor = Math.pow(10D,Double.valueOf(String.valueOf(powerFactorStr.charAt(0))) - 4);
        Double currentFactor = Math.pow(10D,Double.valueOf(String.valueOf(powerFactorStr.charAt(1))) - 4);
        Double powerFactor = 10D  * Math.pow(10D,Double.valueOf(String.valueOf(powerFactorStr.charAt(2))) - 4);
        data.setPowerFactorStr(powerFactorStr);
        //55~66     三相电压
        String voltageStr = substrWithLength(message, strPos.get(), 12, posConsumer);
        List<Double> voltages = getGroupDoubleValue(voltageStr,3,4);
        data.setVoltageA(voltages.get(0) * voltageFactor);
        data.setVoltageB(voltages.get(1) * voltageFactor);
        data.setVoltageC(voltages.get(2) * voltageFactor);

        //67~78     三相电流
        String currentStr = substrWithLength(message, strPos.get(), 12, posConsumer);
        List<Double> currents = getGroupDoubleValue(currentStr,3,4);
        data.setCurrentA(currents.get(0) * currentFactor * ct);
        data.setCurrentB(currents.get(1) * currentFactor * ct);
        data.setCurrentC(currents.get(2) * currentFactor * ct);

        //79~94     有功功率(PA PB PC 总)
        String activePowerStr = substrWithLength(message, strPos.get(), 16, posConsumer);
        List<Double> activePowers = getGroupDoubleValue(activePowerStr,4,4);
        data.setActivePowerA(activePowers.get(0)/1000 * powerFactor * ct);
        data.setActivePowerB(activePowers.get(1)/1000 * powerFactor * ct);
        data.setActivePowerC(activePowers.get(2)/1000 * powerFactor * ct);
        data.setActivePower(activePowers.get(3)/1000 * powerFactor * ct);


        //95~110    无功功率(QA QB QC 总)
        String reactivePowerStr = substrWithLength(message, strPos.get(), 16, posConsumer);
        List<Double> reactivePowers = getGroupDoubleValue(reactivePowerStr,4,4);
        data.setReactivePowerA(reactivePowers.get(0)/1000 * powerFactor * ct);
        data.setReactivePowerB(reactivePowers.get(1)/1000 * powerFactor * ct);
        data.setReactivePowerC(reactivePowers.get(2)/1000 * powerFactor * ct);
        data.setReactivePower(reactivePowers.get(3)/1000 * powerFactor * ct);

        //功率因数
        String pfStr = substrWithLength(message,strPos.get(),16,posConsumer);
        List<Double> powerFactorList = getGroupDoubleValue(pfStr,4,4);
        data.setPowerFactorA(powerFactorList.get(0)/1000);
        data.setPowerFactorB(powerFactorList.get(1)/1000);
        data.setPowerFactorC(powerFactorList.get(2)/1000);
        data.setPowerFactor(powerFactorList.get(3)/1000);


        //111~126   视在功率(SA SB SC 总)
        String apparentPowerStr = substrWithLength(message, strPos.get(), 16, posConsumer);
        List<Double> apparentPowers = getGroupDoubleValue(apparentPowerStr,4,4);
        data.setApparentPowerA(apparentPowers.get(0) * powerFactor * ct / 1000);
        data.setApparentPowerB(apparentPowers.get(1) * powerFactor * ct / 1000);
        data.setApparentPowerC(apparentPowers.get(2) * powerFactor * ct / 1000);
        data.setApparentPower(apparentPowers.get(3) * powerFactor * ct / 1000);

        //127~130   频率（3456 //34.56）
        String frequencyStr = substrWithLength(message,strPos.get(),4,posConsumer);
        data.setFreq(Double.valueOf(frequencyStr) / 100D);

        //131~142   电压谐波(三相)
        String voltageHarmStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> voltageHarms = getGroupDoubleValue(voltageHarmStr, 3, 4);
        data.setVoltageHarmA(voltageHarms.get(0));
        data.setVoltageHarmB(voltageHarms.get(1));
        data.setVoltageHarmC(voltageHarms.get(2));

        //143~154   电流谐波(三相)
        String currentHarmStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> currentHarms = getGroupDoubleValue(currentHarmStr, 3, 4);
        data.setCurrentHarmA(currentHarms.get(0));
        data.setCurrentHarmB(currentHarms.get(1));
        data.setCurrentHarmC(currentHarms.get(2));

        //155~166   电压畸变率(三相)
        String voltageDistortionRateStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> voltageDistortionRate = getGroupDoubleValue(voltageDistortionRateStr,3,4);
        data.setVoltageDistA(voltageDistortionRate.get(0));
        data.setVoltageDistB(voltageDistortionRate.get(1));
        data.setVoltageDistC(voltageDistortionRate.get(2));

        //167~178   电流畸变率(三相)
        String currentDistortionRateStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> currentDistortionRate = getGroupDoubleValue(currentDistortionRateStr,3,4);
        data.setCurrentDistA(currentDistortionRate.get(0));
        data.setCurrentDistB(currentDistortionRate.get(1));
        data.setCurrentDistC(currentDistortionRate.get(2));

        //179~186   吸收有功电能二次侧（全数字）
        String absorbStr = substrWithLength(message,strPos.get(),8,posConsumer);
        Double absorb = parseToDouble(absorbStr);
        data.setSecondaryAbsorbActive(absorb);

        //187~194   释放有功电能二次侧
        String releaseStr = substrWithLength(message,strPos.get(),8,posConsumer);
        Double release = parseToDouble(releaseStr);
        data.setSecondaryReleaseActive(release);


        //195~202   感性无功电能二次侧
        String inductiveStr = substrWithLength(message,strPos.get(),8,posConsumer);
        Double inductive = parseToDouble(inductiveStr);
        data.setSecondaryInductiveReactive(inductive);

        //203~210   容性无功电能二次侧
        String capacityStr = substrWithLength(message,strPos.get(),8,posConsumer);
        Double capacity = parseToDouble(capacityStr);
        data.setSecondaryCapacitiveReactive(capacity);

        //211~218   吸收有功电能一次侧（全数字）
        absorbStr = substrWithLength(message,strPos.get(),8,posConsumer);
        absorb = parseToDouble(absorbStr);
        data.setPrimaryAbsorbActive(absorb);

        //219~226   释放有功电能一次侧
        releaseStr = substrWithLength(message,strPos.get(),8,posConsumer);
        release = parseToDouble(releaseStr);
        data.setPrimaryReleaseActive(release);

        //227~234   感性无功电能一次侧
        inductiveStr = substrWithLength(message,strPos.get(),8,posConsumer);
        inductive = parseToDouble(inductiveStr);
        data.setPrimaryInductiveReactive(inductive);


        //235~242   容性无功电能一次侧
        capacityStr = substrWithLength(message,strPos.get(),8,posConsumer);
        capacity = parseToDouble(capacityStr);
        data.setPrimaryCapacitiveReactive(capacity);

        //255~266   纬度
        String wd = substrWithLength(message,strPos.get(),12,posConsumer);
        data.setLatitude(wd);

        //243~254   经度
        String jd = substrWithLength(message,strPos.get(),12,posConsumer);
        data.setLongitude(jd);

        //267
        String gpsStr = substrWithLength(message, strPos.get(), 1, posConsumer);
        data.setGpsEffective("1".equals(gpsStr) ? 1 : 0);

        //268~271
        String checkDigit = substrWithLength(message,strPos.get(),4,posConsumer);
        data.setCheckDigit(checkDigit);
        return data;
    }

    /**
     *
     * @param str
     * @param startPosition 字符串的起始位置为1
     * @param length
     * @return
     */
    private String substrWithLength(String str, int startPosition, int length, Consumer<Integer> nextPosition){
        if(nextPosition != null){
            nextPosition.accept(startPosition + length);
        }
        return str.substring(startPosition,startPosition + length);
    }

    private List<Double> getGroupDoubleValue(String str,int groupCount,int length){
        return getGroupDoubleValue(str,groupCount,length,Double::valueOf);
    }

    private List<Double> getGroupDoubleValue(String str, int groupCount, int length, Function<String,Double> strToDouble){
        if(str.length() != groupCount * length){
            throw new IllegalArgumentException("字符串无法拆分成" + groupCount + "组长度为" + length + "的子字符串");
        }
        List<Double> group = new ArrayList<>(groupCount);
        for(int i = 0;i < groupCount;i++){
            String substr = str.substring(i * length,(i + 1) * length);
            group.add(strToDouble.apply(substr));
        }
        return group;
    }

    private Double getVoltageOrCurrent(Double value,int factor){
        return  value * 0.1D * Math.pow(10D,factor - 4D);
    }

    private Double powerFactor(Double power,int factor){
        return  power * 10D * Math.pow(10D,factor - 4D);
    }

    private Double getCT(String deviceCode){
        Integer deviceCt = DeviceService.getDeviceCt(deviceCode);
        if(deviceCt != null){
            return deviceCt * 1D;
        }
        return 1D;
    }

    private Double parseToDouble(String asciiStr){
        StringBuilder sb = new StringBuilder();
        asciiStr.chars().forEach(ch->{
            int number = ch;
            sb.append(String.valueOf(number - 48));
        });
        return Double.valueOf(sb.toString());
    }
}
