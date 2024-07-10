package com.example.mediasystemspring.Controllers;

import com.example.mediasystemspring.Models.MediaChannel;
import com.example.mediasystemspring.Services.MediaChannelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/channel")
@CrossOrigin("*")
public class MediaChannelAPI {

    @Autowired
    private MediaChannelService mediaChannelService;

    @PostMapping("/add")
    public MediaChannel createChannels(
            @RequestParam("mediaName") String mediaName,
            @RequestParam("mediaDescription") String mediaDescription,
            @RequestParam("mediaEmail") String mediaEmail,
            @RequestParam("mediaWebUrl") String mediaWebUrl,
            @RequestParam("status") String status,
            @RequestParam("mediaType") String mediaType,
            @RequestParam("image") MultipartFile image) throws IOException {

        MediaChannel mediaChannel = new MediaChannel();
            mediaChannel.setMediaName(mediaName);
            mediaChannel.setMediaDescription(mediaDescription);
            mediaChannel.setMediaEmail(mediaEmail);
            mediaChannel.setMediaWebUrl(mediaWebUrl);
            mediaChannel.setStatus(status);
            mediaChannel.setMediaType(mediaType);
            mediaChannel.setImage(image.getBytes());
            mediaChannel.setCreatedDate(LocalDate.now());
        return mediaChannelService.addChannels(mediaChannel);

    }


    @GetMapping("/all")
    private ResponseEntity<?> getAllChannels(){
        try {
            List<MediaChannel> mediaChannelList = mediaChannelService.getChannels();
            if (mediaChannelList.isEmpty()){
                return new ResponseEntity<>("No channel found", HttpStatus.NOT_FOUND);
            }else {
                return new ResponseEntity<>(mediaChannelList,HttpStatus.OK);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Fail",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/byId/{mediaId}")
    private ResponseEntity<?> getById(@PathVariable Long mediaId){
        try {
            Optional<MediaChannel> mediaChannel = mediaChannelService.getChannelsById(mediaId);
            if (mediaChannel.isPresent()){
                return new ResponseEntity<>(mediaChannel,HttpStatus.OK);
            }else {
                return new ResponseEntity<>("No media found",HttpStatus.NOT_FOUND);
            }
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Fail",HttpStatus.CONFLICT);
        }
    }

    @DeleteMapping("/delete/{mediaId}")
    private  ResponseEntity<?> deleteById(@PathVariable Long mediaId){
        try {
            mediaChannelService.deleteById(mediaId);
            return new ResponseEntity<>("Deleted successfully",HttpStatus.OK);
        }catch (Exception exception){
            return new ResponseEntity<>("Connection Fail",HttpStatus.CONFLICT);
        }
    }

    @GetMapping("/count")
    public ResponseEntity<?> countChannels(){
        try {
            Long count_media_channels  = mediaChannelService.countMediaChannel();
            return new ResponseEntity<>(count_media_channels,HttpStatus.OK);
        }catch (Exception e){
            return new ResponseEntity<>("Opps",HttpStatus.CONFLICT);
        }
    }

    @PutMapping("/update/{mediaId}")
    public ResponseEntity<?> updateChannel(@PathVariable Long mediaId,
                                           @RequestParam("mediaName") String mediaName,
                                           @RequestParam("mediaDescription") String mediaDescription,
                                           @RequestParam("mediaEmail") String mediaEmail,
                                           @RequestParam("mediaWebUrl") String mediaWebUrl,
                                           @RequestParam("status") String status,
                                           @RequestParam("mediaType") String mediaType,
                                           @RequestParam(value = "image", required = false) MultipartFile image) throws IOException {

        try {
            Optional<MediaChannel> mediaChannel = mediaChannelService.getChannelsById(mediaId);
            if (mediaChannel.isPresent()){
                MediaChannel updatedMediaChannel = mediaChannel.get();
                updatedMediaChannel.setMediaName(mediaName);
                updatedMediaChannel.setMediaDescription(mediaDescription);
                updatedMediaChannel.setMediaEmail(mediaEmail);
                updatedMediaChannel.setMediaWebUrl(mediaWebUrl);
                updatedMediaChannel.setStatus(status);
                updatedMediaChannel.setMediaType(mediaType);

                // Only update the image if a new one is provided
                if (image!= null) {
                    updatedMediaChannel.setImage(image.getBytes());
                }

                mediaChannelService.updateChannels(updatedMediaChannel);
                return new ResponseEntity<>("Channel updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("No media found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception exception) {
            return new ResponseEntity<>("Connection Fail", HttpStatus.CONFLICT);
        }
    }
}