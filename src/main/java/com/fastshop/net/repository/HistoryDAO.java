package com.fastshop.net.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.History;

@Repository
public interface HistoryDAO extends JpaRepository<History, Long>{
    List<History> findByTitle(String title);
}
