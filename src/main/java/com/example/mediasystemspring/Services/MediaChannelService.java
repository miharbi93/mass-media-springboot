package com.example.mediasystemspring.Services;

import com.example.mediasystemspring.Models.MediaChannel;
import com.example.mediasystemspring.Repositories.MediaChannelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class MediaChannelService {
    @Autowired
    private MediaChannelRepository mediaChannelRepository;

    public MediaChannel addChannels(MediaChannel mediaChannel){
        return mediaChannelRepository.save(mediaChannel);
    }

    public List<MediaChannel> getChannels(){
        return mediaChannelRepository.findAll();
    }

    public Optional<MediaChannel> getChannelsById(Long mediaId){
        return mediaChannelRepository.findById(mediaId);
    }

    public void deleteById(Long mediaId){
        mediaChannelRepository.deleteById(mediaId);
    }

    public  Long countMediaChannel(){
        return mediaChannelRepository.count();
    }

    public MediaChannel updateChannels(MediaChannel mediaChannel) {
        return mediaChannelRepository.save(mediaChannel);
    }


}
