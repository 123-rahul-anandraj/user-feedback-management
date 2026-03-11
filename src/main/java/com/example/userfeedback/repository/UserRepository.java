package com.example.userfeedback.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userfeedback.entity.UserEntity;
import java.util.List;


@Repository
public interface UserRepository extends JpaRepository<UserEntity, Long>{
	Optional<UserEntity> findById(Long id);
	Optional<UserEntity> findByUsername(String username);
}
