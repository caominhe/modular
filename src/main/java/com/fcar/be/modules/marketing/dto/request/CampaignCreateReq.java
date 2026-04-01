package com.fcar.be.modules.marketing.dto.request;

import com.fcar.be.modules.marketing.enums.DiscountType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.math.BigDecimal;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class CampaignCreateReq {
    @NotBlank String name;
    @NotNull DiscountType discountType;
    @NotNull BigDecimal discountValue;
}