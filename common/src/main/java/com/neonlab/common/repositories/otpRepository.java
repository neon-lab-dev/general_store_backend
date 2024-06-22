package com.neonlab.common.repositories;

import com.neonlab.common.entities.Otp;
import com.neonlab.common.enums.AuthStatus;
import com.neonlab.common.enums.VerificationPurpose;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.Optional;

@Repository
public interface otpRepository extends JpaRepository<Otp, String> {

    Optional<Otp> findFirstByCommunicatedToAndStatusOrderByCreatedAtDesc(String communicatedTo, AuthStatus status);

}
