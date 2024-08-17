package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.DTO.PaymentDTO;
import com.example.mediasystemspring.DTO.PaymentRequest;
import com.example.mediasystemspring.DTO.PaymentResponse;
import com.example.mediasystemspring.Models.Application;
import com.example.mediasystemspring.Models.ApplicationDTO;
import com.example.mediasystemspring.Models.Payment;
import com.example.mediasystemspring.Repositories.ApplicationRepository;
import com.example.mediasystemspring.Repositories.PaymentRepository;
import com.example.mediasystemspring.Services.PaymentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/payment")
@CrossOrigin("*")
public class PaymentAPI{

    @Autowired
    private PaymentService paymentService;

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired

    private PaymentRepository paymentRepository;
    @GetMapping("/{applicationId}")
    public ResponseEntity<Payment>  getPaymentByApplicationId(@PathVariable Long applicationId) {
        Payment payment = paymentRepository.findByApplication_ApplicationId(applicationId);
        if (payment != null) {
            return ResponseEntity.ok(payment);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @GetMapping("/control-number/{applicationId}")
    public ResponseEntity<String> generateControlNumber(@PathVariable Long applicationId) {
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        Payment payment = new Payment();
        payment.setApplication(application);
        payment.setPaidAmount(0L); 
        paymentService.save(payment);
        return new ResponseEntity<>(payment.getControlNumber(), HttpStatus.OK);
    }

    @DeleteMapping("/delete/{paymentId}")
    public ResponseEntity<?> deletePaymentById(Long paymentId){
        try {
            Optional<Payment> payment = paymentService.findPaymentById(paymentId);
            if (payment.isPresent()){
                paymentService.deletePayment(paymentId);
                return new ResponseEntity<>("Payment Deleted Successfull",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No Payment with Id"+ paymentId, HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }



    @PostMapping("/payments/{controlNumber}")
    public ResponseEntity<PaymentResponse> payAmount(@PathVariable String controlNumber, @RequestBody PaymentRequest paymentRequest) {
        Payment payment = paymentService.findByControlNumber(controlNumber);
        if (payment == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        // Accumulate the new payment amount
        payment.setPaidAmount(payment.getPaidAmount() + paymentRequest.getPaidAmount());

        payment.setPaymentDate(new Timestamp(System.currentTimeMillis()));
        paymentService.save(payment);
        PaymentResponse paymentResponse = new PaymentResponse(payment);
        return new ResponseEntity<>(paymentResponse, HttpStatus.OK);
    }


    @GetMapping("/all")
    public ResponseEntity<?> getAllPayment(){
        try {
            List<Payment> paymentList = paymentService.getPayments();
            if (paymentList.isEmpty()){
                return new ResponseEntity<>("No Payment Found", HttpStatus.NOT_FOUND);
            }else{
                return new ResponseEntity<>(paymentList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("No connection Try Again",HttpStatus.BAD_REQUEST);
        }
    }
//
    @GetMapping("/payments")
    public List<PaymentDTO> getAllPayments(){
        return paymentService.getPayments().stream().map(PaymentDTO::new).collect(Collectors.toList());
    }

        @GetMapping("/Id/{paymentId}")

        public ResponseEntity<PaymentDTO> getPayment(@PathVariable Long paymentId) {
            Payment payment = paymentRepository.findById(paymentId).orElseThrow();
            PaymentDTO paymentDTO = new PaymentDTO(payment);
            return ResponseEntity.ok(paymentDTO);
    }
//
//    @GetMapping
//
//    public ResponseEntity<List<PaymentDTO>> getAllPayments() {
//
//        List<Payment> payments = paymentRepository.findAll();
//
//        List<PaymentDTO> paymentDTOs = payments.stream()
//
//                .map(payment -> new PaymentDTO(payment))
//
//                .collect(Collectors.toList());
//
//        return ResponseEntity.ok(paymentDTOs);
//
//    }


}