package com.fcar.be.modules.marketing.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fcar.be.modules.marketing.entity.Voucher;

@Repository
public interface VoucherRepository extends JpaRepository<Voucher, String> {
    boolean existsByCode(String code);

    Optional<Voucher> findByCodeAndUserId(String code, Long userId);
}
