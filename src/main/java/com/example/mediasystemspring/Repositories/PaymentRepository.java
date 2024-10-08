package com.example.mediasystemspring.Repositories;

import com.example.mediasystemspring.Models.Payment;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface PaymentRepository extends JpaRepository<Payment,Long> {
    Payment findByControlNumber(String controlNumber);

    Payment findByApplication_ApplicationId(Long applicationId);

}
