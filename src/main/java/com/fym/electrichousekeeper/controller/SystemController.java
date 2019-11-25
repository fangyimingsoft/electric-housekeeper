package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.dao.UserRepository;
import com.fym.electrichousekeeper.entiry.po.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.security.MessageDigest;

@RestController
@RequestMapping("/")
public class SystemController {

    /**
     * 用戶密碼加密類型
     */
    public static final String ENCTYPE = "MD5";

    @Autowired
    private UserRepository userRepository;

    @PostMapping("/login")
    public ApiResponse login(@RequestParam("username")String userName,
                             @RequestParam("password")String password,
                             HttpServletRequest request){
        ApiResponse response = ApiResponse.OK();
        User user = userRepository.findByUsername(userName);
        if(user == null){
            response.setStatus(ApiResponse.ERROR);
            response.setMessage("用户不存在");
        }else if(!user.getPassword().equals(Encrypt(ENCTYPE,password.trim()))){
            response.setStatus(ApiResponse.ERROR);
            response.setMessage("密码错误");
        }else{
            HttpSession session = request.getSession(true);
            session.setAttribute("user",user);
        }
        return  response;
    }

    public static String Encrypt(String algorithm, String source) {
        MessageDigest md5 = null;
        try {
            md5 = MessageDigest.getInstance("md5");
        } catch (Exception e) {
            return "";
        }
        char[] charArray = source.toCharArray();
        byte[] byteArray = new byte[charArray.length];

        for (int i = 0; i < charArray.length; i++)
            byteArray[i] = (byte) charArray[i];

        byte[] md5Bytes = md5.digest(byteArray);

        StringBuffer hexValue = new StringBuffer();

        for (int i = 0; i < md5Bytes.length; i++) {
            int val = ((int) md5Bytes[i]) & 0xff;
            if (val < 16)
                hexValue.append("0");
            hexValue.append(Integer.toHexString(val));
        }

        return hexValue.toString();
    }
}
