package com.TravelPractise6.TravelPractise6.repository;

import com.TravelPractise6.TravelPractise6.entity.Reviews;
import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ReviewsRepository extends JpaRepository<Reviews, Integer> {

    @Query( "select r from Reviews r JOIN Property p ON r.property=p.id JOIN Userdetails u ON r.userdetails=u.id where u.id=:userid AND p.id=:propertyid")
    Optional<Reviews> findByUseridAndPropertyId(@Param("userid") int id, @Param("propertyid") int propertyId) ;

    //get list of reviews of particular hotel/property
    @Query( "select r from Reviews r JOIN Property p ON r.property=p.id where p.id=:propertyid")
    List<Reviews> findByPropertyName(@Param("propertyid") int propertyid) ;

    //get list of reviews by particular user object
    @Query("select r from Reviews r JOIN Userdetails u ON r.userdetails=u.id WHERE r.userdetails=:userdetails")
    List<Reviews> findByUserId(@Param("userdetails") Userdetails userdetails);
}