package com.avarna.customer.exception;

public class CustomerNotFoundException extends RuntimeException {

    private static final long serialVersionUID = 6475937716669414897L;

    public CustomerNotFoundException(String field, String value) {
        super("Customer not found for " + field + "{" + value + "}");
    }

}
