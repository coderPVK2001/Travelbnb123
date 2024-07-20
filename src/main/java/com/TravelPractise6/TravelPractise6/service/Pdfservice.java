package com.TravelPractise6.TravelPractise6.service;

import com.TravelPractise6.TravelPractise6.payload.BookingsDto;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.FileOutputStream;

@Service
public class Pdfservice {


    public boolean generatePdf(BookingsDto bookingsDto ,String path){
        Document document = new Document();

        try {
            PdfWriter.getInstance(document, new FileOutputStream(path));

            document.open();

            // Add centered header
            Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD, 18);
            Paragraph header = new Paragraph("Booking Details ", headerFont);
            header.setAlignment(Element.ALIGN_CENTER);
            document.add(header);

            // Add some space after the header
            document.add(new Paragraph("\n"));
            document.add(new Paragraph("\n"));

            Font font = FontFactory.getFont(FontFactory.COURIER, 16, BaseColor.BLACK);

            Paragraph paragraph1= new Paragraph("Guest Name     : "+bookingsDto.getGuestname(), font);
            Paragraph paragraph2= new Paragraph("Email-id       : "+bookingsDto.getEmail(), font);
            Paragraph paragraph3= new Paragraph("Mobile No      : "+bookingsDto.getMobile(), font);
            Paragraph paragraph4= new Paragraph("Property Name  : "+bookingsDto.getPropertyName(), font);
            Paragraph paragraph5= new Paragraph("Total nights   : "+bookingsDto.getTotalNights(), font);
            Paragraph paragraph6= new Paragraph("Location       : "+bookingsDto.getLocationName(), font);
            Paragraph paragraph7= new Paragraph("Total Price    : "+bookingsDto.getTotalPrice(), font);
            document.add(paragraph1);
            document.add(paragraph2);
            document.add(paragraph3);
            document.add(paragraph4);
            document.add(paragraph5);
            document.add(paragraph6);
            document.add(paragraph7);

//            // Add image
//            String imagePath = "C:\\Users\\Prajwal\\Downloads\\travelbnbb.jpg"; // replace with the actual path to your image
//            Image image = Image.getInstance(imagePath);
//            image.setAlignment(Element.ALIGN_CENTER);
//            document.add(image);

            document.close();
            return true;

        }catch (Exception e){

            e.printStackTrace();
        }
        return false;

    }

}


