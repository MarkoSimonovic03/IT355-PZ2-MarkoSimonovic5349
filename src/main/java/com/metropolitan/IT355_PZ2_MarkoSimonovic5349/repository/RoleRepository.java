package com.metropolitan.IT355_PZ2_MarkoSimonovic5349.repository;

import com.metropolitan.IT355_PZ2_MarkoSimonovic5349.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
    Optional<Role> findByName(String name);
}
