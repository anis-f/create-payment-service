package com.stuff.payment;


import com.stuff.payment.controller.PaymentControllerTest;
import com.stuff.payment.e2e.TestE2E;
import com.stuff.payment.model.Payment;
import com.stuff.payment.repository.PaymentRepository;
import com.stuff.payment.service.PaymentService;
import com.stuff.payment.service.PaymentServiceTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Random;
import java.util.UUID;

import static org.junit.Assert.assertEquals;

@RunWith(Suite.class)
@Suite.SuiteClasses({PaymentControllerTest.class, PaymentServiceTest.class, TestE2E.class})
public class CreatePaymentFeatureSuite {

}
