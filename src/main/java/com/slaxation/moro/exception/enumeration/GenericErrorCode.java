package com.slaxation.moro.exception.enumeration;

public enum GenericErrorCode {
    NOT_FOUND_ERROR("NOT_FOUND_ERROR"),
    FORBIDDEN_ERROR("FORBIDDEN_ERROR");

    private final String value;

    GenericErrorCode(String value){
        this.value = value;
    }

    @Override
    public String toString(){return value;}
}
