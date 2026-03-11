package com.example.userfeedback.controller;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.userfeedback.entity.UserEntity;
import com.example.userfeedback.model.UserRequestDTO;
import com.example.userfeedback.model.UserResponseDTO;
import com.example.userfeedback.service.UserService;

@RestController
@RequestMapping(value = "/user")
public class UserController {
	
	@Autowired
	private UserService usrService;
	
	@GetMapping("/getAllUser")
	public List<UserEntity> getAllUsr() {
		return usrService.getAllUsr();
	}
	
	@GetMapping("/getUserByName/{username}")
	public UserResponseDTO getUserByName(@PathVariable("username") String username) {
		return usrService.getUserByName(username);
	}
	
	@PostMapping("/register")
	public UserResponseDTO createUser(@RequestBody UserRequestDTO usrRq) {
		return usrService.createUser(usrRq);
	}
	
	@PostMapping("/login")
	public UserResponseDTO userLogin(@RequestBody Map<String,String> loginDtl) {
		UserRequestDTO usrLoginDtl = new UserRequestDTO();
		usrLoginDtl.setUsrname(loginDtl.get("username"));
		usrLoginDtl.setUsrPswd(loginDtl.get("pasword"));
		return usrService.loginUser(usrLoginDtl);
	}
	
	@DeleteMapping("/admin/delete/{userid}")
	public void deleteUsr(@PathVariable("userid") Long userid) {
		usrService.deleteUsr(userid);
	}
	
	@PostMapping("/usrFeedback/add")
	public UserResponseDTO addUsrFeedback(@RequestBody Map<String,String> usrFeedbackDtl) {
		UserRequestDTO usrFeedbackDto = new UserRequestDTO();
		usrFeedbackDto.setUsrId(Long.parseLong(usrFeedbackDtl.get("userId")));
		usrFeedbackDto.setUsrname(usrFeedbackDtl.get("username"));
		usrFeedbackDto.setUsrFeedback(usrFeedbackDtl.get("user_feedback"));
		return usrService.addUsrFeedback(usrFeedbackDto);
	}
	
	@GetMapping("/usrFeedback/{userid}")
	public UserResponseDTO getUsrFeedback(@PathVariable("userid") Long userid) {
		return usrService.getUsrFeedback(userid);
	}
	
	@PostMapping("/usrFeedback/update")
	public UserResponseDTO updateUsrFeedback(@RequestBody Map<String,String> usrFeedbackDtl) {
		UserRequestDTO usrFeedbackDto = new UserRequestDTO();
		usrFeedbackDto.setUsrFeedbackId(Long.parseLong(usrFeedbackDtl.get("userFeedbackId")));
		usrFeedbackDto.setUsrId(Long.parseLong(usrFeedbackDtl.get("userId")));
		usrFeedbackDto.setUsrname(usrFeedbackDtl.get("username"));
		usrFeedbackDto.setUsrFeedback(usrFeedbackDtl.get("user_feedback"));
		return usrService.updateUsrFeedback(usrFeedbackDto);
	}
	
	@DeleteMapping("/admin/delete/{feedbackid}")
	public void deleteUsrFeedback(@PathVariable("feedbackid") Long feedbackid) {
		usrService.deleteUsrFeedback(feedbackid);
	}
}


