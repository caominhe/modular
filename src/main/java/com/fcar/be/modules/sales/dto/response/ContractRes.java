package com.fcar.be.modules.sales.dto.response;

import com.fcar.be.modules.sales.enums.ContractStatus;
import lombok.*;
import lombok.experimental.FieldDefaults;
import java.time.LocalDateTime;

@Data
@Builder
@FieldDefaults(level = AccessLevel.PRIVATE)
public class ContractRes {
    String contractNo;
    Long quotationId;
    Long salesId;
    LocalDateTime signedDate;
    ContractStatus status;
}