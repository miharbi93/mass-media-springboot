package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Customer;
import com.example.mediasystemspring.Repositories.CustomerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
public class CustomerService {
    @Autowired
    private CustomerRepository customerRepository;

    public List<Customer> getAll(){
        return customerRepository.findAll();
    }

    public Optional<Customer> getById(Long userId){
        return customerRepository.findById(userId);
    }

    public Customer addCustomer(Customer customer){
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

        existingCustomer.setPassword(customer.getPassword());

        existingCustomer.setEmail(customer.getEmail());

        existingCustomer.setStatus(customer.getStatus());

        existingCustomer.setRole(customer.getRole());

        return customerRepository.save(existingCustomer);

    }

}
