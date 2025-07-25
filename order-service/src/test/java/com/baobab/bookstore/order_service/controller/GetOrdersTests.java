package com.baobab.bookstore.order_service.controller;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.baobab.bookstore.order_service.AbstractIT;
import com.baobab.bookstore.order_service.WithMockOAuth2User;
import org.junit.jupiter.api.Test;

/**
 * @author AmuDaDev
 * @created 23/07/2025
 */
public class GetOrdersTests extends AbstractIT {
    @Test
    @WithMockOAuth2User(username = "user")
    void shouldGetOrdersSuccessfully() throws Exception {
        mockMvc.perform(get("/api/orders")).andExpect(status().isOk());
    }
}
