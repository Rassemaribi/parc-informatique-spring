package com.freedomofdev.parcinformatique.exception;

public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String resource, String field, Object value) {
        super(String.format("%s non trouvée %s: '%s'", resource, field, value));
    }
}
