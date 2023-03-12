package com.example.demo.request.booking;

import com.example.demo.request.Input;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.*;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class HomeBookingRequest implements Input {
    @Size(max = 100)
    private String location;

    private Long vehicleType;

    @NotNull
    private LocalDateTime fromDate;

    @NotNull
    private LocalDateTime toDate;

    private boolean orderPrice;

    private Long seatType;

    private Long vehicleCompany;

    private Long fuel;

    private Long accountId;

    private Long minPrice;

    private Long maxPrice;


}
