package com.fcar.be.modules.aftersales.repository;

import com.fcar.be.modules.aftersales.entity.WarrantyBook;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface WarrantyBookRepository extends JpaRepository<WarrantyBook, Long> {
    Optional<WarrantyBook> findByCarVin(String carVin);
    Optional<WarrantyBook> findByLicensePlate(String licensePlate);
}