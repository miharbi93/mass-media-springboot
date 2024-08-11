package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Services.ReviewApplicationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/review")
@CrossOrigin("*")
public class ReviewApplicationAPI {


    @Autowired

    private ReviewApplicationService reviewApplicationService;


    @PatchMapping("/applications/{applicationId}/cancel")

    public ResponseEntity<String> cancelReview(@PathVariable Long applicationId) {

        reviewApplicationService.cancelReview(applicationId);

        return ResponseEntity.ok("Review cancelled successfully");

    }

    @PatchMapping("/applications/{applicationId}/reivewstatus")
    public ResponseEntity<String> acceptedORrejected(@PathVariable Long applicationId){
        reviewApplicationService.rejectOrAcceptReview(applicationId);
        return ResponseEntity.ok("Review Status Change successfully");
    }
}
