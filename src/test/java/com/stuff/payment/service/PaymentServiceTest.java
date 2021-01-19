package com.stuff.payment.service;

import com.stuff.payment.entity.Payment;
import org.assertj.core.api.Assertions;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.*;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(properties = "spring.cloud.kubernetes.enabled=false",
                webEnvironment = SpringBootTest.WebEnvironment.NONE)
@ActiveProfiles("test")

public class PaymentServiceTest {

    @Autowired PaymentService paymentService;
    @MockBean
    KafkaTemplate<Object, Object> template;

    @Test
    public void testCreatePaymentHappyPath() throws Exception {
        Payment payment = new Payment();

        payment.setAmount(70.0);
        payment.setCurrency("USD");
        payment.setPayeeId(UUID.randomUUID().toString());
        payment.setPaymentMethodId(UUID.randomUUID().toString());
        payment.setUserId(UUID.randomUUID().toString());

        Payment newPayment = paymentService.createPayment(payment);
        assertNotNull(newPayment);
        assertNotNull(newPayment.getTransactionId());
        assertNotNull(newPayment.getPaymentMethodName());
        assertNotNull(newPayment.getPayeeName());
    }

    @Test
    public void testGetPayeesHappyPath() {
        Set<String> names = new HashSet<>();
        names.add("John");
        names.add("Lisa");
        names.add("Alex");
        names.add("Abby");
        names.add("Barbra");
        names.add("Mike");
        Map<String, Set> payees = paymentService.getPayees();
        Set<String> payeesSet = payees.get("payees");
        Assertions.assertThat(payeesSet.toArray()).containsAnyOf(names.toArray());
    }

    @Test
    public void testGetPaymentMethodsHappyPath() {
        List<String> paymentMethods = new ArrayList<>();
        paymentMethods.add("PayPal");
        paymentMethods.add("Cash");
        paymentMethods.add("Credit Card");
        paymentMethods.add("Bank Transfer");
        paymentMethods.add("Direct Deposit");
        paymentMethods.add("Google Pay");
        Map<String, Set> paymentMethodsFromService = paymentService.getPaymentMethods();
        Set<String> paymentMethodsSet = paymentMethodsFromService.get("paymentMethods");
        Assertions.assertThat(paymentMethodsSet.toArray()).containsAnyOf(paymentMethods.toArray());

    }

}