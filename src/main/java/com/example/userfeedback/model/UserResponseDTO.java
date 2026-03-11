package com.example.userfeedback.model;

import java.util.List;

import lombok.Data;

@Data
public class UserResponseDTO {
	private Long usrId;
	private String usrname;
	private String usrEmailId;
	private List<String> usrFeedback;
	private String status;
}
