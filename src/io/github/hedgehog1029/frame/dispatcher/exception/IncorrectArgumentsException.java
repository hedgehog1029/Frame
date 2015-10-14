package io.github.hedgehog1029.frame.dispatcher.exception;

public class IncorrectArgumentsException extends Exception {

    private String message;

    public IncorrectArgumentsException(String msg) {
        this.message = msg;
    }

    public String getReason() {
        return this.message;
    }
}
