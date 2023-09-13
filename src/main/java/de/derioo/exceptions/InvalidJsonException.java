package de.derioo.exceptions;

public class InvalidJsonException extends RuntimeException {


    private String json;

    public InvalidJsonException(String s) {
        super(s);
        this.json = s;
    }

    public String getS() {
        return json;
    }
}
