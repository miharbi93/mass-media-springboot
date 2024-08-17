package com.example.mediasystemspring.DTO;

import com.example.mediasystemspring.Models.Payment;
import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class PaymentDTO {

    // payment
    private Long paymentId;
    private Long paidAmount;
    private String controlNumber;
    private Timestamp paymentDate;

    // application
    private Timestamp applicationDate;
    private BigDecimal amount;
    private String status;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer dayPackage;
    private Long applicationId;

    // customer
    private String username;
    private Long userId;
    private String email;


    // application review

    private String reviewStatus;
    private Timestamp reviewDate;


    // media channel
    private Long mediaId;
    private String mediaName;
    private String mediaDescription;
    private String mediaEmail;
    private String mediaWebUrl;
    private String mediaType;
    private byte[] image;




    // service for media

    private Long serviceId;
    private String serviceName;
    private String serviceDescription;
    private Long servicePrice;










    public PaymentDTO(Payment payment){
        this.paymentId = payment.getPaymentId();
        this.paidAmount = payment.getPaidAmount();
        this.controlNumber = payment.getControlNumber();
        this.paymentDate = payment.getPaymentDate();

        this.applicationId = payment.getApplication().getApplicationId();
        this.applicationDate = payment.getApplication().getApplicationDate();
        this.amount = payment.getApplication().getAmount();
        this.startDate = payment.getApplication().getStartDate();
        this.endDate = payment.getApplication().getEndDate();
        this.dayPackage = payment.getApplication().getDayPackage();

        this.username = payment.getApplication().getCustomer().getUsername();
        this.userId = payment.getApplication().getCustomer().getUserId();
        this.email = payment.getApplication().getCustomer().getEmail();


        this.reviewStatus  = payment.getApplication().getReviewApplication().getReviewStatus();
        this.reviewDate  = payment.getApplication().getReviewApplication().getReviewDate();

        this.mediaId = payment.getApplication().getMediaService().getMediaChannel().getMediaId();
        this.mediaName = payment.getApplication().getMediaService().getMediaChannel().getMediaName();
        this.mediaType = payment.getApplication().getMediaService().getMediaChannel().getMediaType();
        this.mediaEmail = payment.getApplication().getMediaService().getMediaChannel().getMediaEmail();
        this.mediaWebUrl = payment.getApplication().getMediaService().getMediaChannel().getMediaWebUrl();
        this.mediaDescription = payment.getApplication().getMediaService().getMediaChannel().getMediaDescription();
        this.image = payment.getApplication().getMediaService().getMediaChannel().getImage();


        this.serviceId = payment.getApplication().getMediaService().getServiceId();
        this.serviceName = payment.getApplication().getMediaService().getServiceName();
        this.servicePrice = payment.getApplication().getMediaService().getServicePrice();
        this.serviceDescription = payment.getApplication().getMediaService().getServiceDescription();




    }
}
