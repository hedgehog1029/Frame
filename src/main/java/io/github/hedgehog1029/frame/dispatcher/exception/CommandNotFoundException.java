package io.github.hedgehog1029.frame.dispatcher.exception;

public class CommandNotFoundException extends DispatcherException {
	public CommandNotFoundException(String key) {
		super("Command not found: " + key);
	}
}
