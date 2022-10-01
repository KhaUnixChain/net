package com.fastshop.net.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.fastshop.net.model.Address;

@Repository
public interface AddressDAO extends JpaRepository<Address, Long>{
    @Query("SELECT o.place FROM Address o WHERE o.username = ?1")
    List<String> findAllAddressByAccount(String username);

    @Query("SELECT o.place FROM Address o WHERE o.username = ?1 AND o.choose = 1")
    Optional<String> findByAccountWithChooseIsTrue(String username);
}
