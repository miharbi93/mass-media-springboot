package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Application;
import com.example.mediasystemspring.Models.ReviewApplication;
import com.example.mediasystemspring.Repositories.ApplicationRepository;
import com.example.mediasystemspring.Repositories.ReviewApplicationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ReviewApplicationService {

    @Autowired
    private ReviewApplicationRepository reviewApplicationRepository;

    @Autowired

    private ApplicationRepository applicationRepository;


    public void cancelReview(Long applicationId) {
        Application application = applicationRepository.findApplicationByApplicationId(applicationId);
        if (application != null) {
            ReviewApplication reviewApplication = application.getReviewApplication();
            if (reviewApplication != null) {
                reviewApplication.setReviewStatus("Cancel");
                application.setReviewApplication(reviewApplication); // Update the reviewApplication field on the application object
                applicationRepository.save(application); // Save the application object
            } else {
                throw new RuntimeException("Review not found for application");
            }
        } else {
            throw new RuntimeException("Application not found");
        }
    }

    public void rejectOrAcceptReview(Long applicationId) {
        Application application = applicationRepository.findApplicationByApplicationId(applicationId);
        if (application != null) {
            ReviewApplication reviewApplication = application.getReviewApplication();
            if (reviewApplication != null) {
                if (reviewApplication.getReviewStatus().equals("pending")) {
                    reviewApplication.setReviewStatus("Accepted");
                }else {
                    if (reviewApplication.getReviewStatus().equals("Accepted")) {
                        reviewApplication.setReviewStatus("Rejected");
                    } else {
                        reviewApplication.setReviewStatus("Accepted");
                    }
                }
                application.setReviewApplication(reviewApplication);
                applicationRepository.save(application);
            }else {
                throw new RuntimeException("Review not found for application");
            }
        }else{
            throw new RuntimeException("Application not found");

        }
    }
}
