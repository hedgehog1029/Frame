package io.github.hedgehog1029.frame.dispatcher.provider;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 *
 */
public interface Provider<T> {
	boolean willConsume(ParameterWrapper param);
	T provide(ICommandArguments args, ParameterWrapper param) throws DispatcherException;

	List<String> getSuggestions(int index, String partial, Namespace namespace);
	int argsWanted(ParameterWrapper param);
}
