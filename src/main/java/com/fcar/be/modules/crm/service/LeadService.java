package com.fcar.be.modules.crm.service;

import com.fcar.be.modules.crm.dto.request.LeadCreateReq;
import com.fcar.be.modules.crm.dto.response.LeadRes;

import java.util.List;

public interface LeadService {
    LeadRes createLead(LeadCreateReq request);

    LeadRes assignSales(Long leadId, Long salesId);

    List<LeadRes> getLeadsBySales(Long salesId);
}