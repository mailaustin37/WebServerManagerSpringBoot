package com.influence.webservermanagement.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message){
            super(message);
    }
}