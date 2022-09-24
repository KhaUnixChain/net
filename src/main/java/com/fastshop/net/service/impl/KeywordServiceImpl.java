package com.fastshop.net.service.impl;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Keyword;
import com.fastshop.net.repository.KeywordDAO;
import com.fastshop.net.service.KeywordService;

@Service
public class KeywordServiceImpl implements KeywordService{
    @Autowired
    KeywordDAO keywordDAO;

    @Override
    public List<Keyword> findAll() {
        return keywordDAO.findAll();
    }

    @Override
    public Set<String> getSetKeyWord(Account account) {
        return account.getKeywords()
                      .stream()
                      .map(item -> item.getKeysearch())
                      .collect(Collectors.toSet());
    }

    @Override
    public void save(Keyword keyword) {
        keywordDAO.save(keyword);
    }

    @Override
    public void deleteById(int id) {
        keywordDAO.deleteById(id);
    }

    @Override
    public Keyword edit(int id) {
        return keywordDAO.findById(id).get();
    }

    @Override
    public List<Keyword> findByAccount(Account account) {
        return keywordDAO.findByAccount(account);
    }

    @Override
    public Keyword findById(int id) {
        return keywordDAO.findById(id).get();
    }
}
