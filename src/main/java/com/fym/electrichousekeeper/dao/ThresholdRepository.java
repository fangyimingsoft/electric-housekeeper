package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Threshold;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface ThresholdRepository extends JpaRepository<Threshold,Integer>{
    @Modifying
    @Query("update Threshold set value = ?2 where typeCode = ?1 and isGlobal = 1")
    int updateGlobalThreshold(String typeCode,int value);

    int removeThresholdsByIsGlobal(boolean isGlobal);

    @Modifying
    @Query("delete from Threshold where deviceCode = ?1 and isGlobal = false ")
    int removeDeviceThreshold(String deviceCode);
}
