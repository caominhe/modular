package com.fcar.be.modules.finance.repository;

import com.fcar.be.modules.finance.entity.Handover;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HandoverRepository extends JpaRepository<Handover, Long> {
    Optional<Handover> findByContractNo(String contractNo);
    boolean existsByLicensePlate(String licensePlate);
}