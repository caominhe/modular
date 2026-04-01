package com.fcar.be.modules.aftersales.dto.response;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ServiceTicketRes {
    Long id;
    Long warrantyId;
    LocalDateTime serviceDate;
    String description;
    BigDecimal totalCost;
}