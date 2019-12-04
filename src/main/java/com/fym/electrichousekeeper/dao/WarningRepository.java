package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Warning;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface WarningRepository extends JpaRepository<Warning,Integer> , JpaSpecificationExecutor<Warning> {
    List<Warning> findByTimeGreaterThanEqualAndTimeLessThan(Date start, Date end);
    long countByTimeGreaterThanEqual(Date time);
}
