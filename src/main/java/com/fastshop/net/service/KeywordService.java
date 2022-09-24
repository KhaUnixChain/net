package com.fastshop.net.service;

import java.util.List;
import java.util.Set;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Keyword;

public interface KeywordService {
    void save(Keyword keyword);
    void deleteById(int id);
    Keyword edit(int id);
    Keyword findById(int id);
    List<Keyword> findAll();
    List<Keyword> findByAccount(Account account);
    Set<String> getSetKeyWord(Account account);
}
