package io.github.hedgehog1029.frame.dispatcher.exception;

import java.lang.reflect.Parameter;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class NotEnoughArgumentsException extends DispatcherException {
	private String[] arguments;
	private Parameter[] parameters;

	public NotEnoughArgumentsException(String[] arguments, Parameter[] parameters) {
		this.arguments = arguments;
		this.parameters = parameters;
	}

	public String[] getArguments() {
		return arguments;
	}

	public Parameter[] getParameters() {
		return parameters;
	}
}
