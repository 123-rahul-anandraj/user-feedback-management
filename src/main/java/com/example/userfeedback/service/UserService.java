package com.example.userfeedback.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.userfeedback.entity.UserEntity;
import com.example.userfeedback.entity.UserFeedbackEntity;
import com.example.userfeedback.model.UserRequestDTO;
import com.example.userfeedback.model.UserResponseDTO;
import com.example.userfeedback.repository.FeedbackRepository;
import com.example.userfeedback.repository.UserRepository;

@Service
public class UserService {
	@Autowired
	private UserRepository userRpo;
	
	@Autowired
	private BCryptPasswordEncoder pswdEncoder;
	
	@Autowired
	private FeedbackRepository feedbackRpo;
	
	public UserResponseDTO getUserByName(String name) {
		UserResponseDTO finalUsrDTO = new UserResponseDTO();
		String status = "";
		try {
			UserEntity finalUsr = userRpo.findByUsername(name).orElseThrow(()-> new Exception("not such user exists -- "+name));
			finalUsrDTO.setUsrId(finalUsr.getId());
			finalUsrDTO.setUsrname(finalUsr.getUsername());
			finalUsrDTO.setUsrEmailId(finalUsr.getEmailId());
			status = "Success";
		} catch (Exception e) {
			status = "Failed : "+e.getMessage();
		}
		finally {
			finalUsrDTO.setStatus(status);
		}
		return finalUsrDTO;
	}
	
	public List<UserEntity> getAllUsr(){
		return userRpo.findAll();
	}
	
	public UserResponseDTO createUser(UserRequestDTO usrRqDTO){
		UserResponseDTO finalUsrDTO = new UserResponseDTO();
		String status = "";
		try {
			if(usrRqDTO.getUsrPswd().equals(usrRqDTO.getUsrRePswd())) {
				if(getUserByName(usrRqDTO.getUsrname()).getStatus().equalsIgnoreCase("Success")) 
					throw new Exception("user already exists");
				
				UserEntity newUsr = new UserEntity();
				newUsr.setUsername(usrRqDTO.getUsrname());
				newUsr.setEmailId(usrRqDTO.getUsrEmailId());
				newUsr.setPswd(pswdEncoder.encode(usrRqDTO.getUsrPswd()));
				
				UserEntity usrCreated = userRpo.save(newUsr);
				finalUsrDTO.setUsrId(usrCreated.getId());
				finalUsrDTO.setUsrname(usrCreated.getUsername());
				finalUsrDTO.setUsrEmailId(usrCreated.getEmailId());
				
				status = "Success";
			}
			else {
				status = "Failed : Re-written password for confirmation is not matching";
			}
		} catch (Exception e) {
			status = "Failed : "+e.getMessage();
		}
		finally {
			finalUsrDTO.setStatus(status);
		}
		return finalUsrDTO;
	}
	
	public UserResponseDTO loginUser(UserRequestDTO usrRqDTO){
		UserResponseDTO finalUsrDTO = new UserResponseDTO();
		String status = "";
		try {
			UserEntity usrDtl = userRpo.findById(usrRqDTO.getUsrId()).orElseThrow(()-> new Exception("not such user exists -- "+usrRqDTO.getUsrname()));
			
			if(!pswdEncoder.matches(usrRqDTO.getUsrPswd(), usrDtl.getPswd()))
				throw new Exception("incorrect password");
			
			finalUsrDTO.setUsrId(usrDtl.getId());
			finalUsrDTO.setUsrname(usrDtl.getUsername());
			finalUsrDTO.setUsrEmailId(usrDtl.getEmailId());
			status = "Success : Login Successful";
		} catch (Exception e) {
			status = "Failed : "+e.getMessage();
		}
		finally {
			finalUsrDTO.setStatus(status);
		}
		return finalUsrDTO;
	}
	
	public void deleteUsr(Long usrId) {
		userRpo.deleteById(usrId);
	}
	
	public UserResponseDTO addUsrFeedback(UserRequestDTO usrRqDTO) {
		UserResponseDTO finalUsrDTO = new UserResponseDTO();
		String status = "";
		
		UserFeedbackEntity usrFeedback = new UserFeedbackEntity();
		usrFeedback.setUsrId(usrRqDTO.getUsrId());
		usrFeedback.setUserName(usrRqDTO.getUsrname());
		usrFeedback.setFeedbackContent(usrRqDTO.getUsrFeedback());
		UserFeedbackEntity savedUsrFeedback = feedbackRpo.save(usrFeedback);
		
		finalUsrDTO.setUsrId(savedUsrFeedback.getUsrId());
		finalUsrDTO.setUsrname(savedUsrFeedback.getUserName());
		status = "Success : User Feedback saved successfully";
		
		finalUsrDTO.setStatus(status);
		return finalUsrDTO;
	}
	
	public UserResponseDTO getUsrFeedback(Long usrId){
		UserResponseDTO finalUsrDTO = new UserResponseDTO();
		String status = "";
		try {
			Map<String,String> fdbkMap = new HashMap<>();
			List<UserFeedbackEntity> usrFeedbackList = feedbackRpo.findByUsrId(usrId);
			if(!usrFeedbackList.isEmpty()) {
				usrFeedbackList.stream().forEach(usrFeedback -> fdbkMap.put(String.valueOf(usrFeedback.getId()), usrFeedback.getFeedbackContent()));
				finalUsrDTO.setUsrFeedback(fdbkMap);	
				finalUsrDTO.setUsrId(usrFeedbackList.get(0).getUsrId());
				finalUsrDTO.setUsrname(usrFeedbackList.get(0).getUserName());
				status = "Success";
			}
			else {
				status = "Success : No feedbacks from user-Id :: "+usrId;
			}
		} catch (Exception e) {
			status = "Failed : "+e.getMessage();
		}
		finally {
			finalUsrDTO.setStatus(status);
		}
		return finalUsrDTO;
	}
	
	public void deleteUsrFeedback(Long usrFeedbackId) {
		feedbackRpo.deleteById(usrFeedbackId);
	}
	
	public UserResponseDTO updateUsrFeedback(UserRequestDTO usrRqDTO) {
		UserResponseDTO finalUsrDTO = new UserResponseDTO();
		String status = "";
		try {
			UserFeedbackEntity usrFeedbackDtl = feedbackRpo.findById(usrRqDTO.getUsrFeedbackId()).orElseThrow(()-> new Exception("not such feedback exists for usr -- "+usrRqDTO.getUsrname()));
			
			usrFeedbackDtl.setFeedbackContent(usrRqDTO.getUsrFeedback());
			feedbackRpo.save(usrFeedbackDtl);
			
			finalUsrDTO = getUsrFeedback(usrRqDTO.getUsrId());
			if(finalUsrDTO.getStatus().equals("Success")) {
				status = "Success : Feedback Updated Successful";
			}
			
			
		} catch (Exception e) {
			status = "Failed : "+e.getMessage();
		}
		finally {
			finalUsrDTO.setStatus(status);
		}
		return finalUsrDTO;
	}
}

