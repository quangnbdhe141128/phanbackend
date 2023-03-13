package com.example.demo.service.impl;

import com.example.demo.domain.dto.FeedBackDto;
import com.example.demo.domain.dto.VehicleDetailDto;
import com.example.demo.domain.model.*;
import com.example.demo.repository.*;
import com.example.demo.response.vehicle.GetVehiclesResponse;
import com.example.demo.response.vehicle.VehicleDetailsResponse;
import com.example.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleServiceImpl implements VehicleService {
    @Autowired
    VehicleTypeRepository vehicleTypeRepository;
    @Autowired
    VehicleCompanyRepository vehicleCompanyRepository;
    @Autowired
    TranmistionTypeRepository tranmistionTypeRepository;
    @Autowired
    SeatTypeRepository seatTypeRepository;
    @Autowired
    FuelTypeRepository fuelTypeRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    VehicleRepository vehicleRepository;


    @Override
    public GetVehiclesResponse getVehicleType() {
        List<VehicleType> list = vehicleTypeRepository.findAll();
        List<VehicleDetailDto> dtos = new ArrayList<>();
        for (VehicleType type : list) {
            dtos.add(new VehicleDetailDto(type.getId(), type.getName()));
        }
        return new GetVehiclesResponse(dtos);
    }

    @Override
    public GetVehiclesResponse getVehicleCompany() {
        List<VehicleCompany> list = vehicleCompanyRepository.findAll();
        List<VehicleDetailDto> dtos = new ArrayList<>();
        for (VehicleCompany type : list) {
            dtos.add(new VehicleDetailDto(type.getId(), type.getName()));
        }
        return new GetVehiclesResponse(dtos);
    }

    @Override
    public GetVehiclesResponse getTranmistionType() {
        List<TranmistionType> list = tranmistionTypeRepository.findAll();
        List<VehicleDetailDto> dtos = new ArrayList<>();
        for (TranmistionType type : list) {
            dtos.add(new VehicleDetailDto(type.getId(), type.getName()));
        }
        return new GetVehiclesResponse(dtos);
    }

    @Override
    public GetVehiclesResponse getSeatType() {
        List<SeatType> list = seatTypeRepository.findAll();
        List<VehicleDetailDto> dtos = new ArrayList<>();
        for (SeatType type : list) {
            dtos.add(new VehicleDetailDto(type.getId(), type.getName()));
        }
        return new GetVehiclesResponse(dtos);
    }

    @Override
    public GetVehiclesResponse getFuelType() {
        List<FuelType> list = fuelTypeRepository.findAll();
        List<VehicleDetailDto> dtos = new ArrayList<>();
        for (FuelType type : list) {
            dtos.add(new VehicleDetailDto(type.getId(), type.getName()));
        }
        return new GetVehiclesResponse(dtos);
    }

    @Override
    public GetVehiclesResponse getShop() {
        List<Account> list = accountRepository.findShop();
        List<VehicleDetailDto> dtos = new ArrayList<>();
        for (Account type : list) {
            dtos.add(new VehicleDetailDto(type.getId(), type.getName()));
        }
        return new GetVehiclesResponse(dtos);
    }

    @Override
    public VehicleDetailsResponse getVehicle(Long id) {
        VehicleDetailsResponse response = new VehicleDetailsResponse();
        Optional<Vehicle> vehicle = vehicleRepository.findByIdAndStatusIsTrue(id);

        response.setOwner(vehicle.get().getAccount().getName());
        response.setPhone(vehicle.get().getAccount().getPhone());
        response.setRule(vehicle.get().getRule());

        List<String> images = new ArrayList<>();
        for (Image image : vehicle.get().getImageList()) {
            images.add(image.getName());
        }
        response.setImageList(images);
        response.setColor(vehicle.get().getColorType().getName());
        response.setFuel(vehicle.get().getFuelType().getName());
        response.setSeat(vehicle.get().getSeatType().getName());

        List<String> featureList = new ArrayList<>();
        for (Feature feature : vehicle.get().getFeatureList()) {
            featureList.add(feature.getName());
        }
        response.setFeatureList(featureList);
        response.setPrice(vehicle.get().getPrice());
        Long totalRun = 0L;
        List<FeedBackDto> feedBackList = new ArrayList<>();
        for (Booking booking : vehicle.get().getBookingList()) {
            if (booking.getStatus() == 1 && booking.getToDate().isBefore(LocalDate.now())) {
                totalRun++;
                for (FeedBack feedBack : booking.getFeedBackList()) {
                    if (feedBack.isRoleType()) {
                        feedBackList.add(new FeedBackDto(booking.getAccount().getName(), feedBack.getPoint(), feedBack.getContent()));
                    }
                }
            }

        }
        response.setTotalRun(totalRun);
        response.setFeedBackList(feedBackList);
        response.setLocation(vehicle.get().getLocation());

        return response;
    }
}
