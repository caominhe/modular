package com.fcar.be.modules.finance.service;

import com.fcar.be.modules.finance.dto.request.HandoverUpdateReq;
import com.fcar.be.modules.finance.dto.request.PaymentProcessReq;
import com.fcar.be.modules.finance.dto.response.HandoverRes;
import com.fcar.be.modules.finance.dto.response.PaymentRes;

import java.util.List;

public interface FinanceService {
    PaymentRes processPayment(PaymentProcessReq request);
    List<PaymentRes> getPaymentsByContract(String contractNo);
    HandoverRes initHandover(String contractNo);
    HandoverRes updateHandoverInfo(String contractNo, HandoverUpdateReq request);
}