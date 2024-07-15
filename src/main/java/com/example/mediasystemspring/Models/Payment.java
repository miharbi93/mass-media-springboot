package com.example.mediasystemspring.Models;

import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
public class Payment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long paymentId;

    @Column(nullable = false)
    private Long paidAmount;

    @Column(nullable = false)
    private Timestamp paymentDate;


    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", nullable = false)
    private Application application;

}
