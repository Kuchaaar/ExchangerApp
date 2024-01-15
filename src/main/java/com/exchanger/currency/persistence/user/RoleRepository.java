package com.exchanger.currency.persistence.user;

import com.exchanger.currency.models.ERole;
import com.exchanger.currency.models.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
