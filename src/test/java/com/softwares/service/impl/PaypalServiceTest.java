package com.softwares.service.impl;

import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.APIContext;
import com.softwares.models.Cart;
import com.softwares.models.User;
import com.softwares.service.CartService;
import com.softwares.service.UserService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaypalServiceTest {

    @InjectMocks
    private PaypalService paypalService;

    @Mock
    private APIContext apiContext;

    @Mock
    private CartService cartService;

    @Mock
    private UserService userService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment_returnsApprovalUrl() throws Exception {
        // Arrange
        String jwt = "mock-jwt";

        // Usuario simulado
        User mockUser = new User();
        mockUser.setId(1L);

        // Carrito simulado
        Cart mockCart = new Cart();
        mockCart.setId(1L);
        mockCart.setTotalSellingPrice(100000); // pesos colombianos

        // Objeto Payment simulado
        Payment mockPayment = mock(Payment.class);

        // Link de aprobación simulado
        Links approvalLink = new Links();
        approvalLink.setRel("approval_url");
        approvalLink.setHref("https://www.sandbox.paypal.com/approval-link");

        // Configurar comportamiento de mocks
        when(userService.findUserProfileByJwt(jwt)).thenReturn(mockUser);
        when(cartService.findUserCart(mockUser)).thenReturn(mockCart);
        when(mockPayment.create(apiContext)).thenReturn(mockPayment);
        when(mockPayment.getLinks()).thenReturn(List.of(approvalLink));

        // Espiar el servicio y simular buildPayment (que ahora es protected)
        PaypalService spyService = Mockito.spy(new PaypalService(apiContext, cartService, userService));
        doReturn(mockPayment).when(spyService).buildPayment(mockCart);

        // Act
        String resultUrl = spyService.createPayment(jwt);

        // Assert
        assertNotNull(resultUrl);
        assertEquals("https://www.sandbox.paypal.com/approval-link", resultUrl);
    }
}
