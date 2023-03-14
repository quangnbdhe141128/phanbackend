package com.example.demo.service.impl;

import com.example.demo.config.exception.InvalidException;
import com.example.demo.domain.dto.BookingDto;
import com.example.demo.domain.dto.VehicleDto;
import com.example.demo.domain.model.*;
import com.example.demo.repository.*;
import com.example.demo.request.vehicle_owner.CreateVehicleRequest;
import com.example.demo.request.vehicle_owner.EditBookingRequest;
import com.example.demo.request.vehicle_owner.OwnerBookingRequest;
import com.example.demo.response.vehicle_owner.GetVehiclesResponse;
import com.example.demo.response.vehicle_owner.OwnerBookingResponse;
import com.example.demo.service.VehicleOwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class VehicleOwnerServiceImpl implements VehicleOwnerService {

    @Autowired
    VehicleRepository vehicleRepository;
    @Autowired
    BookingRepository bookingRepository;

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
    ColorTypeRepository colorTypeRepository;


    @Override
    public OwnerBookingResponse listBooking(OwnerBookingRequest request) {
        if (request.getKeyword() == null) {
            request.setKeyword("");
        }
        List<Booking> list = bookingRepository.findByVehicle(request);
        if (request.getStatus() == null) {
        } else if (request.getStatus().equals(true)) {
            list.sort(Comparator.comparing(Booking::getStatus));
        } else {
            list.sort(Comparator.comparing(Booking::getStatus).reversed());
        }
        if (request.getCreateDate() == null) {
        } else if (request.getStatus().equals(true)) {
            list.sort(Comparator.comparing(Booking::getCreateDate));
        } else {
            list.sort(Comparator.comparing(Booking::getCreateDate).reversed());
        }
        OwnerBookingResponse response = new OwnerBookingResponse();
        List<BookingDto> dtoList = new ArrayList<>();
        for (Booking booking : list) {
            BookingDto dto = new BookingDto();
            dto.setNameCustomer(booking.getAccount().getName());
            dto.setNameVehicle(booking.getVehicle().getName());
            dto.setPhone(booking.getAccount().getPhone());
            dto.setBookingId(booking.getId());
            dto.setBod(booking.getAccount().getBod());
            dto.setCardId(booking.getAccount().getCardId());
            dto.setAddress(booking.getAddress());
            dto.setCreateDate(booking.getCreateDate());
            dto.setFrom(booking.getFromDate());
            dto.setTo(booking.getToDate());
            dto.setPoint(booking.getVehicle().getPoint());
            dto.setStatus(booking.getStatus());

            String status = setStatusString(booking);
            dto.setStatusString(status);
            dto.setTotal(booking.getTotalPrice());
            dto.setPrice(booking.getVehicle().getPrice());

            List<String> images = new ArrayList<>();
            for (Image image : booking.getVehicle().getImageList()) {
                images.add(image.getName());
            }

            dto.setImages(images);
            dtoList.add(dto);
        }
        response.setList(dtoList);
        return response;
    }

    @Override
    public void editBooking(EditBookingRequest request) {
        Booking booking = bookingRepository.findById(request.getBookingId()).get();

        Optional<Vehicle> vehicle = vehicleRepository.findById(booking.getVehicle().getId());
        for (Booking book : vehicle.get().getBookingList()) {
            if (booking.getId().equals(book.getId())) {
                continue;
            }
            if (((request.getFrom() != null && request.getFrom().isAfter(book.getFromDate()) && request.getFrom().isBefore(book.getToDate()))
                    || (request.getFrom() != null && request.getTo().isAfter(book.getFromDate()) && request.getTo().isBefore(book.getToDate()))) && book.getStatus() == 1) {
                throw new InvalidException("Xe đã được đặt tại thời gian bạn", "Xe đã được đặt tại thời gian bạn");
            }
        }
        if (request.getName() != null && !request.getName().isBlank()) {
            booking.getAccount().setName(request.getName());
        }
        if (request.getCardId() != null && !request.getCardId().isBlank()) {
            booking.getAccount().setCardId(request.getCardId());
        }
        if (request.getPhone() != null && !request.getPhone().isBlank()) {
            booking.getAccount().setPhone(request.getPhone());
        }
        if (request.getFrom() != null) {
            booking.setFromDate(request.getFrom());
        }
        if (request.getTo() != null) {
            booking.setToDate(request.getTo());
        }
        if (request.getTotal() != null) {
            booking.setTotalPrice(request.getTotal());
        }
        if (request.getIsAccept() != null && request.getIsAccept().equals(true)) {
            booking.setStatus(1);
        }
        if (request.getIsAccept() != null && request.getIsAccept().equals(false)) {
            booking.setStatus(2);
        }
        if (request.getBod() != null) {
            booking.getAccount().setBod(request.getBod());
        }
        bookingRepository.save(booking);
    }

    @Override
    public GetVehiclesResponse getVehicles(Long id) {
        GetVehiclesResponse response = new GetVehiclesResponse();

        List<Vehicle> list = vehicleRepository.findByAccount_Id(id);
        List<VehicleDto> dtos = new ArrayList<>();
        for (Vehicle vehicle : list) {
            dtos.add(new VehicleDto(vehicle.getId(), vehicle.getName(), vehicle.getLicensePlates(), vehicle.getStatus()));
        }
        response.setList(dtos);

        return response;
    }

    @Override
    public void createVehicle(CreateVehicleRequest request) {
        Account account = accountRepository.getById(request.getAccountId());
        VehicleType type = vehicleTypeRepository.getById(request.getType());
        ColorType colorType = colorTypeRepository.getById(request.getColor());
        FuelType fuelType = fuelTypeRepository.getById(request.getFuel());
        SeatType seatType = seatTypeRepository.getById(request.getSeat());
        TranmistionType tranmistionType = tranmistionTypeRepository.getById(request.getTranmistion());
        VehicleCompany vehicleCompany = vehicleCompanyRepository.getById(request.getVehicleCompany());

        Vehicle vehicle = new Vehicle();
        vehicle.setName(request.getName());
        vehicle.setAccount(account);
        vehicle.setVehicleType(type);
        vehicle.setColorType(colorType);
        vehicle.setFuelType(fuelType);
        vehicle.setSeatType(seatType);
        vehicle.setTranmistionType(tranmistionType);
        vehicle.setVehicleCompany(vehicleCompany);
        vehicle.setPrice(request.getPrice());
        vehicle.setLocation(request.getLocation());
        List<Image> imageList = new ArrayList<>();
        for (String s : request.getImageList()){
            imageList.add(new Image(s));
        }
        vehicle.setImageList(imageList);

        List<Feature> featureList = new ArrayList<>();
        for (String s : request.getFeatureList()){
            featureList.add(new Feature(s));
        }
        vehicle.setFeatureList(featureList);
        vehicle.setPoint(100L);
        vehicle.setRule(request.getRule());
        vehicle.setLicensePlates(request.getLicensePlates());

        vehicleRepository.save(vehicle);

    }

    @Override
    public void deleteVehicle(Long id) {
        vehicleRepository.deleteVehicleById(id);
    }

    public String setStatusString(Booking booking) {
        if (booking.getStatus() == 1 && (LocalDate.now().isAfter(booking.getFromDate()) && LocalDate.now().isBefore(booking.getToDate()))) {
            return "Đang diễn ra";
        }
        if (booking.getStatus() == 1 && LocalDate.now().isAfter(booking.getToDate())) {
            return "Kết thúc";
        }
        if (booking.getStatus() == 1 && LocalDate.now().isBefore(booking.getFromDate())) {
            return "Chấp nhận";
        }
        if (booking.getStatus() == 0 && LocalDate.now().isBefore(booking.getToDate())) {
            return "Đang chờ";
        }
        if (booking.getStatus() == 2 || (booking.getStatus() == 0 && LocalDate.now().isAfter(booking.getFromDate()))) {
            return "Từ chối";
        }
        return "";
    }

}
