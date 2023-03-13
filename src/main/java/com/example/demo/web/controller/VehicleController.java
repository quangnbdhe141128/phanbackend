package com.example.demo.web.controller;

import com.example.demo.request.booking.HomeBookingRequest;
import com.example.demo.response.booking.HomeBookingResponse;
import com.example.demo.service.BookingService;
import com.example.demo.service.VehicleService;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/anonymous/vehicle")
public class VehicleController extends BaseController{

    @Autowired
    private VehicleService vehicleService;

    @GetMapping("type")
    public ResponseEntity<?> getVehicleType() {

        return successResponse(vehicleService.getVehicleType());
    }

    @GetMapping("company")
    public ResponseEntity<?> getVehicleCompany() {

        return successResponse(vehicleService.getVehicleCompany());
    }

    @GetMapping("tranmistion")
    public ResponseEntity<?> getTranmistionType() {

        return successResponse(vehicleService.getTranmistionType());
    }

    @GetMapping("seat")
    public ResponseEntity<?> getSeatType() {

        return successResponse(vehicleService.getSeatType());
    }

    @GetMapping("fuel")
    public ResponseEntity<?> getFuelType() {

        return successResponse(vehicleService.getFuelType());
    }


    @GetMapping("shop")
    public ResponseEntity<?> getShop() {

        return successResponse(vehicleService.getShop());
    }

    @GetMapping("details/{id}")
    public ResponseEntity<?> getVehicle(@PathVariable Long id) {

        return successResponse(vehicleService.getVehicle(id));
    }


}