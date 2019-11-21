package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.Device;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeviceRepository extends CrudRepository<Device, String>, PagingAndSortingRepository<Device,String>{

}
