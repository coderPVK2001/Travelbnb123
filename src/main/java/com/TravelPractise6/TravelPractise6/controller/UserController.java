package com.TravelPractise6.TravelPractise6.controller;

import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import com.TravelPractise6.TravelPractise6.payload.LoginDto;
import com.TravelPractise6.TravelPractise6.repository.UserdetailsRepository;
import com.TravelPractise6.TravelPractise6.service.JwtService;
import com.TravelPractise6.TravelPractise6.utils.CsvUtil;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCrypt;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/travel6")
public class UserController {

    private UserdetailsRepository urepo;
    private JwtService jservice;

    public UserController(UserdetailsRepository urepo, JwtService jservice) {
        this.urepo = urepo;
        this.jservice = jservice;
    }

    @PostMapping("/add")
    public ResponseEntity<Userdetails> add(@RequestBody Userdetails ud){

        ud.setPassword(BCrypt.hashpw(ud.getPassword(),BCrypt.gensalt(10)));
        Userdetails save = urepo.save(ud);
        return new ResponseEntity<>(save, HttpStatus.CREATED);
    }

    @PostMapping("/verify")
    public ResponseEntity<?> verify(@RequestBody LoginDto dto){

        Optional<Userdetails> opt=urepo.findByUsername(dto.getUsername());

        if(opt.isPresent()){
            Userdetails userdetails = opt.get();

            if(BCrypt.checkpw(dto.getPassword(), userdetails.getPassword())){

                String token = jservice.generateToken(userdetails.getUsername());
                return new ResponseEntity<>(token, HttpStatus.OK);
            }
        }
       return new ResponseEntity<>("invalid ",HttpStatus.BAD_REQUEST);
    }

    @GetMapping("/csv")
    public void getBookingsInCSV(HttpServletResponse response) throws IOException {
        response.setContentType("text/csv");
        response.setHeader("Content-Disposition", "attachment; filename=bookings.csv");

        List<Userdetails> userlist = urepo.findAll();
        CsvUtil.bookingsToCSV(response.getWriter(), userlist);
    }

}
