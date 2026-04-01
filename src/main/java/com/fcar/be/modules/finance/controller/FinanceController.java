package com.fcar.be.modules.finance.controller;

import com.fcar.be.core.common.dto.ApiResponse;
import com.fcar.be.modules.finance.dto.request.HandoverUpdateReq;
import com.fcar.be.modules.finance.dto.request.PaymentProcessReq;
import com.fcar.be.modules.finance.dto.response.HandoverRes;
import com.fcar.be.modules.finance.dto.response.PaymentRes;
import com.fcar.be.modules.finance.service.FinanceService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/finance")
@RequiredArgsConstructor
public class FinanceController {

    private final FinanceService financeService;

    @PostMapping("/payments")
    public ApiResponse<PaymentRes> processPayment(@RequestBody @Valid PaymentProcessReq request) {
        return ApiResponse.<PaymentRes>builder()
                .result(financeService.processPayment(request))
                .build();
    }

    @GetMapping("/contracts/{contractNo}/payments")
    public ApiResponse<List<PaymentRes>> getPaymentsByContract(@PathVariable String contractNo) {
        return ApiResponse.<List<PaymentRes>>builder()
                .result(financeService.getPaymentsByContract(contractNo))
                .build();
    }

    @PostMapping("/contracts/{contractNo}/handover")
    public ApiResponse<HandoverRes> initHandover(@PathVariable String contractNo) {
        return ApiResponse.<HandoverRes>builder()
                .result(financeService.initHandover(contractNo))
                .build();
    }

    @PutMapping("/contracts/{contractNo}/handover")
    public ApiResponse<HandoverRes> updateHandover(@PathVariable String contractNo,
                                                   @RequestBody @Valid HandoverUpdateReq request) {
        return ApiResponse.<HandoverRes>builder()
                .result(financeService.updateHandoverInfo(contractNo, request))
                .build();
    }
}