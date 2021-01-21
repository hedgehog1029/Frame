package io.github.hedgehog1029.frame.dispatcher.exception;

import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class NotEnoughArgumentsException extends DispatcherException {
	private final ParameterWrapper parameter;

	public NotEnoughArgumentsException(ParameterWrapper param) {
		super("Expected value for non-optional parameter " + param.getName());
		this.parameter = param;
	}

	public ParameterWrapper getParameter() {
		return parameter;
	}
}
