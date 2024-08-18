package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.User;
import com.example.mediasystemspring.Services.EmailService;
import com.example.mediasystemspring.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.Random;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAPI {

    @Autowired
    private UserService userService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    @GetMapping("/all")
    private ResponseEntity<?> allUsers(){
        try {
            List<User> userList = userService.getAllUser();
            if (userList.isEmpty()){
                return new ResponseEntity<>("No User Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(userList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection not good",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/byId/{userId}")
    private ResponseEntity<?> getById(@PathVariable Long userId){
        try {
            Optional<User> optionalUser = userService.getUserById(userId);
            if (optionalUser.isPresent()){
                return new ResponseEntity<>(optionalUser,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No User with Id "+userId,HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection not good",HttpStatus.BAD_GATEWAY);
        }
    }

    @PostMapping("/add")
    private ResponseEntity<?> createUser(@RequestBody User user){
        try {
            User user1 = userService.addUser(user);
            return new ResponseEntity<>("Create User successfully",HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>("Connection not good",HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/delete/{userId}")
    private ResponseEntity<?> deleteUser(@PathVariable Long userId){
        try {
            Optional<User> userOptional  = userService.getUserById(userId);
            if (userOptional.isPresent()) {
                userService.deleteUser(userId);
                return new ResponseEntity<>("User with Id " + userId + " deleted successfully", HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No user with Id "+userId,HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection not good",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/username/{email}")
    private ResponseEntity<?> usernameLogin(@PathVariable String email){
        try {
            Optional<User> userOptional = userService.findByEmail(email);
            if (userOptional.isPresent()){
                return new ResponseEntity<>(userOptional,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("Email not email",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Not Found",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/usernames/{email}")
    private ResponseEntity<?> usernameLogin(@PathVariable String email, @RequestParam String password){
        try {
            Optional<User> userOptional = userService.findByEmail(email);
            if (userOptional.isPresent()){
                User user = userOptional.get();
                // Check if the input password matches the stored hashed password
                if (passwordEncoder.matches(password, user.getPassword())) {
                    // Remove the password from the response
                    user.setPassword(null);
                    return new ResponseEntity<>(user, HttpStatus.OK);
                } else {
                    return new ResponseEntity<>("Invalid password", HttpStatus.UNAUTHORIZED);
                }
            }else {
                return new ResponseEntity<>("Email not found", HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Not Found", HttpStatus.BAD_GATEWAY);
        }
    }
//    @PatchMapping("/update/{userId}")
//    public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody User user){
//        User user1 = userService.updateUser(userId,user);
//        return ResponseEntity.ok(user1);
//    }

    @PatchMapping("/update/{userId}")
    public ResponseEntity<?> updateUser(@PathVariable Long userId, @RequestBody User user){
        try {
            User updatedUser = userService.updateUser(userId, user);
            // Remove the password from the response
            updatedUser.setPassword(null);
            return new ResponseEntity<>(updatedUser, HttpStatus.OK);
        } catch (Exception exception){
            return new ResponseEntity<>("Connection Not Found", HttpStatus.BAD_GATEWAY);
        }
    }
//
//    @PatchMapping("/resetpassword/{userId}")
//    public ResponseEntity<String> resetPasswordForUser(@PathVariable Long userId){
//            Optional<User> userOptional = userService.getUserById(userId);
//            if (userOptional.isPresent()){
//                User user = userOptional.get();
//
//                String userEmail = user.getEmail();
//                String subject = "Mass Media Advertising Hub Account";
//                String text = "Hello Dear " + user.getUsername()+ "," +
//                        " \n\n Your new Password is "+ user.getPassword() + " "+ "has been reset successfully on " + subject;
//                emailService.sendEmail(userEmail,subject,text);
//
//
//            }
//            return ResponseEntity.ok("Password reset successfully");
//
//    }


    @PatchMapping("/resetpassword/{email}")
    public ResponseEntity<String> resetPasswordForUser(@PathVariable String email) {
        Optional<User> userOptional = userService.findByEmail(email);
        if (userOptional.isPresent()) {
            User user = userOptional.get();

            // Generate a random new password
            String newPassword = generateRandomPassword();

            // Set the new password (without encoding)
            user.setPassword(newPassword);

            // Save the user (encoding will be done automatically)
            userService.addUser(user);

            // Send a password reset email with the new password
            String userEmail = user.getEmail();
            String subject = "Mass Media Advertising Hub Account";
            String text = "Hello Dear " + user.getUsername() + "," +
                    " \n\n Your password has been reset successfully. Please use the following new password to log in: " + newPassword;
            emailService.sendEmail(userEmail, subject, text);
        }
        return ResponseEntity.ok("Password reset successfully");
    }

    private String generateRandomPassword() {
        Random random = new Random(System.nanoTime());
        int firstPart = random.nextInt(900) + 100; // generates a random number between 100 and 999
        int secondPart = random.nextInt(900) + 100; // generates a random number between 100 and 999
        int thirdPart = random.nextInt(900) + 100; // generates a random number between 100 and 999
        return String.format("%03d-%03d-%03d", firstPart, secondPart,thirdPart);

    }

}
