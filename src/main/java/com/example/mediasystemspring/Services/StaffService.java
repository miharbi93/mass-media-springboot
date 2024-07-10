package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.Staff;
import com.example.mediasystemspring.Repositories.StaffRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class StaffService {

    @Autowired
    private StaffRepository staffRepository;

    public List<Staff> getAllStaff(){
        return staffRepository.findAll();
    }

    public Staff addStaff(Staff staff){
        return staffRepository.save(staff);
    }

    public Optional<Staff> staffById(Long userId){
        return staffRepository.findById(userId);
    }

    public  void deleteStaff(Long userId){
        staffRepository.deleteById(userId);
    }

    public  Long countStaff(){
        return staffRepository.count();
    }
    public Staff updateStaff(Long userId, Staff staff) {

        Staff existingStaff = staffRepository.findById(userId).orElseThrow();

        existingStaff.setUsername(staff.getUsername());

        if (staff.getPassword() != null && !staff.getPassword().isEmpty()) {
            existingStaff.setPassword(staff.getPassword());
        }

        existingStaff.setEmail(staff.getEmail());

        existingStaff.setRole(staff.getRole());

        existingStaff.setMediaChannel(staff.getMediaChannel());

        return staffRepository.save(existingStaff);

    }
}
