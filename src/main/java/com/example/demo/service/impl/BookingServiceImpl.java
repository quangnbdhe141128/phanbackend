package com.example.demo.service.impl;

import com.example.demo.config.exception.InvalidException;
import com.example.demo.domain.dto.VehicleDto;
import com.example.demo.domain.model.*;
import com.example.demo.repository.AccountRepository;
import com.example.demo.repository.BookingRepository;
import com.example.demo.repository.RoleRepository;
import com.example.demo.repository.VehicleRepository;
import com.example.demo.request.booking.BookingRequest;
import com.example.demo.request.booking.HomeBookingRequest;
import com.example.demo.response.booking.BookingDetailsResponse;
import com.example.demo.response.booking.BookingResponse;
import com.example.demo.response.booking.HomeBookingResponse;
import com.example.demo.service.BookingService;
import com.example.demo.service.VehicleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class BookingServiceImpl implements BookingService {
    @Autowired
    VehicleRepository vehicleRepository;

    @Autowired
    BookingRepository bookingRepository;

    @Autowired
    AccountRepository accountRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    VehicleService vehicleService;

    @Override
    public HomeBookingResponse getVehicle(HomeBookingRequest request) {
        LocalDateTime dateTime = LocalDateTime.now();
        if (request.getFromDate().isBefore(dateTime) || request.getToDate().isBefore(dateTime) || request.getFromDate().isAfter(request.getToDate())) {
            throw new InvalidException(" Thời gian đăng ký phải ơ tương lai ", " Thời gian đăng ký phải ơ tương lai");
        }
        List<Vehicle> list;
        if (request.isOrderPrice()) {
            list = vehicleRepository.getVehicleOrderPriceASC(request);
        } else {
            list = vehicleRepository.getVehicleeOrderPriceDESC(request);
        }
        List<VehicleDto> dtos = new ArrayList<>();
        for (Vehicle vehicle : list) {
            boolean check = false;
            for (Booking booking : vehicle.getBookingList()) {
                if (((request.getFromDate().isAfter(booking.getFromDate()) && request.getFromDate().isBefore(booking.getToDate()))
                        || (request.getToDate().isAfter(booking.getFromDate()) && request.getToDate().isBefore(booking.getToDate()))) && booking.getStatus() == 1) {
                    check = true;
                    break;
                }
            }
            if (check) {
                continue;
            }
            dtos.add(new VehicleDto(vehicle.getId(), vehicle.getName(), vehicle.getLocation(), vehicle.getImageList().get(0).getName(), vehicle.getPrice()));
        }
        HomeBookingResponse response = new HomeBookingResponse();
        response.setList(dtos);
        return response;
    }

    @Override
    public BookingResponse booking(BookingRequest request) {
        BookingResponse response = new BookingResponse();
        Optional<Vehicle> vehicle = vehicleRepository.findById(request.getVehicleId());
        for (Booking booking : vehicle.get().getBookingList()) {
            if (((request.getFrom().isAfter(booking.getFromDate()) && request.getFrom().isBefore(booking.getToDate()))
                    || (request.getTo().isAfter(booking.getFromDate()) && request.getTo().isBefore(booking.getToDate()))) && booking.getStatus() == 1) {
                throw new InvalidException("Xe đã được đặt tại thời gian bạn", "Xe đã được đặt tại thời gian bạn");
            }
        }
        if (!vehicleRepository.existsById(request.getVehicleId())) {
            throw new InvalidException("Xe không tồn tại", "Xe không tồn tại");
        }
        Long accountId = request.getAccountId();
        if (request.getAccountId() == null || request.getAccountId().equals(0L)) {
            if (request.getCardId() == null || request.getBod() == null || request.getPhone() == null || request.getAddress() == null || request.getName() == null) {
                throw new InvalidException("Khách hàng đang nhập thiếu dữ liệu cần thiết", "Khách hàng đang nhập thiếu dữ liệu cần thiết");

            }
            Account account = new Account();
            account.setName(request.getName());
            account.setAddress(request.getAddress());
            account.setPhone(request.getPhone());
            account.setBod(request.getBod());
            account.setCardId(request.getCardId());

            Optional<Role> role = roleRepository.findByName(ERole.ROLE_ANONYMOUS);
            account.setRole(role.get());
            accountRepository.save(account);
            accountId = account.getId();
        }
        Optional<Account> account = accountRepository.findById(accountId);
        if (!account.isPresent()) {
            throw new InvalidException("User không tồn tại", "User không tồn tại");
        }
        Booking booking = new Booking();
        booking.setAddress(vehicle.get().getLocation());
        booking.setCreateDate(LocalDateTime.now());
        booking.setFromDate(request.getFrom());
        booking.setToDate(request.getTo());
        booking.setStatus(0);
        booking.setTotalPrice(request.getTotal());
        booking.setVehicle(vehicle.get());
        booking.setAccount(account.get());
        bookingRepository.save(booking);
        response.setKey(booking.getId());
        return response;
    }

    @Override
    public BookingDetailsResponse bookingDetails(Long id) {

        BookingDetailsResponse response = new BookingDetailsResponse();

        Optional<Booking> booking = bookingRepository.findById(id);
        response.setVehicleDetails(vehicleService.getVehicle(booking.get().getVehicle().getId()));
        response.setFrom(booking.get().getFromDate());
        response.setTo(booking.get().getToDate());
        response.setLocation(booking.get().getAddress());
        response.setTotal(booking.get().getTotalPrice());
        response.setName(booking.get().getAccount().getName());
        response.setAccountId(booking.get().getAccount().getId());
        response.setAddress(booking.get().getAccount().getAddress());
        response.setPhone(booking.get().getAccount().getPhone());
        response.setBod(booking.get().getAccount().getBod());
        response.setCardId(booking.get().getAccount().getCardId());
        response.setStatus(booking.get().getStatus());

        return response;
    }


}
