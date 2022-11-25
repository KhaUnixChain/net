package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Account;
import com.fastshop.net.model.Notify;

@Repository
public interface NotifyDAO extends JpaRepository<Notify, Long>{
    @Query("SELECT o FROM Notify o WHERE o.account = ?1 AND o.sentDate = GETDATE() AND o.status = true ORDER BY o.sentDate DESC")
    List<Notify> findAllByAccAndNowAndStatusOrderBy(Account account);
}
