package com.fcar.be.modules.inventory.repository;

import com.fcar.be.modules.inventory.entity.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CarRepository extends JpaRepository<Car, String> {
    boolean existsByEngineNumber(String engineNumber);
}