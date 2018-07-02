package io.github.hedgehog1029.frame.dispatcher.exception;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandCallException extends DispatcherException {
	public CommandCallException(String message, Throwable parent) {
		super(message);
		initCause(parent);
	}

	public CommandCallException(Throwable parent) {
		this(parent.getMessage(), parent);
	}
}
