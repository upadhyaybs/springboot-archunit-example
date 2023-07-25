package com.tp.springboot.archunit.exception;

public class CustomerNotFoundException extends RuntimeException{

    private static final long serialVersionUID = 6475937716669414897L;
	StringBuffer message= new StringBuffer("Customer not found for ");

    public CustomerNotFoundException(String field , String value ){
        message.append(field)
                .append("{")
                .append(value)
                .append("}");
    }

    public String getMessage(){
        return message.toString();
    }

}
