package com.softwares.controller;

import com.paypal.api.payments.*;
import com.paypal.base.rest.PayPalRESTException;
import com.softwares.service.impl.PaymentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
public class PaymentController {

    private final PaymentService paymentService;

    @GetMapping("/pay")
    public ResponseEntity<?> createPayment(@RequestParam Double amount) {
        try {
            Payment payment = paymentService.createPayment(
                    amount,
                    "USD",
                    "paypal",
                    "sale",
                    "Compra desde el carrito",
                    "http://localhost:3000/paypal/cancel",
                    "http://localhost:3000/paypal/success"
            );

            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(Map.of("redirect_url", link.getHref()));
                }
            }

            return ResponseEntity.status(500).body("No se encontró el enlace de aprobación");
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/success")
    public ResponseEntity<?> executePayment(
            @RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = paymentService.executePayment(paymentId, payerId);
            return ResponseEntity.ok(payment);
        } catch (PayPalRESTException e) {
            return ResponseEntity.status(500).body(e.getMessage());
        }
    }

    @GetMapping("/cancel")
    public ResponseEntity<?> cancelPayment() {
        return ResponseEntity.ok("El pago fue cancelado por el usuario");
    }
}
