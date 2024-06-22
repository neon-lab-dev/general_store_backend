package com.neonlab.common.entities;

import com.neonlab.common.enums.PaymentStatus;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@EqualsAndHashCode(callSuper = true)
@Entity
@NoArgsConstructor
@Table(name = "payment" ,indexes = {
        @Index(name = "idx_externalId",columnList = "externalId")
})
public class
Payment extends Generic {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "amount", nullable = false)
    private BigDecimal amount;

    @Column(name = "description")
    private String description;

    @Column(name = "service_provider", nullable = false)
    private Integer serviceProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "status", nullable = false)
    private PaymentStatus status;
    @Column(name = "external_id")
    private String externalId;

    @Column(name = "additional_info", columnDefinition = "json")
    private String additionalInfo;

}
