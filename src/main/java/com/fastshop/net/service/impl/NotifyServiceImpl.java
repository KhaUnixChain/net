package com.fastshop.net.service.impl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;
import com.fastshop.net.repository.NotifyDAO;
import com.fastshop.net.service.NotifyService;

@Service
public class NotifyServiceImpl implements NotifyService {
    @Autowired
    NotifyDAO notifyDAO;

    @Override
    public List<Notify> findAllByAccAndNowAndStatusOrderBy(Account account) {
        try {
            Date date = java.sql.Date.valueOf(LocalDate.now());
            return notifyDAO.findAllByAccAndNowAndStatusOrderBy(account, date);
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public void save(Notify notify) {
        notifyDAO.save(notify);
    }

    @Override
    public void deleteById(Long id) {
        notifyDAO.deleteById(id);
    }

    @Override
    public void delete(Notify notify) {
        notifyDAO.delete(notify);
    }
}
