package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.dao.DataRepository;
import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.util.DateComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api/data")
@CrossOrigin
public class DataController {

    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/{code}/today")
    public ApiResponse getTodayData(@PathVariable("code") String code,
                                    @DateTimeFormat(pattern = "yyyy-MM-dd") Date date){
        ApiResponse response = ApiResponse.OK();
        Date tomorrow = DateComputeUtil.add(date,DateComputeUtil.TYPE_DATE,1);
        List<Data> data = dataRepository.findDataByCodeAndTimeGreaterThanEqualAndTimeLessThan(code, date, tomorrow);
        response.setRows(data);
        return  response;

    }
}
