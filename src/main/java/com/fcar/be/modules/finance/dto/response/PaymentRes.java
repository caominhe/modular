package com.fcar.be.modules.finance.dto.response;

import com.fcar.be.modules.finance.enums.PaymentStatus;
import com.fcar.be.modules.finance.enums.PaymentType;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentRes {
    Long id;
    String contractNo;
    BigDecimal amount;
    PaymentType paymentType;
    LocalDateTime paymentDate;
    PaymentStatus status;
}