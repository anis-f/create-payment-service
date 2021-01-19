package com.stuff.payment.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stuff.payment.entity.Payment;
import com.stuff.payment.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.*;

@Service
@Transactional
public class PaymentService implements Serializable {

    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private KafkaTemplate<Object, Object> template;
    ObjectMapper objectMapper = new ObjectMapper();

    public Payment createPayment(Payment payment) throws Exception {
        template.send("payment", objectMapper.writeValueAsString(payment));
        return payment;
        //paymentRepository.save(payment);
    };

    public Map<String, Set> getPayees() {
        Map<String, Set> payeesMap = new HashMap<>();
        Set<String> payees = new LinkedHashSet<>();
        Iterable<Payment> paymentsIterator = paymentRepository.findAll();
        paymentsIterator.forEach(payment -> {
            payees.add(payment.getPayeeName());
        });
        payeesMap.put("payees", payees);
        return payeesMap;
    };

    public Map<String, Set> getPaymentMethods() {
        Map<String, Set> paymentMethodsMap = new HashMap<>();
        Set<String> paymentMethods = new LinkedHashSet<>();
        Iterable<Payment> paymentsIterator = paymentRepository.findAll();
        paymentsIterator.forEach(payment -> {
            paymentMethods.add(payment.getPaymentMethodName());
        });
        paymentMethodsMap.put("paymentMethods", paymentMethods);
        return paymentMethodsMap;
    }


    public Optional<Payment> getPaymentByTransactionId(String transactionId) {
        return paymentRepository.findById(transactionId);
    }
}
