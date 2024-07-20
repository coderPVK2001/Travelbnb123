package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Bookings;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BookingsRepository extends JpaRepository<Bookings, Integer> {
}