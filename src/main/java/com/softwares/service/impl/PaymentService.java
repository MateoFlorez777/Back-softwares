package com.softwares.service.impl;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class PaymentService {

    private final APIContext apiContext;

    // Puedes cambiar esto o extraerlo de application.properties si lo deseas
    private static final double EXCHANGE_RATE_COP_TO_USD = 4054.54;

    public Payment createPayment(
            Double totalInCOP,
            String currency,
            String method,
            String intent,
            String description,
            String cancelUrl,
            String successUrl) throws PayPalRESTException {

        // Convertir de pesos colombianos a dólares
        double amountInUSD = totalInCOP / EXCHANGE_RATE_COP_TO_USD;

        // Formatear correctamente el valor
        Amount amount = new Amount();
        amount.setCurrency(currency);
        amount.setTotal(String.format(Locale.US, "%.2f", amountInUSD));

        // Crear transacción
        Transaction transaction = new Transaction();
        transaction.setDescription(description);
        transaction.setAmount(amount);

        List<Transaction> transactions = List.of(transaction);

        // Método de pago
        Payer payer = new Payer();
        payer.setPaymentMethod(method);

        // Configurar URLs de redirección
        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl(cancelUrl);
        redirectUrls.setReturnUrl(successUrl);

        // Crear objeto de pago
        Payment payment = new Payment();
        payment.setIntent(intent);
        payment.setPayer(payer);
        payment.setTransactions(transactions);
        payment.setRedirectUrls(redirectUrls);

        // Debug logs (opcional)
        System.out.println("🧾 Monto total COP: " + totalInCOP);
        System.out.println("💵 Monto convertido USD: " + amount.getTotal());

        return payment.create(apiContext);
    }

    public Payment executePayment(String paymentId, String payerId) throws PayPalRESTException {
        Payment payment = new Payment();
        payment.setId(paymentId);

        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId(payerId);

        return payment.execute(apiContext, paymentExecution);
    }
}
