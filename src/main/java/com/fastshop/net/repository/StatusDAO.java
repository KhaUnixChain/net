package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Status;

@Repository
public interface StatusDAO extends JpaRepository<Status, Integer>{
    
}
