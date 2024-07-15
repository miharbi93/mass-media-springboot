package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.Application;
import com.example.mediasystemspring.Models.ApplicationDTO;
import com.example.mediasystemspring.Repositories.ApplicationRepository;
import com.example.mediasystemspring.Services.ApplicationService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/applications")
@CrossOrigin("*")
public class ApplicationAPI {
    @Autowired
    private ApplicationService applicationService;

    @Autowired
    private ApplicationRepository applicationRepository;

//    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
//    @PostMapping("/apply")
//    public ResponseEntity<Application> createApplication(@RequestParam("customerId") Long customerId,
//                                                         @RequestParam("serviceId") Long serviceId,
//                                                         @RequestParam("startDate") LocalDate startDate,
//                                                         @RequestParam("endDate") LocalDate endDate,
//                                                         @RequestPart("advertiseDocument") MultipartFile advertiseDocument,
//                                                         @RequestPart("uthibitishoDocument") MultipartFile uthibitishoDocument) throws IOException {
//        Application application = applicationService.createApplication(customerId, serviceId, startDate, endDate, advertiseDocument, uthibitishoDocument);
//        return ResponseEntity.ok(application);
//    }


    @PostMapping("/apply")
    public ResponseEntity<Application> createApplication(@RequestParam("customerId") Long customerId,
                                                         @RequestParam("serviceId") Long serviceId,
                                                         @RequestParam("startDate") LocalDate startDate,
                                                         @RequestParam("endDate") LocalDate endDate,
                                                         @RequestPart("advertiseDocument") MultipartFile advertiseDocument,
                                                         @RequestPart("uthibitishoDocument") MultipartFile uthibitishoDocument) throws IOException {
        Application application = applicationService.createApplication(customerId, serviceId, startDate, endDate, advertiseDocument, uthibitishoDocument);
        return ResponseEntity.ok(application);
    }

    @GetMapping("/{applicationId}/advertiseDocument")

    public ResponseEntity<byte[]> downloadAdvertiseDocument(@PathVariable Long applicationId) {

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new EntityNotFoundException("Application not found"));

        byte[] document = application.getAdvertiseDocument();

        return ResponseEntity.ok()

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"advertiseDocument.pdf\"")

                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")

                .body(document);

    }


    @GetMapping("/{applicationId}/uthibitishoDocument")

    public ResponseEntity<byte[]> downloadUthibitishoDocument(@PathVariable Long applicationId) {

        Application application = applicationRepository.findById(applicationId).orElseThrow(() -> new EntityNotFoundException("Application not found"));

        byte[] document = application.getUthibitishoDocument();

        return ResponseEntity.ok()

                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"uthibitishoDocument.pdf\"")

                .header(HttpHeaders.CONTENT_TYPE, "application/pdf")

                .body(document);

    }


//    @GetMapping("/all")
//
//    public List<Application> getAllApplications() {
//
//        List<Application> applications = applicationService.getAllApplications();
//
//        return applications;
//
//    }



    @GetMapping("/customers/{customerId}/applications")

    public ResponseEntity<List<Application>> getApplicationsByCustomerId(@PathVariable Long customerId) {

        List<Application> applications = applicationService.getApplicationsByCustomerId(customerId);


        return new ResponseEntity<>(applications, HttpStatus.OK);

    }


    @GetMapping("/applications")
    public List<ApplicationDTO> getAllApplications() {
        return applicationService.getAllApplications().stream().map(ApplicationDTO::new).collect(Collectors.toList());

    }


}