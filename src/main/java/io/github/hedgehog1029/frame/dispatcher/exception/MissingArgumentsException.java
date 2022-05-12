package io.github.hedgehog1029.frame.dispatcher.exception;

import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;

public class MissingArgumentsException extends DispatcherException {
	public MissingArgumentsException(ParameterWrapper missing) {
		super(String.format("Parameter %s is non-optional, but no argument was provided.", missing.getName()));
	}
}
