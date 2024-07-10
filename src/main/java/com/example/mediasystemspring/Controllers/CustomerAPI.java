package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.Customer;
import com.example.mediasystemspring.Services.CustomerService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/customer")
@CrossOrigin("*")
public class CustomerAPI {
    @Autowired
    private CustomerService customerService;

    @GetMapping("/all")
    public ResponseEntity<?> getAll(){
        try {
            List<Customer> customerList = customerService.getAll();
            if (customerList.isEmpty()){
                return new ResponseEntity<>("No Customer Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(customerList,HttpStatus.OK);
            }
        }catch (Exception e){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/byId/{userId}")
    public ResponseEntity<?> byId(@PathVariable Long userId){
        try {
            Optional<Customer> customer = customerService.getById(userId);
            if (customer.isPresent()){
                return new ResponseEntity<>(customer,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No Customer Found with Id "+userId+" ",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }



    @PostMapping("/add")

    public ResponseEntity<Customer> createCustomer(@RequestParam("image") MultipartFile image, @RequestParam("customer") String customerJson) throws IOException {

        Customer customer = new ObjectMapper().readValue(customerJson, Customer.class);

        customer.setCustomerImage(image.getBytes());

        Customer newCustomer = customerService.addCustomer(customer);

        return new ResponseEntity<>(newCustomer, HttpStatus.CREATED);

    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> deleteCustomer(@PathVariable Long userId){
        try {
            Optional<Customer> customerOptional = customerService.getById(userId);
            if (customerOptional.isPresent()){
                customerService.deleteCustomer(userId);
                return new ResponseEntity<>("Customer deleted Successfully",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Customer Not Found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }


    @PatchMapping("/update/{userId}")
    public ResponseEntity<Customer> updateCustomer(@PathVariable Long userId,
                                                   @RequestParam(value = "image", required = false) MultipartFile image,
                                                   @RequestParam("customer") String customerJson) throws IOException {
        Customer customer = new ObjectMapper().readValue(customerJson, Customer.class);
        Optional<Customer> existingCustomer = customerService.getById(userId); // retrieve the existing customer

        if (image != null && !image.isEmpty()) {
            byte[] imageBytes = image.getBytes();
            customer.setCustomerImage(imageBytes);
        } else {
            // If no new image is provided, use the existing image
            customer.setCustomerImage(existingCustomer.get().getCustomerImage());
        }

        // Check if password is provided in the customerJson
        if (customer.getPassword() == null || customer.getPassword().trim().isEmpty()) {
            customer.setPassword(existingCustomer.get().getPassword()); // set the existing password if no new password is provided
        }

        Customer updatedCustomer = customerService.updateCustomer(userId, customer);

        return new ResponseEntity<>(updatedCustomer, HttpStatus.OK);
    }

    @GetMapping("/count")
    public Long countCustomers(){
        return customerService.countCustomer();
    }
}
