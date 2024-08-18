package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Customer;
import com.example.mediasystemspring.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getById(Long userId){
        return customerRepository.findById(userId);
    }

    public Customer addCustomer(Customer customer){
        String encryptedPassword = passwordEncoder.encode(customer.getPassword());
        customer.setPassword(encryptedPassword);
        return customerRepository.save(customer);
    }
    public void  deleteCustomer(Long userId){
        customerRepository.deleteById(userId);
    }

    public Long countCustomer(){
        return customerRepository.count();
    }

    public Customer updateCustomer(Long userId, Customer customer) {

        Customer existingCustomer = customerRepository.findById(userId).orElseThrow();

        existingCustomer.setUsername(customer.getUsername());

        existingCustomer.setCustomerImage(customer.getCustomerImage());

//        existingCustomer.setPassword(customer.getPassword());

        String encryptedPassword = passwordEncoder.encode(customer.getPassword());
        existingCustomer.setPassword(encryptedPassword);

        existingCustomer.setEmail(customer.getEmail());

        existingCustomer.setAccount_status(customer.getAccount_status());

        existingCustomer.setRole(customer.getRole());

        return customerRepository.save(existingCustomer);

    }

}
