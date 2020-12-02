package com.stuff.payment.web;

import com.stuff.payment.domain.Payment;
import com.stuff.payment.service.PaymentService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.util.Map;
import java.util.Optional;
import java.util.Set;


@RestController
@RequestMapping("/payments")
public class PaymentController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PaymentController.class);

    @Autowired
    private DiscoveryClient discoveryClient;

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private RestTemplate restTemplate;

    @GetMapping("methods")
    public Map<String, Set> getPaymentMethods() {
        return paymentService.getPaymentMethods();
    };

    @GetMapping("payees")
    public Map<String, Set> getPayees() {
        return paymentService.getPayees();
    };

    @GetMapping("/{transactionId}")
    public Optional<Payment> getPaymentByTransactionId(@PathVariable(value = "transactionId") String transactionId) {
        LOGGER.info("GET /payments/{}", transactionId);
        return paymentService.getPaymentByTransactionId(transactionId);
    };
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public void createPayment(@RequestBody @Validated Payment payment) throws Exception {
        LOGGER.info("POST /payments");
        ResponseEntity<Payment> response = restTemplate.postForEntity("http://payment-risk-service/risk", payment, Payment.class);
        //Payment paymentWithRisk = restTemplate.getForObject("http://payment-risk-service/risk", Payment.class);
        payment.setRisk(response.getBody().getRisk());
        paymentService.createPayment(payment);
    };

}
