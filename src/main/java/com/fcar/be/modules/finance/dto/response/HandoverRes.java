package com.fcar.be.modules.finance.dto.response;

import com.fcar.be.modules.finance.enums.HandoverStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HandoverRes {
    Long id;
    String contractNo;
    String licensePlate;
    LocalDateTime handoverDate;
    HandoverStatus status;
}