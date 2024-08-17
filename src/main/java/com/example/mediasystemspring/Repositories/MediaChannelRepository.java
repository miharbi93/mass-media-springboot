package com.example.mediasystemspring.Repositories;

import com.example.mediasystemspring.Models.MediaChannel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MediaChannelRepository extends JpaRepository<MediaChannel,Long>{


}
