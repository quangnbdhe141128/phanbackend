package com.example.demo.service;

import com.example.demo.response.vehicle.VehicleDetailsResponse;
import com.example.demo.response.vehicle.GetVehiclesResponse;
import org.springframework.stereotype.Component;

@Component
public interface VehicleService {

    GetVehiclesResponse getVehicleType();

    GetVehiclesResponse getVehicleCompany();
    GetVehiclesResponse getTranmistionType();
    GetVehiclesResponse getSeatType();
    GetVehiclesResponse getFuelType();

    GetVehiclesResponse getShop();

    VehicleDetailsResponse getVehicle(Long id);

}
