package com.fym.electrichousekeeper;

import com.alibaba.fastjson.JSONObject;
import com.fym.electrichousekeeper.core.MessageParser_Old;
import com.fym.electrichousekeeper.entiry.po.Data;
import org.apache.tomcat.util.buf.HexUtils;
import org.junit.jupiter.api.Test;
import sun.rmi.runtime.Log;

import java.io.BufferedReader;
import java.io.FileReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParserTest {

    Pattern pattern = Pattern.compile("value : (\\{.+\\}),order : \\d+");

    @Test
    public void test1() throws Exception {

        /*BufferedReader reader = new BufferedReader( new FileReader("E:\\development\\workspace\\electric-housekeeper\\electric-housekeeper.log"));
        reader.lines().forEach(line->{
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                String message = matcher.group(1);
                String data = null;
                try {
                    JSONObject parse = (JSONObject)JSONObject.parse(message);
                    Long timestamp = parse.getLong("timestamp");
                    System.out.println(timestamp.getClass());
                    JSONObject eventPayload = (JSONObject)parse.get("eventPayload");
                    data = eventPayload.getString("rawdata");
                    if(data.length() != 574){
                        System.err.println("报文长度有误");
                        return;
                    }
                    StringBuilder str = new StringBuilder();
                    for(int i = 0;i < MessageParser_Old.MESSAGE_LENGTH * 2;i+=2){
                        String bit = data.substring(i,i+2);
                        str.append(toAsciiChar(bit));
                    }
                    MessageParser_Old messageParser_old = new MessageParser_Old();

                } catch (Exception e) {

                }

            }
        });*/
    }


    @Test
    public void testHex(){
        for(int i = 0;i < 10;i++){
            for(int j = 'a';j <= 'f'; j++){
                String hex = "" + i + (char)j;
                byte[] bytes = HexUtils.fromHexString(hex);
                byte aByte = bytes[0];
                int number = aByte > 0 ? aByte : (127 + 129 - Math.abs(aByte));
                System.out.println(hex + "->" + number);
            }
        }
    }

    private char toAsciiChar(String hexStr){
        byte[] bytes = HexUtils.fromHexString(hexStr);
        byte aByte = bytes[0];
        int number = aByte > 0 ? aByte : (127 + 129 - Math.abs(aByte));
        return (char) number;
    }

    @Test
    public void test12(){
        System.out.println((int)'1');
    }
}
