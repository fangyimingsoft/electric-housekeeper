package com.fym.electrichousekeeper.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
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

    public Message_Old parse(String message){
        if(message == null){
            throw new NullPointerException();
        }
        if(message.length() != MESSAGE_LENGTH){
            throw new IllegalArgumentException("消息长度错误：" + message.length() + ",长度应为：" + MESSAGE_LENGTH);
        }
        AtomicInteger strPos = new AtomicInteger(1);
        Consumer<Integer> posConsumer = strPos::set;
        Message_Old msg = new Message_Old();
        //1  起始符#
        String startFrame = substrWithLength(message, strPos.get(), 1, posConsumer);
        msg.setStartFrame(startFrame);

        //2~12  设备编号
        String deviceCode = substrWithLength(message, strPos.get(), 11, posConsumer);
        msg.setDeviceCode(deviceCode);

        //13~14  帧类型，默认是00
        String frameType = substrWithLength(message, strPos.get(), 2, posConsumer);
        msg.setFrameType(frameType);

        //15~49  7路温度(每种温度长度为5；01123代表+112.3度)
        String temperStr7 = substrWithLength(message, strPos.get(), 35, posConsumer);
        List<Double> tempers = new ArrayList<>(7);
        for(int i = 0;i < 5;i++){
            String temperStr = temperStr7.substring(i * 5, (i + 1) * 5);
            boolean isPositive = temperStr.startsWith("0");
            String decimal = String.valueOf(temperStr.charAt(4));
            Double temper =  (isPositive ? 1d : -1d) * Double.valueOf(temperStr.substring(1,4)) + (Double.valueOf(decimal) / 10d);
            tempers.add(temper);
        }
        msg.setTemperatures(tempers);

        //50~54     电力系数
        String powerFactorStr = substrWithLength(message, strPos.get(), 5, posConsumer);
        List<Double> powerFactors = Arrays.asList(
                0.1D  * Math.pow(10,Double.valueOf(String.valueOf(powerFactorStr.charAt(0))) - 4),
                0.1D  * Math.pow(10,Double.valueOf(String.valueOf(powerFactorStr.charAt(1))) - 4),
                0.1D  * Math.pow(10,Double.valueOf(String.valueOf(powerFactorStr.charAt(2))) - 4),
                Integer.valueOf(String.valueOf(powerFactorStr.charAt(3))) == 0 ? 1D : -1D,
                Integer.valueOf(String.valueOf(powerFactorStr.charAt(4))) == 0 ? 1D : -1D
        );
        msg.setPowerFactors(powerFactors);

        //55~66     三相电压
        String voltageStr = substrWithLength(message, strPos.get(), 12, posConsumer);
        List<Double> voltages = getGroupDoubleValue(voltageStr,3,4);
        msg.setVoltage(voltages);

        //67~78     三相电流
        String currentStr = substrWithLength(message, strPos.get(), 12, posConsumer);
        List<Double> currents = getGroupDoubleValue(currentStr,3,4);
        msg.setCurrent(currents);

        //79~94     有功功率(PA PB PC 总)
        String activePowerStr = substrWithLength(message, strPos.get(), 16, posConsumer);
        List<Double> activePowers = getGroupDoubleValue(activePowerStr,4,4);
        msg.setActivePower(activePowers);

        //95~110    无功功率(QA QB QC 总)
        String reactivePowerStr = substrWithLength(message, strPos.get(), 16, posConsumer);
        List<Double> reactivePowers = getGroupDoubleValue(reactivePowerStr,4,4);
        msg.setReactivePower(reactivePowers);

        //111~126   视在功率(SA SB SC 总)
        String apparentPowerStr = substrWithLength(message, strPos.get(), 16, posConsumer);
        List<Double> apparentPowers = getGroupDoubleValue(apparentPowerStr,4,4);
        msg.setApparentPower(apparentPowers);

        //127~130   频率（3456 //34.56）
        String frequencyStr = substrWithLength(message,strPos.get(),4,posConsumer);
        msg.setFrequency(Double.valueOf(startFrame) / 100D);

        //131~142   电压谐波(三相)
        String voltageHarmStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> voltageHarms = getGroupDoubleValue(voltageHarmStr, 3, 4);
        msg.setVoltageHarmonics(voltageHarms);

        //143~154   电流谐波(三相)
        String currentHarmStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> currentHarms = getGroupDoubleValue(currentHarmStr, 3, 4);
        msg.setCurrentHarmonics(currentHarms);

        //155~166   电压畸变率(三相)
        String voltageDistortionRateStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> voltageDistortionRate = getGroupDoubleValue(voltageDistortionRateStr,3,4);
        msg.setVoltageDistortionRate(voltageDistortionRate);

        //167~178   电流畸变率(三相)
        String currentDistortionRateStr = substrWithLength(message,strPos.get(),12,posConsumer);
        List<Double> currentDistortionRate = getGroupDoubleValue(currentDistortionRateStr,3,4);
        msg.setCurrentDistortionRate(currentDistortionRate);

        //179~186   吸收有功电能二次侧（全数字）
        String absorbStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setSecondarySideAbsorbActive(Double.valueOf(absorbStr));

        //187~194   释放有功电能二次侧
        String releaseStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setSecondarySideReleaseActive(Double.valueOf(releaseStr));

        //195~202   感性无功电能二次侧
        String inductiveStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setSecondarySideInductiveReactive(Double.valueOf(inductiveStr));

        //203~210   容性无功电能二次侧
        String capacityStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setSecondarySideCapacitiveReactive(Double.valueOf(capacityStr));

        //211~218   吸收有功电能一次侧（全数字）
        absorbStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setPrimarySideAbsorbActive(Double.valueOf(absorbStr));

        //219~226   释放有功电能一次侧
        releaseStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setPrimarySideReleaseActive(Double.valueOf(releaseStr));

        //227~234   感性无功电能一次侧
        inductiveStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setPrimarySideInductiveReactive(Double.valueOf(inductiveStr));

        //235~242   容性无功电能一次侧
        capacityStr = substrWithLength(message,strPos.get(),8,posConsumer);
        msg.setPrimarySideCapacitiveReactive(Double.valueOf(capacityStr));

        //255~266   纬度
        String wd = substrWithLength(message,strPos.get(),12,posConsumer);

        //243~254   经度
        String jd = substrWithLength(message,strPos.get(),12,posConsumer);

        //267
        String gpsStr = substrWithLength(message, strPos.get(), 1, posConsumer);
        msg.setGpsEffective(gpsStr.equals("1"));

        //268~271
        String checkDigit = substrWithLength(message,strPos.get(),4,posConsumer);
        msg.setCheckDigit(checkDigit);

        return msg;
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
            String substr = str.substring(i * length,length);
            group.add(strToDouble.apply(substr));
        }
        return group;
    }
}
