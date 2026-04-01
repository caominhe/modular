package com.fcar.be.modules.crm.entity;

import com.fcar.be.modules.crm.enums.LeadSource;
import com.fcar.be.modules.crm.enums.LeadStatus;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "leads")
@EntityListeners(AuditingEntityListener.class)
public class Lead {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(name = "user_id")
    Long userId; // ID khách hàng (nếu đã có tài khoản, link sang module Identity)

    @Column(name = "full_name", nullable = false)
    String fullName;

    @Column(nullable = false)
    String phone;

    @Enumerated(EnumType.STRING)
    LeadSource source;

    @Column(name = "assigned_sales_id")
    Long assignedSalesId; // ID nhân viên Sales phụ trách

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    @Builder.Default
    LeadStatus status = LeadStatus.NEW;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;
}