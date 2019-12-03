package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Data;
import com.fym.electrichousekeeper.entiry.po.Device;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

public interface  DataRepository extends JpaRepository<Data,Integer> {
    List<Data> findDataByCodeAndTimeGreaterThanEqualAndTimeLessThan(String code, Date start, Date end);
}
