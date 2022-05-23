package com.supershaun.bikeshop.repositories;

import com.supershaun.bikeshop.models.Role;
import com.supershaun.bikeshop.models.enums.ERole;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(ERole name);
}
