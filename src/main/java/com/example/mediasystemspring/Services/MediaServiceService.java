package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.MediaService;
import com.example.mediasystemspring.Repositories.MediaServiceRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;

import javax.print.attribute.standard.Media;
import java.util.List;
import java.util.Optional;

@Service
public class MediaServiceService {

    @Autowired
    private MediaServiceRepository mediaServiceRepository;

    public Optional<MediaService> getById(Long serviceId){
        return mediaServiceRepository.findById(serviceId);
    }

    public List<MediaService> getAllService(){
        return mediaServiceRepository.findAll();
    }

    public List<MediaService> findAllByMediaChannelMediaId(Long mediaId) {
        return mediaServiceRepository.findAllByMediaChannelMediaId(mediaId);
    }

    public  MediaService addService(MediaService mediaService){
        return mediaServiceRepository.save(mediaService);
    }

    public  void  deleteService(Long serviceId){
        mediaServiceRepository.deleteById(serviceId);
    }

    public Long countMediaService(){
        return mediaServiceRepository.count();
    }

    public MediaService updateMediaService(Long serviceId, MediaService mediaService){
        MediaService existingService = mediaServiceRepository.findById(serviceId).orElseThrow();
        existingService.setServiceName(mediaService.getServiceName());
        existingService.setServicePrice(mediaService.getServicePrice());
        existingService.setServiceDescription(mediaService.getServiceDescription());
        return mediaServiceRepository.save(existingService);
    }







}
