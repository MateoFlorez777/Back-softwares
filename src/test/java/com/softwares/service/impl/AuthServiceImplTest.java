package com.softwares.service.impl;

import com.softwares.config.JwtProvider;
import com.softwares.domain.USER_ROLE;
import com.softwares.exceptions.SellerException;
import com.softwares.models.VerificationCode;
import com.softwares.repository.CartRepository;
import com.softwares.repository.UserRepository;
import com.softwares.repository.VerificationCodeRepository;
import com.softwares.request.LoginRequest;
import com.softwares.response.AuthResponse;
import com.softwares.service.EmailService;
import com.softwares.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class AuthServiceImplTest {

    @InjectMocks
    private AuthServiceImpl authService;

    @Mock private UserService userService;
    @Mock private VerificationCodeRepository verificationCodeRepository;
    @Mock private EmailService emailService;
    @Mock private PasswordEncoder passwordEncoder;
    @Mock private UserRepository userRepository;
    @Mock private JwtProvider jwtProvider;
    @Mock private CustomeUserServiceImplementation customUserDetails;
    @Mock private CartRepository cartRepository;
    @Mock private UserDetails userDetails;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testSignin_successful() throws Exception {
        // Arrange
        String email = "test@example.com";
        String otp = "123456";

        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setEmail(email);
        loginRequest.setOtp(otp);

        VerificationCode verificationCode = new VerificationCode();
        verificationCode.setEmail(email);
        verificationCode.setOtp(otp);

        when(customUserDetails.loadUserByUsername(email)).thenReturn(userDetails);
        when(verificationCodeRepository.findByEmail(email)).thenReturn(verificationCode);
        when(jwtProvider.generateToken(any())).thenReturn("mocked-jwt-token");

        when(userDetails.getAuthorities()).thenAnswer(invocation -> {
            Collection<GrantedAuthority> granted = new ArrayList<>();
            granted.add(() -> USER_ROLE.ROLE_CUSTOMER.name());
            return granted;
        });

        // Act
        AuthResponse response = authService.signin(loginRequest);

        // Assert
        assertNotNull(response);
        assertEquals("mocked-jwt-token", response.getJwt());
        assertEquals(USER_ROLE.ROLE_CUSTOMER, response.getRole());
        assertEquals("Inicio de sesión exitosamente", response.getMessage());
    }
}
