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

    @Column(nullable = false)
    private String serviceStatus;

    @ManyToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "mass_media_channel_id", nullable = false)
    private MediaChannel mediaChannel;

    public Long getServicePrice() {
        return servicePrice;
    }

    public void setServicePrice(Long servicePrice) {
        this.servicePrice = servicePrice;
    }
}
