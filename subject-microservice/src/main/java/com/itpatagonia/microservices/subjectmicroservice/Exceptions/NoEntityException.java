package com.itpatagonia.microservices.subjectmicroservice.Exceptions;

public class NoEntityException extends Exception{
    public NoEntityException(String message){
        super(message);
    }
}
