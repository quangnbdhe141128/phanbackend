package com.example.demo.request.vehicle_owner;

import com.example.demo.request.Input;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class OwnerBookingRequest implements Input {

   private Boolean createDate;
   private Boolean status;

   private String keyword;
   private Long ownerId;
}
