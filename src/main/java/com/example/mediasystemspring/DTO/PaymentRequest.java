package com.example.mediasystemspring.DTO;

import lombok.Data;

@Data
public class PaymentRequest {

    private Long paidAmount;

    public Long getPaidAmount() {
        return paidAmount;
    }

    public void setPaidAmount(Long paidAmount) {
        this.paidAmount = paidAmount;
    }
}
