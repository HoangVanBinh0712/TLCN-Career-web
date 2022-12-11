package com.be.controller;

import java.net.URI;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.be.payload.BaseResponse;
import com.be.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;

@RestController
public class PaypalController {

    @Autowired
    PaypalService service;

    public static final String SUCCESS_URL = "api/pay/success";
    public static final String CANCEL_URL = "api/pay/cancel";

    @PostMapping("api/pay")
    public ResponseEntity<?> payment(@RequestParam(value = "orderId", required = true) Long orderId) {

        if (orderId == null)
            return ResponseEntity.ok(new BaseResponse(false, "Order must not be null !"));

        try {
            Payment payment = service.createPayment(orderId, "http://localhost:8081/" + CANCEL_URL,
                    "http://localhost:8081/" + SUCCESS_URL);
            for (Links link : payment.getLinks()) {
                if (link.getRel().equals("approval_url")) {
                    return ResponseEntity.ok(new BaseResponse(true, link.getHref()));
                }
            }

        } catch (PayPalRESTException e) {

            e.printStackTrace();
        }
        return ResponseEntity.ok(new BaseResponse(false, "There is some thing wrong !"));
    }

    @GetMapping(CANCEL_URL)
    public ResponseEntity<?> cancelPay() {
        return ResponseEntity.ok(new BaseResponse(false, "cancel"));
    }

    @GetMapping(SUCCESS_URL)
    public ResponseEntity<?> successPay(@RequestParam("paymentId") String paymentId,
            @RequestParam("PayerID") String payerId) {
        try {
            Payment payment = service.executePayment(paymentId, payerId);
            if (payment.getState().equals("approved")) {

                // Do update here
                service.handleSuccess(payment);
                return ResponseEntity.status(HttpStatus.FOUND)
                        .location(URI.create("http://localhost:3000/employer/orders"))
                        .build();

                // return ResponseEntity.ok(service.handleSuccess(payment));

            }
        } catch (PayPalRESTException e) {
            System.out.println(e.getMessage());
        }
        return ResponseEntity.ok(new BaseResponse(false, "There is some thing wrong !"));
    }
}
