package com.baobab.bookstore.order_service.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author AmuDaDev
 * @created 12/09/2024
 */
@Service
@Transactional
public class SecurityServiceImpl implements SecurityService {
    @Override
    public String getLoginUserName() {
        return "Amu";
    }
}
