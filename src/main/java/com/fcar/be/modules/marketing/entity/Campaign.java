package com.fcar.be.modules.marketing.entity;

import com.fcar.be.modules.marketing.enums.DiscountType;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
@Entity
@Table(name = "campaigns")
@EntityListeners(AuditingEntityListener.class)
public class Campaign {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;

    @Column(nullable = false)
    String name;

    @Enumerated(EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    DiscountType discountType;

    @Column(name = "discount_value", nullable = false)
    BigDecimal discountValue;

    @CreatedDate
    @Column(name = "created_at", updatable = false)
    LocalDateTime createdAt;
}