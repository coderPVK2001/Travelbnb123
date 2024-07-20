package com.TravelPractise6.TravelPractise6.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "bookings")
public class Bookings {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "guest_name", nullable = false, length = 100)
    private String guestName;

    @Column(name = "total_nights", nullable = false)
    private Integer totalNights;

    @Column(name = "email", nullable = false, length = 100)
    private String email;

    @Column(name = "mobile", nullable = false, length = 16)
    private String mobile;

    @Column(name = "total_price")
    private Integer totalPrice;

    @ManyToOne
    @JoinColumn(name = "property_id")
    private Property property;

    @ManyToOne
    @JoinColumn(name = "userdetails_id")
    private Userdetails userdetails;

}