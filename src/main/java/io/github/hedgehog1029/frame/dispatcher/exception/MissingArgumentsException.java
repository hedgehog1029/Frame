package io.github.hedgehog1029.frame.dispatcher.exception;

import java.lang.reflect.Parameter;

public class MissingArgumentsException extends DispatcherException {
	public MissingArgumentsException(Parameter missing) {
		super(String.format("Parameter %s is non-optional, but no argument was provided.", missing.getName()));
	}
}
