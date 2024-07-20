package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Property;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface PropertyRepository extends JpaRepository<Property, Integer> {

    @Query("SELECT p from Property p JOIN Country c ON p.country=c.id JOIN Location l ON p.location=l.id WHERE c.name=:searchname OR l.name=:searchname")
    List<Property> searchHotel(@Param("searchname") String name);

    Optional<Property> findByName(String propertyName);
}