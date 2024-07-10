package com.example.mediasystemspring.Models;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Entity
@Data
public class Staff extends User{

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "media_id", nullable = false)
    private MediaChannel mediaChannel;

    public void addUserToChannel(MediaChannel channel) {
        this.mediaChannel = channel;
    }

    public void setMediaChannel(MediaChannel mediaChannel) {

        this.mediaChannel = mediaChannel;
    }

    public MediaChannel getMediaChannel() {

        return mediaChannel;

    }
}
