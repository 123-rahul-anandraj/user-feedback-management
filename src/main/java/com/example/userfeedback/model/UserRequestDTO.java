package com.example.userfeedback.model;

import lombok.Data;

@Data
public class UserRequestDTO {
	private Long usrId;
	private Long usrFeedbackId;
	private String usrname;
	private String usrPswd;
	private String usrRePswd;
	private String usrEmailId;
	private String usrFeedback;
}
