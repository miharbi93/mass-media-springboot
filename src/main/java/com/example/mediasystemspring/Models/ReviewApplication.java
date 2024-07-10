package com.example.mediasystemspring.Models;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

import java.sql.Timestamp;

@Entity
@Data
@Table(name = "application_review")
public class ReviewApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reviewId;

    @Column(nullable = false)
    private Timestamp reviewDate;

    @Column(nullable = false,updatable = true)
    private String reviewStatus;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "application_id", nullable = false)
    @JsonBackReference
    private Application application;


    public Application getApplication() {

        return application;

    }


    public void setApplication(Application application) {

        this.application = application;

    }


    public void setReviewStatus(String reviewStatus) {
        this.reviewStatus = reviewStatus;
    }
}
