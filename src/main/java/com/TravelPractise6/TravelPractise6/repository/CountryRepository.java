package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Country;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CountryRepository extends JpaRepository<Country, Integer> {
}