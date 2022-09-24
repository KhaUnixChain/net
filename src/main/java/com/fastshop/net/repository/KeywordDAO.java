package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Keyword;

@Repository
public interface KeywordDAO extends JpaRepository<Keyword, Integer>{
    List<Keyword> findByAccount(Account account);
}
