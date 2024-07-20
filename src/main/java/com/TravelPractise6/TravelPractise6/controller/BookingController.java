package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.entity.Bookings;
import com.TravelPractise6.TravelPractise6.entity.Property;
import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import com.TravelPractise6.TravelPractise6.exception.PropertyIdNotFoundException;
import com.TravelPractise6.TravelPractise6.payload.BookingsDto;
import com.TravelPractise6.TravelPractise6.repository.BookingsRepository;
import com.TravelPractise6.TravelPractise6.repository.PropertyRepository;
import com.TravelPractise6.TravelPractise6.service.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Optional;

@RestController
@RequestMapping("/api/travel6/v1/bookings")
public class BookingController {

    private BookingsRepository bookingsRepository;
    private PropertyRepository propertyRepository;
    private Pdfservice pdfservice;
    private FileConversionService fileConversionService;
    private BucketService bucketService;
    private SmsService smsService;
    private SmsServiceSIB smsServiceSIB;
    private EmailService emailService;
    private WhatsAppService whatsAppService;

    public BookingController(BookingsRepository bookingsRepository, PropertyRepository propertyRepository, Pdfservice pdfservice, FileConversionService fileConversionService, BucketService bucketService, SmsService smsService, SmsServiceSIB smsServiceSIB, EmailService emailService, WhatsAppService whatsAppService) {
        this.bookingsRepository = bookingsRepository;
        this.propertyRepository = propertyRepository;
        this.pdfservice = pdfservice;
        this.fileConversionService = fileConversionService;
        this.bucketService = bucketService;
        this.smsService = smsService;
        this.smsServiceSIB = smsServiceSIB;
        this.emailService = emailService;
        this.whatsAppService = whatsAppService;
    }

    @PostMapping("/add")
    public ResponseEntity<?> newBooking(
            @AuthenticationPrincipal Userdetails userdetails,
            @RequestParam int propertyId,
            @RequestBody Bookings bookings
            ) {

        Optional<Property> optionalProperty = propertyRepository.findById(propertyId);
        if (optionalProperty.isPresent()) {

            Property property = optionalProperty.get();

            int perNightPrice = property.getPerNightPrice();

            int totalPriceCalculated = perNightPrice * bookings.getTotalNights();

            bookings.setTotalPrice(totalPriceCalculated);
            bookings.setProperty(property);
            bookings.setUserdetails(userdetails);
            Bookings savedBookings = bookingsRepository.save(bookings);


            BookingsDto bookingsDto = new BookingsDto();
            bookingsDto.setGuestname(savedBookings.getGuestName());
            bookingsDto.setEmail(savedBookings.getEmail());
            bookingsDto.setMobile(savedBookings.getMobile());
            bookingsDto.setPropertyName(savedBookings.getProperty().getName());
            bookingsDto.setLocationName(savedBookings.getProperty().getLocation().getName());
            bookingsDto.setCountryName(savedBookings.getProperty().getCountry().getName());
            bookingsDto.setMaxGuests(savedBookings.getProperty().getNoOfGuests());
            bookingsDto.setNumberOfBedrooms(savedBookings.getProperty().getNoOfBedrooms());
            bookingsDto.setTotalNights(savedBookings.getTotalNights());
            bookingsDto.setPerNightPrice(savedBookings.getProperty().getPerNightPrice());
            bookingsDto.setTotalPrice(savedBookings.getTotalPrice());
            System.out.println("hello");

            //pdf generation
            Integer bookingsId = savedBookings.getId();
            String path = "C:\\Users\\Prajwal\\intellij_workspace\\TravelPractise6\\TravelPractise6\\bookingpdfs\\" + bookingsId + "-confirmed.pdf";
            boolean status = pdfservice.generatePdf(bookingsDto, path); //to check whether pdf got generated or not

            //aws bucket upload
            if (status) {
                MultipartFile multipartFile = fileConversionService.getMultipartFileFromPath(path);
                String bucketname = "bookingstravel6";
                String url = bucketService.uploadFile(multipartFile, bucketname);

//                //sending sms
//                smsService.sendSms(bookingsDto.getMobile(), " Your Booking is confirmed !! please check the booking details:-"+url);
//                smsServiceSIB.sendSms(bookingsDto.getMobile()," Your Booking is confirmed !! please check the booking details:-"+url);

                emailService.sendemail(bookingsDto.getEmail(),"Booking details", " Your Booking is confirmed !! please check the booking details:- "+ url);

                whatsAppService.sendMessage(bookingsDto.getMobile()," Your Booking is confirmed !! please check the booking details:- "+ url );

                return new ResponseEntity<>(url, HttpStatus.CREATED);
            } else {
                return new ResponseEntity<>("pdf not generated", HttpStatus.BAD_REQUEST);
            }

        } else {
            throw new PropertyIdNotFoundException("property id " + propertyId + " is invalid /Not available at the moment !!");
        }
    }

}
