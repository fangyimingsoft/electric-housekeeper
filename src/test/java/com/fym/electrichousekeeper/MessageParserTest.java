package com.fym.electrichousekeeper;

import com.fym.electrichousekeeper.core.MessageParser_Old;
import com.fym.electrichousekeeper.core.Message_Old;
import com.fym.electrichousekeeper.entiry.po.Data;
import com.rabbitmq.tools.json.JSONUtil;
import com.sun.org.apache.xerces.internal.impl.dv.util.HexBin;
import org.apache.tomcat.util.buf.HexUtils;
import org.json.JSONException;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.io.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MessageParserTest {

    Pattern pattern = Pattern.compile("value : (\\{.+\\}),order : \\d+");

    @Test
    public void test1() throws Exception {

        BufferedReader reader = new BufferedReader( new FileReader("E:\\development\\workspace\\electric-housekeeper\\electric-housekeeper.log"));
        reader.lines().forEach(line->{
            Matcher matcher = pattern.matcher(line);
            if(matcher.find()){
                String message = matcher.group(1);
                String data = null;
                try {
                    JSONObject jsonObject = new JSONObject(message);
                    JSONObject eventPayLoad = (JSONObject)jsonObject.get("eventPayload");
                    data = eventPayLoad.get("rawdata").toString();
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
                    Data parse = messageParser_old.parse(str.toString());
                    System.out.println(parse);
                } catch (Exception e) {

                }

            }
        });
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
}
