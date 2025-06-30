package com.softwares.service;

import com.softwares.exceptions.SellerException;
import com.softwares.exceptions.UserException;
import com.softwares.request.LoginRequest;
import com.softwares.request.SignupRequest;
import com.softwares.response.AuthResponse;
import jakarta.mail.MessagingException;

public interface AuthService {

    void sentLoginOtp(String email) throws UserException, MessagingException;
    String createUser(SignupRequest req) throws SellerException;
    AuthResponse signin(LoginRequest req) throws SellerException;

}
