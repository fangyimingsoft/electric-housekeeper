package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.dao.DeptRepository;
import com.fym.electrichousekeeper.entiry.Dept;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/dept")
public class DeptController {

    @Autowired
    private DeptRepository deptRepository;

    @GetMapping("/list")
    public ApiResponse list(){
        ApiResponse response = ApiResponse.OK();
        Iterable<Dept> all = deptRepository.findAll();
        List<Dept> result = new ArrayList<>();
        all.forEach(result::add);
        response.setRows(new ArrayList<Dept>(result));
        return response;
    }
}
