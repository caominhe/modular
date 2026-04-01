package com.fcar.be.modules.marketing.controller;

import com.fcar.be.core.common.dto.ApiResponse;
import com.fcar.be.core.common.util.SecurityUtils;
import com.fcar.be.core.exception.AppException;
import com.fcar.be.core.exception.ErrorCode;
import com.fcar.be.modules.marketing.dto.response.VoucherRes;
import com.fcar.be.modules.marketing.service.MarketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/vouchers")
@RequiredArgsConstructor
public class VoucherController {
    private final MarketingService marketingService;

    // Khách hàng tự thu thập mã giảm giá (Claim)
    @PostMapping("/{code}/claim")
    public ApiResponse<VoucherRes> claimVoucher(@PathVariable String code) {
        // Lấy ID của user đang đăng nhập (Tạm parse String sang Long, thực tế cần check kỹ hơn)
        String userIdStr = SecurityUtils.getCurrentUserLogin()
                .orElseThrow(() -> new AppException(ErrorCode.UNAUTHENTICATED));
        Long userId = Long.parseLong(userIdStr); // Giả định SecurityContext đang lưu ID

        return ApiResponse.<VoucherRes>builder()
                .result(marketingService.claimVoucher(code, userId))
                .build();
    }
}