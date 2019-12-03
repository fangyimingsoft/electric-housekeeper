package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Device;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends JpaRepository<Device, String>{
}
