package com.softwares.service;


import com.softwares.models.VerificationCode;

public interface VerificationService {

    VerificationCode createVerificationCode(String otp, String email);
}
