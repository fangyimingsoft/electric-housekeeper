package com.fym.electrichousekeeper.dao;

import com.fym.electrichousekeeper.entiry.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends CrudRepository<User,Long> {
    User findByUsername(String userName);
}
