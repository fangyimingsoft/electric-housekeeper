package com.fym.electrichousekeeper.controller;

import com.fym.electrichousekeeper.common.ApiResponse;
import com.fym.electrichousekeeper.common.PageableResponse;
import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.entiry.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/device")
@CrossOrigin
public class DeviceController {

    @Autowired
    private DeviceRepository deviceRepository;

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

}
