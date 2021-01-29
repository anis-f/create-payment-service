package com.stuff.payment.repository;

import com.stuff.payment.model.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, String> {


}
