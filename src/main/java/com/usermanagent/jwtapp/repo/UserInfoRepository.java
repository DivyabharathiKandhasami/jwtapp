package com.usermanagent.jwtapp.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.usermanagent.jwtapp.entity.User;

@Repository

public interface UserInfoRepository extends JpaRepository<User, Integer> {
	Optional<User> findByUserName(String userName);

}
