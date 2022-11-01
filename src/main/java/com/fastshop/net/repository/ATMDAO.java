package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.ATM;

@Repository
public interface ATMDAO extends JpaRepository<ATM, Integer> {
    ATM findByUsername(String username);
    ATM findByNumber(String number);
}
