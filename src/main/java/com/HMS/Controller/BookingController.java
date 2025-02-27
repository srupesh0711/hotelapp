package com.HMS.Controller;

import com.HMS.Entity.Bookigs;
import com.HMS.Entity.Property;
import com.HMS.Entity.Rooms;
import com.HMS.Repository.BookigsRepository;
import com.HMS.Repository.PropertyRepository;
import com.HMS.Repository.RoomsRepository;
import com.HMS.Service.PDFService;
import com.HMS.Service.TwilioSmsService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/bookings")
public class BookingController {
    private PDFService pdfService;
    private PropertyRepository propertyRepository;
    private BookigsRepository bookigsRepository;
    private RoomsRepository roomsRepository;
    private TwilioSmsService twilioSmsService;

    public BookingController(PDFService pdfService, PropertyRepository propertyRepository, BookigsRepository bookigsRepository, RoomsRepository roomsRepository, TwilioSmsService twilioSmsService) {
        this.pdfService = pdfService;
        this.propertyRepository = propertyRepository;
        this.bookigsRepository = bookigsRepository;
        this.roomsRepository = roomsRepository;
        this.twilioSmsService = twilioSmsService;
    }
    @PostMapping("/booking")
    public ResponseEntity<?> createBookings(
            @RequestParam long propertyId,
            @RequestParam String type,
            @RequestBody Bookigs bookigs) {

            Property property = propertyRepository.findById(propertyId).get();
            List<Rooms> rooms = roomsRepository.findByTypeAndProperty(bookigs.getFromDate(), bookigs.getToDate(),type,property);
            for(Rooms room : rooms) {
                if(room.getCount()==0){
                    return new ResponseEntity<>("no rooms"+ room.getDate(),  HttpStatus.INTERNAL_SERVER_ERROR);
                }
            }
//             Rooms byTypeAndProperty = roomsRepository.findByTypeAndProperty(type, property);
//             if(byTypeAndProperty.getCount()==0){
//                 return "No rooms Available";
//             }
           Bookigs saved = bookigsRepository.save(bookigs);
            if(saved!=null){
            for(Rooms room: rooms){
                 room.setCount(room.getCount()-1);
                 roomsRepository.save(room);
             }
            }
            pdfService.generateBookingPdf(
                    "C:\\Intelli J\\HMS_Bookings\\confirmation-order"+saved.getId()+".pdf",property);
            twilioSmsService.sendSms("","","");
           return new ResponseEntity<>(rooms,HttpStatus.OK);
    }


}
