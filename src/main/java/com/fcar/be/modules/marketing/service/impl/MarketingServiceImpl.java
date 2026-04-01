package com.fcar.be.modules.marketing.service.impl;

import com.fcar.be.core.exception.AppException;
import com.fcar.be.core.exception.ErrorCode;
import com.fcar.be.modules.marketing.dto.request.CampaignCreateReq;
import com.fcar.be.modules.marketing.dto.response.VoucherRes;
import com.fcar.be.modules.marketing.entity.Campaign;
import com.fcar.be.modules.marketing.entity.Voucher;
import com.fcar.be.modules.marketing.enums.VoucherStatus;
import com.fcar.be.modules.marketing.mapper.MarketingMapper;
import com.fcar.be.modules.marketing.repository.CampaignRepository;
import com.fcar.be.modules.marketing.repository.VoucherRepository;
import com.fcar.be.modules.marketing.service.MarketingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class MarketingServiceImpl implements MarketingService {
    private final CampaignRepository campaignRepository;
    private final VoucherRepository voucherRepository;
    private final MarketingMapper marketingMapper;

    @Override
    public Campaign createCampaign(CampaignCreateReq request) {
        return campaignRepository.save(marketingMapper.toCampaign(request));
    }

    @Override
    @Transactional
    public List<VoucherRes> generateVouchers(Long campaignId, int quantity, String prefix, LocalDateTime expiredAt) {
        Campaign campaign = campaignRepository.findById(campaignId)
                .orElseThrow(() -> new AppException(ErrorCode.CAMPAIGN_NOT_FOUND));

        List<Voucher> newVouchers = new ArrayList<>();
        for (int i = 0; i < quantity; i++) {
            // Sinh mã ngẫu nhiên: VD "VIP-8F3A9C"
            String code = (prefix != null ? prefix + "-" : "") + UUID.randomUUID().toString().substring(0, 6).toUpperCase();

            newVouchers.add(Voucher.builder()
                    .code(code)
                    .campaign(campaign)
                    .status(VoucherStatus.ACTIVE)
                    .expiredAt(expiredAt)
                    .build());
        }

        return voucherRepository.saveAll(newVouchers).stream()
                .map(marketingMapper::toVoucherRes).toList();
    }

    @Override
    @Transactional
    public VoucherRes claimVoucher(String code, Long userId) {
        Voucher voucher = voucherRepository.findById(code)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_FOUND));

        if (voucher.getStatus() != VoucherStatus.ACTIVE) {
            throw new AppException(ErrorCode.VOUCHER_INVALID_STATUS);
        }
        if (voucher.getExpiredAt().isBefore(LocalDateTime.now())) {
            voucher.setStatus(VoucherStatus.EXPIRED);
            voucherRepository.save(voucher);
            throw new AppException(ErrorCode.VOUCHER_EXPIRED);
        }

        voucher.setUserId(userId);
        voucher.setStatus(VoucherStatus.CLAIMED);
        return marketingMapper.toVoucherRes(voucherRepository.save(voucher));
    }

    @Override
    @Transactional
    public VoucherRes useVoucher(String code, Long userId) {
        Voucher voucher = voucherRepository.findByCodeAndUserId(code, userId)
                .orElseThrow(() -> new AppException(ErrorCode.VOUCHER_NOT_OWNED));

        if (voucher.getStatus() != VoucherStatus.CLAIMED) {
            throw new AppException(ErrorCode.VOUCHER_INVALID_STATUS);
        }
        if (voucher.getExpiredAt().isBefore(LocalDateTime.now())) {
            throw new AppException(ErrorCode.VOUCHER_EXPIRED);
        }

        voucher.setStatus(VoucherStatus.USED);
        return marketingMapper.toVoucherRes(voucherRepository.save(voucher));
    }
}