package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.po.Dept;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DeptRepository extends JpaRepository<Dept,Integer> {

}
