package com.fcar.be.modules.crm.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TestDriveReq {
    @NotNull Long leadId;
    @NotNull Long carModelId;
    @NotNull LocalDateTime scheduleTime;
}