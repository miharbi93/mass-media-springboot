package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Payment;
import com.example.mediasystemspring.Repositories.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PaymentService {

    @Autowired
    private PaymentRepository paymentRepository;

    public Payment findByControlNumber(String controlNumber) {
        return paymentRepository.findByControlNumber(controlNumber);
    }

    public void save(Payment payment) {
        paymentRepository.save(payment);
    }

    public List<Payment> getPayments(){
        return paymentRepository.findAll();
    }

    public Optional<Payment> findPaymentById(Long paymentId){
        return paymentRepository.findById(paymentId);
    }

    public void  deletePayment(Long paymentId){
        paymentRepository.deleteById(paymentId);
    }
}