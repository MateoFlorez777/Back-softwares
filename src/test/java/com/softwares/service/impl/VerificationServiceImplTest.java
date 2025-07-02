package com.softwares.service.impl;

import com.softwares.models.VerificationCode;
import com.softwares.repository.VerificationCodeRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class VerificationServiceImplTest {

    @InjectMocks
    private VerificationServiceImpl verificationService;

    @Mock
    private VerificationCodeRepository verificationCodeRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateVerificationCode_NewCodeSaved() {
        String otp = "123456";
        String email = "test@example.com";

        // Simular que no existe código previo para este email
        when(verificationCodeRepository.findByEmail(email)).thenReturn(null);

        VerificationCode savedCode = new VerificationCode();
        savedCode.setOtp(otp);
        savedCode.setEmail(email);

        when(verificationCodeRepository.save(any(VerificationCode.class))).thenReturn(savedCode);

        VerificationCode result = verificationService.createVerificationCode(otp, email);

        assertNotNull(result);
        assertEquals(otp, result.getOtp());
        assertEquals(email, result.getEmail());

        verify(verificationCodeRepository).findByEmail(email);
        verify(verificationCodeRepository, never()).delete(any());
        verify(verificationCodeRepository).save(any(VerificationCode.class));
    }

    @Test
    void testCreateVerificationCode_ExistingCodeDeletedAndNewSaved() {
        String otp = "654321";
        String email = "existing@example.com";

        VerificationCode existingCode = new VerificationCode();
        existingCode.setOtp("000000");
        existingCode.setEmail(email);

        when(verificationCodeRepository.findByEmail(email)).thenReturn(existingCode);

        VerificationCode savedCode = new VerificationCode();
        savedCode.setOtp(otp);
        savedCode.setEmail(email);

        when(verificationCodeRepository.save(any(VerificationCode.class))).thenReturn(savedCode);

        VerificationCode result = verificationService.createVerificationCode(otp, email);

        assertNotNull(result);
        assertEquals(otp, result.getOtp());
        assertEquals(email, result.getEmail());

        verify(verificationCodeRepository).findByEmail(email);
        verify(verificationCodeRepository).delete(existingCode);
        verify(verificationCodeRepository).save(any(VerificationCode.class));
    }
}
