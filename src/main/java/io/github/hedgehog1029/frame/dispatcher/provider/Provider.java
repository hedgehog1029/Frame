package io.github.hedgehog1029.frame.dispatcher.provider;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;

import java.lang.reflect.Parameter;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 *
 */
public interface Provider<T> {
	boolean consumesArguments();
	T provide(ICommandArguments args, Parameter param) throws DispatcherException;
}
