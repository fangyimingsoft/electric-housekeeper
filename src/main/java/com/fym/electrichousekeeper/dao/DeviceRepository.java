package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String>{

    @Query("select status,count(status) from Device group by status")
    List findDeviceStatus();

    @Query("select d.code,count(w.deviceCode) from Device d left join Warning w on d.code = w.deviceCode and w.time >= ?1 where d.status = 1 group by d.code")
    List findDeviceWarningCountAfter(Date date);
}
