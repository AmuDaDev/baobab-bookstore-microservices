package com.baobab.bookstore.order_service.exception;

/**
 * @author AmuDaDev
 * @created 03/10/2024
 */
public class InvalidOrderException extends RuntimeException {

    public InvalidOrderException(String message) {
        super(message);
    }
}
