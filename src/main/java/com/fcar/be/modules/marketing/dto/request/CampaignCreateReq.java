package com.fcar.be.modules.marketing.dto.request;

import java.math.BigDecimal;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fcar.be.modules.marketing.enums.DiscountType;

import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampaignCreateReq {
    @NotBlank
    String name;

    @NotNull
    DiscountType discountType;

    @NotNull
    BigDecimal discountValue;
}
