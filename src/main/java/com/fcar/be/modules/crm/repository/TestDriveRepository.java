package com.fcar.be.modules.crm.repository;

import com.fcar.be.modules.crm.entity.TestDrive;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TestDriveRepository extends JpaRepository<TestDrive, Long> {
    List<TestDrive> findByLeadId(Long leadId);
}