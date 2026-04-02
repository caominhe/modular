package com.fcar.be.modules.crm.service;

import java.util.List;

import com.fcar.be.modules.crm.dto.request.LeadCreateReq;
import com.fcar.be.modules.crm.dto.response.LeadRes;

public interface LeadService {
    LeadRes createLead(LeadCreateReq request);

    LeadRes assignSales(Long leadId, Long salesId);

    List<LeadRes> getLeadsBySales(Long salesId);
}
