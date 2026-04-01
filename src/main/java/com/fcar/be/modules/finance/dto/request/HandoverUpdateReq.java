package com.fcar.be.modules.finance.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class HandoverUpdateReq {
    @NotBlank String licensePlate;
    LocalDateTime handoverDate; // Có thể null nếu chỉ mới bấm biển chứ chưa giao
}