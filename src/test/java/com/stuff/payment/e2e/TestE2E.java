package com.stuff.payment.e2e;

import com.stuff.payment.controller.PaymentController;
import com.stuff.payment.model.Payment;
import com.stuff.payment.repository.PaymentRepository;
import com.stuff.payment.service.PaymentService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.client.RestClientTest;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@ActiveProfiles("test")
@SpringBootTest(properties = "spring.cloud.kubernetes.enabled=false",
        webEnvironment = SpringBootTest.WebEnvironment.NONE)
public class TestE2E {

    TestRestTemplate restTemplate = new TestRestTemplate();
    @Autowired PaymentRepository paymentRepository;
    @Test
    public void testE2E() throws InterruptedException {


        Random random = new Random();
        List<String> paymentCurrency = new ArrayList<>();
        paymentCurrency.add("USD");
        paymentCurrency.add("EUR");
        paymentCurrency.add("ILS");

        double rangeMin = 1;
        double rangeMax = 1000;

        int totalAccepted = 0;
        int totalRejected = 0;
        int total = 0;
        for (int i = 0; i < 10; i++) {
            double randomValue = rangeMin + (rangeMax - rangeMin) * random.nextDouble();
            Payment payment = new Payment();
            payment.setAmount(randomValue);
            int randomPaymentCurrencyIndex = new Random().nextInt(paymentCurrency.size());
            payment.setCurrency(paymentCurrency.get(randomPaymentCurrencyIndex));
            payment.setUserId(UUID.randomUUID().toString());
            payment.setPayeeId(UUID.randomUUID().toString());
            payment.setPaymentMethodId(UUID.randomUUID().toString());
            ResponseEntity<Payment> paymentWithRisk = restTemplate.postForEntity("http://192.168.64.21:31967/payments", payment, Payment.class);

        }
        Thread.sleep(5000);
        Iterable<Payment> payments = paymentRepository.findAll();

        for (Payment payment : payments) {
            if (payment.getRisk().equals("ACCEPTED"))
                totalAccepted++;
            else
                totalRejected++;
        }
        total = totalAccepted + totalRejected;
        assertEquals((int) (total * 0.7), totalAccepted);
        assertEquals((int) (total * 0.3), totalRejected);
    }

}
