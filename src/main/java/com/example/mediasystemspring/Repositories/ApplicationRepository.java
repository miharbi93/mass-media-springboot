package com.example.mediasystemspring.Repositories;

import com.example.mediasystemspring.Models.Application;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ApplicationRepository extends JpaRepository<Application,Long> {

    Application findApplicationByApplicationId(Long applicationId);
}
