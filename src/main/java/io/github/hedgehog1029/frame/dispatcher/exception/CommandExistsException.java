package io.github.hedgehog1029.frame.dispatcher.exception;

public class CommandExistsException extends Exception {
    String command;

    public CommandExistsException(String command) {
        this.command = command;
    }
}
