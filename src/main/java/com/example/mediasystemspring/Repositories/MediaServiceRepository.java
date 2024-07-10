package com.example.mediasystemspring.Repositories;

import com.example.mediasystemspring.Models.MediaService;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MediaServiceRepository extends JpaRepository<MediaService,Long> {

    @Query("SELECT ms FROM MediaService ms JOIN FETCH ms.mediaChannel mc WHERE mc.mediaId = :mediaId")
    List<MediaService> findAllByMediaChannelMediaId(Long mediaId);

}
