package com.example.demo.repository;

import com.example.demo.entity.email;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface repositoryEmail extends JpaRepository<email,Integer> {
    @Query(nativeQuery = true,value = "select Count(*) from email e where e.email_from =?1")
    int findCount(String emailFrom);
}
