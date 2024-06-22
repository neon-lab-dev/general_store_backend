package com.neonlab.common.repositories;

import com.neonlab.common.entities.AuthUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface AuthUserRepository extends JpaRepository<AuthUser, String> {

    Optional<AuthUser> findByUserId(String userId);

    @Query("select au from AuthUser au where au.active = true and au.userName = :username")
    Optional<AuthUser> findActiveByUsernameAndDeviceId(
            @Param("username") String username
    );

    @Query("select au from AuthUser au where au.active = true and au.token = :token")
    Optional<AuthUser> findActiveByToken(
            @Param("token") String token
    );

    @Query("select au from AuthUser au where au.active = false and au.userName = :username")
    Optional<AuthUser> findInactiveByUsername(
            @Param("username") String username
    );

    Long countByActive(boolean active);

}
