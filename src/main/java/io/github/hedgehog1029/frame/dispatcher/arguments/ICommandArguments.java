package io.github.hedgehog1029.frame.dispatcher.arguments;

import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.util.Namespace;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public interface ICommandArguments {
	boolean hasNext();
	Argument next() throws MissingArgumentsException;
	Namespace getNamespace();
}
