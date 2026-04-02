package com.fcar.be.modules.crm.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fcar.be.core.exception.AppException;
import com.fcar.be.core.exception.ErrorCode;
import com.fcar.be.modules.crm.dto.request.LeadCreateReq;
import com.fcar.be.modules.crm.dto.response.LeadRes;
import com.fcar.be.modules.crm.entity.Lead;
import com.fcar.be.modules.crm.enums.LeadStatus;
import com.fcar.be.modules.crm.mapper.CrmMapper;
import com.fcar.be.modules.crm.repository.LeadRepository;
import com.fcar.be.modules.crm.service.LeadService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class LeadServiceImpl implements LeadService {

    private final LeadRepository leadRepository;
    private final CrmMapper crmMapper;

    @Override
    @Transactional
    public LeadRes createLead(LeadCreateReq request) {
        Lead lead = crmMapper.toLead(request);
        lead.setStatus(LeadStatus.NEW);
        return crmMapper.toLeadRes(leadRepository.save(lead));
    }

    @Override
    @Transactional
    public LeadRes assignSales(Long leadId, Long salesId) {
        Lead lead = leadRepository.findById(leadId).orElseThrow(() -> new AppException(ErrorCode.LEAD_NOT_FOUND));

        lead.setAssignedSalesId(salesId);
        lead.setStatus(LeadStatus.CONTACTING);

        return crmMapper.toLeadRes(leadRepository.save(lead));
    }

    @Override
    public List<LeadRes> getLeadsBySales(Long salesId) {
        return leadRepository.findByAssignedSalesId(salesId).stream()
                .map(crmMapper::toLeadRes)
                .toList();
    }
}
