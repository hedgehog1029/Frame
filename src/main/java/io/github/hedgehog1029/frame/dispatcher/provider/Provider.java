package io.github.hedgehog1029.frame.dispatcher.provider;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;

import java.lang.reflect.Parameter;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 *
 */
public interface Provider<T> {
	T provide(ICommandArguments args, Parameter param) throws DispatcherException;
	List<String> getSuggestions(String partial);
}
