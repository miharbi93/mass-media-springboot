package com.example.mediasystemspring.Models;

import lombok.Data;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Data
public class ApplicationDTO {
    private Long applicationId;
    private Timestamp applicationDate;
    private BigDecimal amount;
    private String status;
    private byte[] advertiseDocument;
    private byte[] uthibitishoDocument;
    private LocalDate startDate;
    private LocalDate endDate;
    private Integer dayPackage;
    private String username;
    private Long mediaServiceId;
//    private String mediaName;
    private String mediaServiceName;
    private String email;
    private Long mediaId;
    private Long userId;
    private String reviewStatus;

    public ApplicationDTO(Application application) {
        this.mediaServiceName = application.getMediaService().getServiceName();
        this.email = application.getCustomer().getEmail();
        this.mediaId = application.getMediaService().getMediaChannel().getMediaId();


        this.applicationId = application.getApplicationId();
        this.applicationDate = application.getApplicationDate();
        this.amount = application.getAmount();
        this.status = application.getReviewApplication().getReviewStatus();
        this.advertiseDocument = application.getAdvertiseDocument();
        this.uthibitishoDocument = application.getUthibitishoDocument();
        this.startDate = application.getStartDate();
        this.endDate = application.getEndDate();
        this.dayPackage = application.getDayPackage();
        this.username = application.getCustomer().getUsername();
        this.mediaServiceId = Long.valueOf(String.valueOf(application.getMediaService().getServiceId()));
        this.userId = application.getCustomer().getUserId();
        this.reviewStatus = application.getReviewApplication().getReviewStatus();

    }
}