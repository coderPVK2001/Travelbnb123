package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserdetailsRepository extends JpaRepository<Userdetails, Integer> {
    Optional<Userdetails> findByUsername(String username);
}