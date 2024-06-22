package com.neonlab.common.repositories;
import com.neonlab.common.entities.Address;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;
import java.util.List;



@Repository
public interface AddressRepository extends JpaRepository<Address, String>
        , JpaSpecificationExecutor<Address> {
    List<Address> findByUser_IdOrderByCreatedAtAsc(String userId);
}
