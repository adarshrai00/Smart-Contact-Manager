package com.scm.Exceptions;

public class ResourceNotFoundException extends RuntimeException {
    public ResourceNotFoundException( String e ) {
        super(e);
    }
}
