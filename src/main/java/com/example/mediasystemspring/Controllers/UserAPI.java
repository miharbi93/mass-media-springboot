package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.User;
import com.example.mediasystemspring.Services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/user")
@CrossOrigin(origins = "*", maxAge = 3600)
public class UserAPI {

    @Autowired
    private UserService userService;

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

    @PatchMapping("/update/{userId}")
    public ResponseEntity<User> updateUser(@PathVariable Long userId,@RequestBody User user){
        User user1 = userService.updateUser(userId,user);
        return ResponseEntity.ok(user1);
    }

}
