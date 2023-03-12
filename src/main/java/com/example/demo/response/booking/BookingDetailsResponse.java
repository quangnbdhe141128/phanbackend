package com.example.demo.response.booking;

import com.example.demo.response.Output;
import com.example.demo.response.vehicle.VehicleDetailsResponse;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDetailsResponse implements Output {
    private VehicleDetailsResponse vehicleDetails;

    private LocalDateTime from;
    private LocalDateTime to;

    private String location;

    private Long total;

    private String name;

    private Long accountId;

    private String address;
    private String phone;
    private LocalDate bod;
    private String cardId;

    private int status;


}
