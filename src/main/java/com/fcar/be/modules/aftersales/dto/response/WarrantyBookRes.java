package com.fcar.be.modules.aftersales.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class WarrantyBookRes {
    Long id;
    String carVin;
    String licensePlate;
    LocalDate startDate;
    LocalDate endDate;
    LocalDateTime createdAt;
    boolean isExpired; // Thuộc tính tính toán thêm cho Frontend
}