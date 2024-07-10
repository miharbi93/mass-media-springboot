package com.example.mediasystemspring.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@PrimaryKeyJoinColumn(name = "user_id")
public class Customer extends User {


    @Lob
    @Column(length = 100000)
    private byte[] customerImage;

    public byte[] getCustomerImage() {
        return customerImage;
    }

    public void setCustomerImage(byte[] customerImage) {
        this.customerImage = customerImage;
    }
}
