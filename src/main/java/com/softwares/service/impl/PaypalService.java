package com.softwares.service.impl;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;
import java.util.Locale;

import org.springframework.stereotype.Service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.softwares.models.Cart;
import com.softwares.models.User;
import com.softwares.service.CartService;
import com.softwares.service.UserService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class PaypalService {

    private final APIContext apiContext;
    private final CartService cartService;
    private final UserService userService;

    public String createPayment(String jwt) throws Exception {
        User user = userService.findUserProfileByJwt(jwt);
        Cart cart = cartService.findUserCart(user);

        Payment payment = buildPayment(cart);
        Payment createdPayment = payment.create(apiContext);

        for (Links link : createdPayment.getLinks()) {
            if ("approval_url".equals(link.getRel())) {
                return link.getHref();
            }
        }
        throw new RuntimeException("No se pudo crear el pago");
    }

    private Payment buildPayment(Cart cart) {
        Amount amount = new Amount();
        amount.setCurrency("USD");

        double usdAmount = cart.getTotalSellingPrice() / 4000.0;
        BigDecimal totalAmount = BigDecimal.valueOf(usdAmount).setScale(2, RoundingMode.HALF_UP);
        amount.setTotal(totalAmount.toPlainString());

        Transaction transaction = new Transaction();
        transaction.setDescription("Compra en Software's");
        transaction.setAmount(amount);

        Payer payer = new Payer();
        payer.setPaymentMethod("paypal");

        RedirectUrls redirectUrls = new RedirectUrls();
        redirectUrls.setCancelUrl("http://localhost:5173/payment/cancel");
        redirectUrls.setReturnUrl("http://localhost:5173/payment/success");

        Payment payment = new Payment();
        payment.setIntent("sale");
        payment.setPayer(payer);
        payment.setTransactions(List.of(transaction));
        payment.setRedirectUrls(redirectUrls);

        return payment;
    }
}

