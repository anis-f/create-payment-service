package com.stuff.payment.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.stuff.payment.entity.Payment;
import com.stuff.payment.service.PaymentService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.http.MediaType.APPLICATION_JSON_UTF8;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@WebMvcTest(PaymentController.class)
@ActiveProfiles("test")
public class PaymentControllerTest {

    ObjectMapper objectMapper = new ObjectMapper();
    @MockBean
    private DiscoveryClient discoveryClient;
    @Autowired
    private MockMvc mockMvc;
    @MockBean
    private PaymentService paymentService;

    @InjectMocks
    private PaymentController paymentController;

    @MockBean
    KafkaTemplate<Object, Object> template;


    @Before
    public void setup(){
        MockitoAnnotations.initMocks(this);
    }


    @Test
    public void testCreatePaymentHappyPath() throws Exception {

        Payment payment = new Payment();
        payment.setAmount(70.0);
        payment.setCurrency("USD");
        payment.setPayeeId(UUID.randomUUID().toString());
        payment.setPaymentMethodId(UUID.randomUUID().toString());
        payment.setUserId(UUID.randomUUID().toString());


        when(paymentService.createPayment(any(Payment.class)))
                .thenReturn(payment);

        mockMvc
            .perform(post("/payments").contentType(APPLICATION_JSON_UTF8)
            .content(objectMapper.writeValueAsString(payment)))
            .andExpect(status().isCreated()).andReturn();

    }

    @Test
    public void testCreatePaymentNegativePayment() throws Exception {

        Payment payment = new Payment();
        payment.setAmount(-1);
        payment.setCurrency("USD");
        payment.setPayeeId(UUID.randomUUID().toString());
        payment.setPaymentMethodId(UUID.randomUUID().toString());
        payment.setUserId(UUID.randomUUID().toString());


        when(paymentService.createPayment(any(Payment.class)))
                .thenReturn(payment);

        mockMvc
                .perform(post("/payments").contentType(APPLICATION_JSON_UTF8)
                        .content(objectMapper.writeValueAsString(payment)))
                .andExpect(status().isBadRequest()).andReturn();

    }

}