package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.MediaService;
import com.example.mediasystemspring.Services.MediaServiceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("api/services")
@CrossOrigin("*")
public class MediaServiceAPI {

    @Autowired
    private MediaServiceService mediaServiceService;

    @GetMapping("/id/{serviceId}")
    private ResponseEntity<?> getService(@PathVariable Long serviceId){
        try {
            Optional<MediaService> mediaServiceOptional = mediaServiceService.getById(serviceId);
            if (mediaServiceOptional.isPresent()){
                return new ResponseEntity<>(mediaServiceOptional, HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No Service Found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/all")
    public ResponseEntity<?> allServices(){
        try {
            List<MediaService> mediaServiceList = mediaServiceService.getAllService();
            if (mediaServiceList.isEmpty()){
                return new ResponseEntity<>("No Service Found",HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(mediaServiceList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps Connection Fail",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/mediaId/{mediaId}")
    public ResponseEntity<?> findAllByMediaChannelMediaId(@PathVariable Long mediaId) {
        List<MediaService> mediaServices = mediaServiceService.findAllByMediaChannelMediaId(mediaId);
        if (!mediaServices.isEmpty()) {
            return ResponseEntity.ok(mediaServices);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @PostMapping("/add")
    public ResponseEntity<?> addServices(@RequestBody MediaService mediaService){
        try {
            MediaService mediaService1 = mediaServiceService.addService(mediaService);
            return new ResponseEntity<>("Service added successfully",HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.BAD_GATEWAY);
        }
    }

    @DeleteMapping("/delete/{serviceId}")
    public ResponseEntity<?> delete(@PathVariable Long  serviceId){
        try {
            Optional<MediaService> mediaServiceOptional = mediaServiceService.getById(serviceId);

            if (mediaServiceOptional.isPresent()){
                mediaServiceService.deleteService(serviceId);
                return new ResponseEntity<>("Service deleted successfull",HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No Service Found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Opps",HttpStatus.BAD_GATEWAY);
        }
    }

    @GetMapping("/count")
    public Long count(){
        return mediaServiceService.countMediaService();
    }

    @PutMapping("/update/{serviceId}")
    public ResponseEntity<MediaService> updateMediaService(@PathVariable Long serviceId, @RequestBody MediaService mediaService) {

        MediaService updatedMediaService = mediaServiceService.updateMediaService(serviceId, mediaService);

        return new ResponseEntity<>(updatedMediaService, HttpStatus.OK);

    }

}
