package com.stuff.payment.repository;

import com.stuff.payment.entity.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, String> {


}
