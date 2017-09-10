package io.github.hedgehog1029.frame.dispatcher.exception;

public class CommandExistsException extends DispatcherException {
    String command;

    public CommandExistsException(String command) {
        this.command = command;
    }
}
