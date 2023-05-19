package com.example.secAuth.entity;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepo extends JpaRepository<UserSec, Long>{

	UserSec findByusername(String userName);
}
