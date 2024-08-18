package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.User;
import com.example.mediasystemspring.Repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<User> getAllUser(){
        return userRepository.findAll();
    }

    public Optional<User> getUserById(Long userId){
        return userRepository.findById(userId);
    }

//    public User addUser(User user){
//        return userRepository.save(user);
//    }

    public User addUser(User user){
        String encryptedPassword = passwordEncoder.encode(user.getPassword());
        user.setPassword(encryptedPassword);
        return userRepository.save(user);
    }

    public void deleteUser(Long userId){
        userRepository.deleteById(userId);
    }

    public Optional<User> findByEmail(String email){
        return userRepository.findByEmail(email);
    }
//
//    public User updateUser(User user){
//        User existinguser = userRepository.findById(userId).orElseThrow();
//
//        existinguser.setUsername(user.getUsername());
//        existinguser.setEmail(user.getEmail());
//        existinguser.setPassword(user.getPassword());
//        return userRepository.save(existinguser);
//    }

    public User updateUser(Long userId, User user){
        User existingUser = userRepository.findById(userId).orElseThrow();
        existingUser.setUsername(user.getUsername());
        existingUser.setEmail(user.getEmail());
        // If the user is trying to update the password, encrypt it first
        if (user.getPassword() != null) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(existingUser);
    }

}
