package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Application;
import com.example.mediasystemspring.Models.Customer;
import com.example.mediasystemspring.Models.MediaService;
import com.example.mediasystemspring.Models.ReviewApplication;
import com.example.mediasystemspring.Repositories.*;
import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class ApplicationService {

    @Autowired
    private ApplicationRepository applicationRepository;

    @Autowired
    private MediaServiceRepository mediaServiceRepository;

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @Autowired
    private ReviewApplicationRepository reviewApplicationRepository;

    public Application createApplication(Long userId, Long serviceId, LocalDate startDate, LocalDate endDate,
                                         MultipartFile advertiseDocument, MultipartFile uthibitishoDocument) throws IOException {
        Customer customer = customerRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("Customer not found"));
        MediaService service = mediaServiceRepository.findById(serviceId).orElseThrow(() -> new EntityNotFoundException("Service not found"));

        if (startDate == null || endDate == null) {
            throw new IllegalArgumentException("Start date and end date cannot be null");
        }

        if (startDate.isAfter(endDate)) {
            throw new IllegalArgumentException("Start date cannot be after end date");
        }

        Application application = new Application();
        application.setCustomer(customer);
        application.setMediaService(service);
        application.setStartDate(startDate);
        application.setEndDate(endDate);
        application.setApplicationDate(Timestamp.valueOf(LocalDate.now().atStartOfDay())); // set application date to current timestamp
        BigDecimal amount = calculateAmount(service, startDate, endDate);
        application.setAmount(amount);

        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;
        application.setDayPackage((int) days); // set dayPackage attribute

        // Set uploaded files
        application.setAdvertiseDocument(advertiseDocument.getBytes());
        application.setUthibitishoDocument(uthibitishoDocument.getBytes());

        // Save the application to get its ID
        Application savedApplication = applicationRepository.save(application);

        // Create and save a ReviewApplication linked to the newly created Application
        ReviewApplication reviewApplication = new ReviewApplication();
        reviewApplication.setApplication(savedApplication);
        reviewApplication.setReviewStatus("pending");
        reviewApplication.setReviewDate(Timestamp.from(Instant.now()));

        reviewApplicationRepository.save(reviewApplication);

        // Set the ReviewApplication in the saved application and save again
        savedApplication.setReviewApplication(reviewApplication);
        return applicationRepository.save(savedApplication);
    }

    private BigDecimal calculateAmount(MediaService service, LocalDate startDate, LocalDate endDate) {
        if (service == null) {
            throw new NullPointerException("Service cannot be null");
        }

        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;
        return BigDecimal.valueOf(service.getServicePrice()).multiply(BigDecimal.valueOf(days));
    }


    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }


    public List<Application> getApplicationsByCustomerId(Long customerId) {

        Customer customer = new Customer();

        customer.setUserId(customerId);

        List<Application> applications = applicationRepository.findByCustomer(customer);

        return applications;
    }


    public List<Application> getAllApplicationsWithCustomerDetails() {
        return applicationRepository.findAll();
    }

    public void deleteApplication(Long applicationId){
        Application application = applicationRepository.findById(applicationId).orElseThrow();
        reviewApplicationRepository.deleteById(applicationId);
        paymentRepository.deleteById(applicationId);
        applicationRepository.deleteById(applicationId);
    }

}
