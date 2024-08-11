package com.example.mediasystemspring.Models;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "media_services")
public class MediaService {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long serviceId;

    @Column(nullable = false)
    private String serviceName;

    @Column(nullable = false)
    private String serviceDescription;

    @Column(nullable = false)
    private Long servicePrice;

    @ManyToOne
    @JoinColumn(name = "mass_media_channel_id", nullable = false)
    private MediaChannel mediaChannel;

    public Long getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Long servicePrice) {
        this.servicePrice = servicePrice;
    }

    public Long getServiceId() {
        return serviceId;
    }

    public void setServiceId(Long serviceId) {
        this.serviceId = serviceId;
    }

    public String getServiceName() {
        return serviceName;
    }

    public void setServiceName(String serviceName) {
        this.serviceName = serviceName;
    }

    public MediaChannel getMediaChannel() {
        return mediaChannel;
    }

    public void setMediaChannel(MediaChannel mediaChannel) {
        this.mediaChannel = mediaChannel;
    }

    public String getServiceDescription() {
        return serviceDescription;
    }

    public void setServiceDescription(String serviceDescription) {
        this.serviceDescription = serviceDescription;
    }
}
