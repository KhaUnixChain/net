package com.fastshop.net.service;

import java.util.List;

public interface AddressService {
    List<String> findAllAddressByAccount(String username);
    String findByAccountWithChooseIsTrue(String username);
}
