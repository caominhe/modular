package com.fcar.be.modules.aftersales.repository;

import com.fcar.be.modules.aftersales.entity.ServiceTicket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ServiceTicketRepository extends JpaRepository<ServiceTicket, Long> {
    List<ServiceTicket> findByWarrantyIdOrderByServiceDateDesc(Long warrantyId);
}