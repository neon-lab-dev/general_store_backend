package com.neonlab.common.repositories;

import com.neonlab.common.entities.SystemConfig;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SystemConfigRepository extends JpaRepository<SystemConfig, String> {

    SystemConfig findByKey(String key);
}
