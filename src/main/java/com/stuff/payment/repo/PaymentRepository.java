package com.stuff.payment.repo;

import com.stuff.payment.domain.Payment;
import org.springframework.data.repository.CrudRepository;

public interface PaymentRepository extends CrudRepository<Payment, String> {


}
