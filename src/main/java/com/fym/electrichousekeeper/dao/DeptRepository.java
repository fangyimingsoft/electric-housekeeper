package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.Dept;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends CrudRepository<Dept,Integer> {

}
