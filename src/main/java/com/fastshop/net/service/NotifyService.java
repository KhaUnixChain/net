package com.fastshop.net.service;

import java.util.List;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;

public interface NotifyService {
    List<Notify> findAllByAccAndNowAndStatusOrderBy(Account account);
    void save(Notify notify);
    void deleteById(Long id);
    void delete(Notify notify);
}
