package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Data;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Date;
import java.util.List;

public interface  DataRepository extends JpaRepository<Data,Integer> {
    List<Data> findDataByCodeAndTimeGreaterThanEqualAndTimeLessThan(String code, Date start, Date end);
}
