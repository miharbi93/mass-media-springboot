package com.example.mediasystemspring.DTO;

import com.example.mediasystemspring.Models.Payment;
import lombok.Data;

import java.sql.Timestamp;

@Data
public class PaymentResponse {

    private Long paymentId;

    private String controlNumber;

    private Long paidAmount;

    private Timestamp paymentDate;

    private String username;

    public PaymentResponse(Payment payment) {
    }

    public Long getPaymentId() {
        return paymentId;
    }

    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    public String getControlNumber() {
        return controlNumber;
    }

    public void setControlNumber(String controlNumber) {
        this.controlNumber = controlNumber;
    }

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }

    public Timestamp getPaymentDate() {
        return paymentDate;
    }

    public void setPaymentDate(Timestamp paymentDate) {
        this.paymentDate = paymentDate;
    }
}
