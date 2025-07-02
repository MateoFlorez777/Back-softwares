package com.softwares.service.impl;

import com.softwares.exceptions.UserException;
import com.softwares.models.User;
import com.softwares.repository.UserRepository;
import com.softwares.config.JwtProvider;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class UserServiceImplementationTest {

    @InjectMocks
    private UserServiceImplementation userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private JwtProvider jwtProvider;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindUserProfileByJwt_UserExists() throws Exception {
        String jwt = "mock-jwt-token";
        String email = "test@example.com";

        User mockUser = new User();
        mockUser.setEmail(email);

        when(jwtProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(mockUser);

        User result = userService.findUserProfileByJwt(jwt);

        assertNotNull(result);
        assertEquals(email, result.getEmail());
        verify(jwtProvider).getEmailFromJwtToken(jwt);
        verify(userRepository).findByEmail(email);
    }

    @Test
    void testFindUserProfileByJwt_UserNotFound() {
        String jwt = "mock-jwt-token";
        String email = "notfound@example.com";

        when(jwtProvider.getEmailFromJwtToken(jwt)).thenReturn(email);
        when(userRepository.findByEmail(email)).thenReturn(null);

        UserException exception = assertThrows(UserException.class, () -> {
            userService.findUserProfileByJwt(jwt);
        });

        assertEquals("El usuario no existe con el correo electrónico " + email, exception.getMessage());
        verify(jwtProvider).getEmailFromJwtToken(jwt);
        verify(userRepository).findByEmail(email);
    }
}
