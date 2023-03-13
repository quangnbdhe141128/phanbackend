package com.example.demo.web.controller;

import com.example.demo.request.booking.BookingRequest;
import com.example.demo.request.booking.HomeBookingRequest;
import com.example.demo.request.vehicle_owner.EditBookingRequest;
import com.example.demo.request.vehicle_owner.OwnerBookingRequest;
import com.example.demo.response.booking.BookingResponse;
import com.example.demo.response.booking.HomeBookingResponse;
import com.example.demo.service.BookingService;
import com.example.demo.service.VehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/vehicle-owner")
public class VehicleOwnerController extends BaseController {

    @Autowired
    private VehicleOwnerService vehicleOwnerService;


    @PostMapping("/booking")
    @PreAuthorize("hasRole('ROLE_RENTAL') OR hasRole('ROLE_SHOP')")
    public ResponseEntity<?> bookingDetail(@Valid @RequestBody OwnerBookingRequest request) {
        return successResponse(vehicleOwnerService.listBooking(request));
    }

    @PutMapping("/booking/edit")
    @PreAuthorize("hasRole('ROLE_RENTAL') OR hasRole('ROLE_SHOP')")
    public ResponseEntity<?> editBooking(@Valid @RequestBody EditBookingRequest request) {
        vehicleOwnerService.editBooking(request);
        return successResponse();
    }

}