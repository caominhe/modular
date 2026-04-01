package com.fcar.be.modules.inventory.repository;

import com.fcar.be.modules.inventory.entity.MasterData;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MasterDataRepository extends JpaRepository<MasterData, Long> {
}