package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.Staff;
import com.example.mediasystemspring.Services.StaffService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/staff")
@CrossOrigin("*")
public class StaffAPI {

    @Autowired
    private StaffService staffService;

    @GetMapping("/all")
    private ResponseEntity<?> allStaff(){
        try {
            List<Staff> staffList = staffService.getAllStaff();
            if (staffList.isEmpty()){
                return new ResponseEntity<>("No Staff Found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(staffList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Fail",HttpStatus.CONFLICT);
        }
    }

    @PostMapping("/add")
    private ResponseEntity<?> createStaff(@RequestBody  Staff staff){
        try {
            Staff staff1 = staffService.addStaff(staff);
            return new ResponseEntity<>("Staff Created Successfully",HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Fail",HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{userId}")
    public ResponseEntity<?> delete(@PathVariable Long userId){
        try {
            Optional<Staff> staffOptional = staffService.staffById(userId);

            if (staffOptional.isPresent()){
                staffService.deleteStaff(userId);
                return new ResponseEntity<>("Staff Deleted Successfully",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No staff with Id "+userId+ " found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/count")
    ResponseEntity<?> countStaff(){
        try {
            Long number_of_staff = staffService.countStaff();
            return new ResponseEntity<>(number_of_staff,HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.BAD_GATEWAY);
        }
    }

//    @PatchMapping("/update/{userId}")
//    public ResponseEntity<Staff> updateStaff(@PathVariable Long userId, @RequestBody Staff staff){
//        Staff staff1 = staffService.updateStaff(userId, staff);
//        return ResponseEntity.ok(staff1);
//    }


    @PatchMapping("/update/{userId}")

    public ResponseEntity<Staff> updateStaff(@PathVariable Long userId, @RequestBody Staff staff) {

        Staff updatedStaff = staffService.updateStaff(userId, staff);

        return ResponseEntity.ok(updatedStaff);

    }


    @GetMapping("/byId/{userId}")
    public ResponseEntity<?> getStaffbyId(@PathVariable Long userId){
        try {
            Optional<Staff>  optionalStaff = staffService.staffById(userId);
            if (optionalStaff.isPresent()){
                return new ResponseEntity<>(optionalStaff,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No staff found",HttpStatus.NOT_FOUND);
            }

        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }
}
