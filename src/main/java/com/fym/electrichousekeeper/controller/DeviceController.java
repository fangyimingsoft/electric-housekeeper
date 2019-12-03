package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.common.PageableResponse;
import com.fym.electrichousekeeper.dao.DataRepository;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.entiry.po.Device;
import com.fym.electrichousekeeper.util.DateComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;

import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/device")
@CrossOrigin
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DataRepository dataRepository;

    @GetMapping("/list")
    public ApiResponse list(@RequestParam(value = "currentPage",required = false,defaultValue = "1")Integer currentPage,
                            @RequestParam(value = "pageSize",required = false,defaultValue = "10")Integer pageSize){
        Pageable pageInfo = PageRequest.of(currentPage - 1,pageSize);
        Page<Device> all = deviceRepository.findAll(pageInfo);
        PageableResponse response = PageableResponse.OK(all.getTotalElements(), all.getTotalPages());
        System.out.println(all.get().count());
        response.setRows(all.getContent());
        return response;
    }

    @GetMapping("/todayData")
    public ApiResponse getOne(@RequestParam("code")String code){
        ApiResponse response = ApiResponse.OK();
        Date todayZero = DateComputeUtil.todayZero();
        Date tomorrowZero = DateComputeUtil.add(todayZero,DateComputeUtil.TYPE_DATE,1);
        List<Data> data = dataRepository.findDataByCodeAndTimeGreaterThanEqualAndTimeLessThan(code, todayZero, tomorrowZero);
        data.sort(Comparator.comparing(Data::getTime));
        response.setRows(data);
        return response;
    }

}
