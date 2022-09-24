package com.fastshop.net.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Role;

@Repository
public interface RoleDAO extends JpaRepository<Role, String>{
    Optional<Role> findByName(String name);
}
