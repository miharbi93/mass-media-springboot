package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Application;
import com.example.mediasystemspring.Models.Customer;
import com.example.mediasystemspring.Models.MediaService;
import com.example.mediasystemspring.Repositories.ApplicationRepository;
import com.example.mediasystemspring.Repositories.CustomerRepository;
import com.example.mediasystemspring.Repositories.MediaServiceRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;

@Service
public class ApplicationService {
    @Autowired
    private ApplicationRepository applicationRepository;
    @Autowired
    private MediaServiceRepository mediaServiceRepository;
    @Autowired
    private CustomerRepository customerRepository;

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

//        long days = ChronoUnit.DAYS.between(startDate, endDate);
        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;
        application.setDayPackage((int) days); // set dayPackage attribute

        // Set uploaded files
        application.setAdvertiseDocument(advertiseDocument.getBytes());
        application.setUthibitishoDocument(uthibitishoDocument.getBytes());


        return applicationRepository.save(application);
    }

    private BigDecimal calculateAmount(MediaService service, LocalDate startDate, LocalDate endDate) {
        if (service == null) {
            throw new NullPointerException("Service cannot be null");
        }

//        long days = ChronoUnit.DAYS.between(startDate, endDate);
        long days = startDate.until(endDate, ChronoUnit.DAYS) + 1;
        return BigDecimal.valueOf(service.getServicePrice()).multiply(BigDecimal.valueOf(days));
    }


    public List<Application> getAllApplications() {
        return applicationRepository.findAll();
    }









}