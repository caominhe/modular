package com.fcar.be.modules.sales.service.impl;

import java.math.BigDecimal;
import java.util.UUID;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcar.be.core.exception.AppException;
import com.fcar.be.core.exception.ErrorCode;
import com.fcar.be.modules.inventory.dto.response.CarDetailRes;
import com.fcar.be.modules.inventory.service.CarService;
import com.fcar.be.modules.marketing.dto.response.VoucherRes;
import com.fcar.be.modules.marketing.enums.DiscountType;
import com.fcar.be.modules.marketing.service.MarketingService;
import com.fcar.be.modules.sales.dto.request.QuotationCreateReq;
import com.fcar.be.modules.sales.dto.response.ContractRes;
import com.fcar.be.modules.sales.dto.response.QuotationRes;
import com.fcar.be.modules.sales.entity.Contract;
import com.fcar.be.modules.sales.entity.Quotation;
import com.fcar.be.modules.sales.enums.ContractStatus;
import com.fcar.be.modules.sales.enums.QuotationStatus;
import com.fcar.be.modules.sales.mapper.SalesMapper;
import com.fcar.be.modules.sales.repository.ContractRepository;
import com.fcar.be.modules.sales.repository.QuotationRepository;
import com.fcar.be.modules.sales.service.SalesService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class SalesServiceImpl implements SalesService {

    private final QuotationRepository quotationRepository;
    private final ContractRepository contractRepository;
    private final SalesMapper salesMapper;

    // Tiêm (Inject) Service từ các Module khác vào
    private final CarService carService;
    private final MarketingService marketingService;

    @Override
    @Transactional
    public QuotationRes createQuotation(QuotationCreateReq request, Long customerUserId) {
        // 1. Gọi Inventory lấy giá xe gốc
        CarDetailRes car = carService.getCarByVin(request.getCarVin());
        BigDecimal totalAmount = car.getBasePrice();
        BigDecimal finalAmount = totalAmount;

        // 2. Nếu có Voucher, gọi Marketing để kiểm tra, đổi trạng thái và áp dụng giảm giá
        if (request.getVoucherCode() != null && !request.getVoucherCode().isBlank()) {
            VoucherRes voucher = marketingService.useVoucher(request.getVoucherCode(), customerUserId);

            if (voucher.getDiscountType() == DiscountType.PERCENT) {
                // Tính: 1 tỷ - (1 tỷ * 5 / 100)
                BigDecimal discountAmt =
                        totalAmount.multiply(voucher.getDiscountValue()).divide(BigDecimal.valueOf(100));
                finalAmount = totalAmount.subtract(discountAmt);
            } else {
                // Trừ thẳng tiền mặt
                finalAmount = totalAmount.subtract(voucher.getDiscountValue());
            }
        }

        // 3. Lưu báo giá
        Quotation quotation = Quotation.builder()
                .leadId(request.getLeadId())
                .carVin(request.getCarVin())
                .voucherCode(request.getVoucherCode())
                .totalAmount(totalAmount)
                .finalAmount(finalAmount)
                .status(QuotationStatus.DRAFT)
                .build();

        return salesMapper.toQuotationRes(quotationRepository.save(quotation));
    }

    @Override
    @Transactional
    public ContractRes createContract(Long quotationId, Long salesId) {
        // Kiểm tra xem báo giá đã có hợp đồng chưa (quan hệ 1-1)
        if (contractRepository.existsByQuotationId(quotationId)) {
            throw new AppException(ErrorCode.CONTRACT_EXISTED);
        }

        Quotation quotation = quotationRepository
                .findById(quotationId)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));

        if (quotation.getStatus() != QuotationStatus.ACCEPTED) {
            throw new AppException(ErrorCode.QUOTATION_NOT_ACCEPTED);
        }

        // Sinh số hợp đồng ngẫu nhiên (VD: HD-8F3A9)
        String contractNo = "HD-" + UUID.randomUUID().toString().substring(0, 5).toUpperCase();

        Contract contract = Contract.builder()
                .contractNo(contractNo)
                .quotationId(quotationId)
                .salesId(salesId)
                .status(ContractStatus.PENDING)
                .build();

        return salesMapper.toContractRes(contractRepository.save(contract));
    }

    @Override
    @Transactional
    public QuotationRes acceptQuotation(Long quotationId) {
        Quotation quotation = quotationRepository
                .findById(quotationId)
                .orElseThrow(() -> new AppException(ErrorCode.QUOTATION_NOT_FOUND));
        quotation.setStatus(QuotationStatus.ACCEPTED);
        return salesMapper.toQuotationRes(quotationRepository.save(quotation));
    }
}
