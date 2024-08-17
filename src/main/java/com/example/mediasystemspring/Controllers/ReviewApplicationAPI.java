package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.Application;
import com.example.mediasystemspring.Repositories.ApplicationRepository;
import com.example.mediasystemspring.Services.EmailService;
import com.example.mediasystemspring.Services.ReviewApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/api/review")
@CrossOrigin("*")
public class ReviewApplicationAPI {


    @Autowired

    private ReviewApplicationService reviewApplicationService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private ApplicationRepository applicationRepository;


    @PatchMapping("/applications/{applicationId}/cancel")

    public ResponseEntity<String> cancelReview(@PathVariable Long applicationId) {

        reviewApplicationService.cancelReview(applicationId);

        return ResponseEntity.ok("Review cancelled successfully");

    }

    @PatchMapping("/applications/{applicationId}/reivewstatus")
    public ResponseEntity<String> acceptedORrejected(@PathVariable Long applicationId){
        reviewApplicationService.rejectOrAcceptReview(applicationId);

        Optional<Application> applicationOptional = applicationRepository.findById(applicationId);
        if (applicationOptional.isPresent()) {

            Application application = applicationOptional.get();


        String userEmail = application.getCustomer().getEmail();
        String subject =  application.getReviewApplication().getReviewStatus();
        String text = "Hello Dear " + application.getCustomer().getUsername()+ ", \n\n Your application for "+ application.getMediaService().getServiceName()+
                " on Media "+ application.getMediaService().getMediaChannel().getMediaName()+ " From "+application.getReviewApplication().getApplication().getStartDate()+
                " to "+ application.getReviewApplication().getApplication().getEndDate()+" has beeen "+ subject;
        emailService.sendEmail(userEmail,subject,text);
        }
        return ResponseEntity.ok("Review Status Change successfully");
    }
}
