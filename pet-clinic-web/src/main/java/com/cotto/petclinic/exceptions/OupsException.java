package com.cotto.petclinic.exceptions;

public class OupsException extends RuntimeException{

    public OupsException(String message){
        super(message);
    }
}
