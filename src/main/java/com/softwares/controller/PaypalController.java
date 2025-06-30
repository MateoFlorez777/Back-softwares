package com.softwares.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.softwares.service.impl.PaypalService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/paypal")
@RequiredArgsConstructor
public class PaypalController {

    private final PaypalService paypalService;

    @PostMapping("/create-payment")
    public ResponseEntity<String> createPayment(@RequestHeader("Authorization") String jwt) {
        try {
            String approvalUrl = paypalService.createPayment(jwt);
            return ResponseEntity.ok(approvalUrl);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al crear pago: " + e.getMessage());
        }
    }
}
