package com.fcar.be.modules.finance.dto.request;

import com.fcar.be.modules.finance.enums.PaymentType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PaymentProcessReq {
    @NotBlank String contractNo;
    @NotNull @Positive BigDecimal amount;
    @NotNull PaymentType paymentType;
}