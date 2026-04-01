package com.fcar.be.modules.sales.repository;

import com.fcar.be.modules.sales.entity.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ContractRepository extends JpaRepository<Contract, String> {
    boolean existsByQuotationId(Long quotationId);
}