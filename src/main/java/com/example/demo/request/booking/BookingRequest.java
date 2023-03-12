package com.example.demo.request.booking;

import com.example.demo.request.Input;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class BookingRequest implements Input {

    @NotNull
    @Min(1)
    private Long vehicleId;

    private Long accountId;

    private String name;

    private String address;

    private String phone;

    private LocalDate bod;

    private String cardId;

    private Long total;

    @NotNull
    private LocalDateTime from;

    @NotNull
    private LocalDateTime to;
}
