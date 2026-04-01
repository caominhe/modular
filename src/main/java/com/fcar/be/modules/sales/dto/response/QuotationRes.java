package com.fcar.be.modules.sales.dto.response;

import com.fcar.be.modules.sales.enums.QuotationStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class QuotationRes {
    Long id;
    Long leadId;
    String carVin;
    String voucherCode;
    BigDecimal totalAmount;
    BigDecimal finalAmount;
    QuotationStatus status;
    LocalDateTime createdAt;
}