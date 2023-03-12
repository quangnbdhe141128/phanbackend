package com.example.demo.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingDto {

    private String nameCustomer;
    private String nameVehicle;
    private String phone;
    private Long bookingId;

    private LocalDate bod;
    private String cardId;
    private String address;
    private LocalDateTime createDate;
    private LocalDateTime from;
    private LocalDateTime  to;

    private Long point;
    private int status;

    private String statusString;

    private List<String> images;

    private Long price;
    private Long total;
}
