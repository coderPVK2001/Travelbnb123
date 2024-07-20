package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Favourites;
import com.TravelPractise6.TravelPractise6.entity.Property;
import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.parameters.P;

import java.util.List;
import java.util.Optional;

public interface FavouritesRepository extends JpaRepository<Favourites, Integer> {

    @Query("SELECT f FROM Favourites f where f.userdetails=:user")
    List<Favourites> findByUser(@Param("user") Userdetails userdetails);

    @Query("SELECT f FROM Favourites f where f.userdetails=:user and f.property=:property")
    Optional<Favourites> findByUserAndProperty(@Param("user") Userdetails userdetails, @Param("property") Property property);
}