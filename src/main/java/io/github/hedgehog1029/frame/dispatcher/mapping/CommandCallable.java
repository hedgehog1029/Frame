package io.github.hedgehog1029.frame.dispatcher.mapping;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandCallable {
	private final Command metadata;
	private final MethodWrapper wrappedMethod;

	public CommandCallable(Command metadata, MethodWrapper wrappedMethod) {
		this.metadata = metadata;
		this.wrappedMethod = wrappedMethod;
	}

	public void invoke(Object[] arguments) throws DispatcherException {
		this.wrappedMethod.invoke(arguments);
	}
}
