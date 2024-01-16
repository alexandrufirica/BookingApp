package com.BookingApp.Data.Repository;

import com.BookingApp.Data.Entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional <Role>  findByName(String name);

    Boolean existsByName(String name);
}
