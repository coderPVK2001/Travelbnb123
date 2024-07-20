package com.TravelPractise6.TravelPractise6.utils;

import com.TravelPractise6.TravelPractise6.entity.Userdetails;
import org.apache.catalina.User;

import java.io.PrintWriter;
import java.util.List;

public class CsvUtil {

    public static void bookingsToCSV(PrintWriter writer, List<Userdetails> userdetails) {
        writer.write("Userid,Role,Username\n");

        for (Userdetails userdetails1:userdetails) {
            writer.write(userdetails1.getId() + ","
                    + userdetails1.getRole()     + ","
                    + userdetails1.getUsername() + "\n");
        }
    }

}
