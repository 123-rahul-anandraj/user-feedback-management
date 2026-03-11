package com.example.userfeedback.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.userfeedback.entity.UserFeedbackEntity;

@Repository
public interface FeedbackRepository extends JpaRepository<UserFeedbackEntity, Long>{
	List<UserFeedbackEntity> findByUsrId(Long usrId);
}
