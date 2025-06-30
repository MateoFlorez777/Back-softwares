package com.softwares.service;

import com.softwares.exceptions.UserException;
import com.softwares.models.User;

import java.util.List;


public interface UserService {

	public User findUserProfileByJwt(String jwt) throws UserException;
	
	public User findUserByEmail(String email) throws UserException;


}
