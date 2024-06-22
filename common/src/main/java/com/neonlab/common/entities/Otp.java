package com.neonlab.common.entities;

import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.enums.CommunicationMode;
import com.neonlab.common.enums.VerificationPurpose;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

@EqualsAndHashCode(callSuper = true)
@Entity
@Table(name = "otp", indexes = {
        @Index(name = "idx_communicated_to", columnList = "communicated_to"),
        @Index(name = "idx_status", columnList = "status")
})
@Data
@AllArgsConstructor
public class Otp extends Generic {

    public Otp(){
        super();
    }

    public Otp(String createdBy, String modifiedBy){
        super(createdBy, modifiedBy);
    }

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private String id;

    @Column(name = "communicated_to")
    private String communicatedTo;

    @Column(name = "otp")
    private String otp;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private AuthStatus status;

    @Column(name = "expiry_time")
    private Date expiryTime;


}
