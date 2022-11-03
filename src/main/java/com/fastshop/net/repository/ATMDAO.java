package com.fastshop.net.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.ATM;
import com.fastshop.net.model.Account;

@Repository
public interface ATMDAO extends JpaRepository<ATM, Integer> {
    ATM findByAccount(Account account);
    ATM findByNumber(String number);
}
