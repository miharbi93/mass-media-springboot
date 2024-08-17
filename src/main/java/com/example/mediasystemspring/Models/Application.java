package com.example.mediasystemspring.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;

@Entity
@Data
public class Application {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long applicationId;

    @Column(nullable = false)
    private Timestamp applicationDate;

    private BigDecimal amount;


    @Column(nullable = false, length = 1000000)
    @Lob
    private byte[] advertiseDocument;

    @Column(length = 1000000)
    @Lob
    private byte[] uthibitishoDocument;


    private LocalDate startDate;

    private LocalDate endDate;

    private Integer dayPackage;



    @ManyToOne
    @JoinColumn(name = "customer_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonBackReference
    private Customer customer;

    @ManyToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "media_service_id", nullable = false)
    private MediaService mediaService;


    @OneToOne(mappedBy = "application")
    @JsonManagedReference
    private ReviewApplication reviewApplication;





    public ReviewApplication getReviewApplication() {
        return reviewApplication;
    }

    public void setReviewApplication(ReviewApplication reviewApplication) {
        this.reviewApplication = reviewApplication;
    }

    public Long getApplicationId() {
        return applicationId;
    }

    public void setApplicationId(Long applicationId) {
        this.applicationId = applicationId;
    }

    public Timestamp getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(Timestamp applicationDate) {
        this.applicationDate = applicationDate;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public byte[] getAdvertiseDocument() {
        return advertiseDocument;
    }

    public void setAdvertiseDocument(byte[] advertiseDocument) {
        this.advertiseDocument = advertiseDocument;
    }

    public byte[] getUthibitishoDocument() {
        return uthibitishoDocument;
    }

    public void setUthibitishoDocument(byte[] uthibitishoDocument) {
        this.uthibitishoDocument = uthibitishoDocument;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Customer getCustomer() {
        return customer;
    }

    public void setCustomer(Customer customer) {
        this.customer = customer;
    }

    public MediaService getMediaService() {
        return mediaService;
    }

    public void setMediaService(MediaService mediaService) {
        this.mediaService = mediaService;
    }

    public Integer getDayPackage() {
        return dayPackage;
    }

    public void setDayPackage(Integer dayPackage) {
        this.dayPackage = dayPackage;
    }




//    @ManyToOne
//    @JsonBackReference
//    private User user;
//


}
