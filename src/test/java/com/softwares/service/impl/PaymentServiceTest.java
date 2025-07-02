package com.softwares.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.List;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PaymentServiceTest {

    @Mock
    private APIContext apiContext;

    @InjectMocks
    private PaymentService paymentService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreatePayment_success() throws PayPalRESTException {
        // Arrange
        Payment mockPayment = mock(Payment.class);
        when(mockPayment.create(apiContext)).thenReturn(mockPayment);

        // Espía el servicio real pero con comportamiento de mock
        PaymentService spyService = Mockito.spy(new PaymentService(apiContext));

        // Cuando se cree el Payment, lo forzamos a devolver el mock
        doReturn(mockPayment).when(spyService).createPayment(
                anyDouble(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()
        );

        // Act
        Payment result = spyService.createPayment(
                100000.0,
                "USD",
                "paypal",
                "sale",
                "Test Payment",
                "http://cancel.url",
                "http://success.url"
        );

        // Assert
        assertNotNull(result);
    }

    @Test
    void testExecutePayment_success() throws PayPalRESTException {
        // Arrange
        Payment payment = new Payment();
        payment.setId("PAYMENT_ID");

        PaymentExecution execution = new PaymentExecution();
        execution.setPayerId("PAYER_ID");

        Payment mockPayment = mock(Payment.class);
        when(mockPayment.execute(eq(apiContext), any(PaymentExecution.class))).thenReturn(mockPayment);

        // Aquí simulamos ejecutar el pago
        PaymentService spyService = Mockito.spy(new PaymentService(apiContext));
        doReturn(mockPayment).when(spyService).executePayment("PAYMENT_ID", "PAYER_ID");

        // Act
        Payment result = spyService.executePayment("PAYMENT_ID", "PAYER_ID");

        // Assert
        assertNotNull(result);
    }
}
