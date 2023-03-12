package com.example.demo.service;

import com.example.demo.request.vehicle_owner.EditBookingRequest;
import com.example.demo.request.vehicle_owner.OwnerBookingRequest;
import com.example.demo.response.vehicle_owner.OwnerBookingResponse;
import org.springframework.stereotype.Component;

@Component
public interface VehicleOwnerService {

    OwnerBookingResponse listBooking(OwnerBookingRequest request);

    void editBooking(EditBookingRequest request);
}
