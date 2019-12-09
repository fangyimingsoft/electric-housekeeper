package com.fym.electrichousekeeper.service;

import com.fym.electrichousekeeper.dao.DeviceRepository;
import com.fym.electrichousekeeper.entiry.po.Device;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.Optional;

@Service
public class DeviceService {

    @Autowired
    private DeviceRepository repository;

    static private DeviceRepository staticRepository;

    @PostConstruct
    public void initStaticRepository(){
        staticRepository = this.repository;
    }

    public static Integer getDeviceCt(String code){
        Optional<Device> byId = staticRepository.findById(code);
        if(byId.isPresent()){
            return  byId.get().getCt();
        }
        return null;
    }
}
