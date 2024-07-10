package com.example.mediasystemspring.Models;

import jakarta.persistence.*;
import lombok.Data;
import org.springframework.data.annotation.CreatedDate;

import java.time.LocalDate;

@Entity
@Data
@Table(name = "media_channels")
public class MediaChannel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long mediaId;

    @Column(nullable = false)
    private String mediaName;

    @Column(nullable = false)
    private String mediaDescription;

    @Column(nullable = false)
    private String mediaEmail;

    @Column(nullable = false)
    private String mediaWebUrl;

    @Column(nullable = false)
    private String mediaType;

    @Lob
    @Column(length = 100000)
    private byte[] image;

    @Column(nullable = false)
    @CreatedDate
    private LocalDate createdDate;

    @Column(nullable = false)
    private String status;

    public Long getMediaId() {
        return mediaId;
    }

    public void setMediaId(Long mediaId) {
        this.mediaId = mediaId;
    }

    public String getMediaName() {
        return mediaName;
    }

    public void setMediaName(String mediaName) {
        this.mediaName = mediaName;
    }

    public String getMediaDescription() {
        return mediaDescription;
    }

    public void setMediaDescription(String mediaDescription) {
        this.mediaDescription = mediaDescription;
    }

    public String getMediaEmail() {
        return mediaEmail;
    }

    public void setMediaEmail(String mediaEmail) {
        this.mediaEmail = mediaEmail;
    }

    public String getMediaWebUrl() {
        return mediaWebUrl;
    }

    public void setMediaWebUrl(String mediaWebUrl) {
        this.mediaWebUrl = mediaWebUrl;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMediaType() {
        return mediaType;
    }

    public void setMediaType(String mediaType) {
        this.mediaType = mediaType;
    }

    public LocalDate getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDate createdDate) {
        this.createdDate = createdDate;
    }
}
