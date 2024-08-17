package com.example.mediasystemspring.Models;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Data
public class Staff extends User{

    @ManyToOne
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
