package com.example.userfeedback.model;

import java.util.Map;

import lombok.Data;

@Data
public class UserResponseDTO {
	private Long usrId;
	private String usrname;
	private String usrEmailId;
	private Map<String,String> usrFeedback;
	private String status;
}
