package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.dao.UserRepository;
import com.fym.electrichousekeeper.entiry.User;
import net.bytebuddy.asm.Advice;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.jws.soap.SOAPBinding;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin
public class UserController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/{id}")
    public List<?> userList(@PathVariable("id")Long id){
        Optional<User> byId = userRepository.findById(id);
        List<User> result = new ArrayList<>();
        if(byId.isPresent()){
            result.add(byId.get());
        }
        return  result;
    }

    @GetMapping
    public User userList(@RequestParam("name") String name){
        return userRepository.findByUsername(name);
    }

}
