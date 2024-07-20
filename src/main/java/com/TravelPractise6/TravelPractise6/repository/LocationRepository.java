package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, Integer> {
}