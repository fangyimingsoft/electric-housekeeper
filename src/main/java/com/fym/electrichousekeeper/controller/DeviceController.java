package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.common.PageableResponse;
import com.fym.electrichousekeeper.dao.DataRepository;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.dao.WarningRepository;
import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.entiry.po.Device;
import com.fym.electrichousekeeper.util.DateComputeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.thymeleaf.util.DateUtils;

import java.util.*;

@RestController
@RequestMapping("/api/device")
@CrossOrigin
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

    @Autowired
    private DataRepository dataRepository;

    @Autowired
    private WarningRepository warningRepository;

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

    /**
     * 查询所有设备各状态的数量
     * 设备数量、正在运行、今日未报警（正在运行中且未报警）、今日异常设备、今日报警次数
     * @return
     */
    @GetMapping("/allStatus")
    public ApiResponse getStatus(){
        ApiResponse response = ApiResponse.OK();
        Map<String,Integer> resultMap = new HashMap<>();
        Map<Integer,Integer> statusCount = new HashMap<>();
        List deviceStatus = deviceRepository.findDeviceStatus();
        for (Object obj : deviceStatus) {
            Object [] arr = (Object []) obj;
            Integer status = Integer.valueOf(arr[0].toString());
            Integer count = Integer.valueOf(arr[1].toString());
            statusCount.put(status,count);
        }
        int running = statusCount.get(Device.RUNNING) != null ? statusCount.get(Device.RUNNING) : 0;
        int notRunning = statusCount.get(Device.NOT_RUNNING) != null ? statusCount.get(Device.NOT_RUNNING) : 0;
        resultMap.put("all",running + notRunning);
        resultMap.put("running",running);
        resultMap.put("notRunning",notRunning);
        long warningCount = warningRepository.countByTimeGreaterThanEqual(DateComputeUtil.todayZero());
        resultMap.put("warningCount",new Long(warningCount).intValue());
        List list = deviceRepository.findDeviceWarningCountAfter(DateComputeUtil.todayZero());
        int normalCount = 0;//今日正常，未报警
        int errorCount = 0;//今日异常，报过警
        for (Object result : list) {
            Object [] resultArr = (Object [])result;
            Integer count = Integer.valueOf(resultArr[1].toString());
            if(count > 0){
                errorCount++;
            }else{
                normalCount++;
            }
        }
        resultMap.put("normal",normalCount);
        resultMap.put("error",errorCount);
        response.setData(resultMap);
        return response;
    }
}
