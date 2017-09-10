package io.github.hedgehog1029.frame.dispatcher.provider;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 *
 */
public interface Provider<T> {
	boolean consumesArguments();
	T provide(ICommandArguments args) throws DispatcherException;
}
