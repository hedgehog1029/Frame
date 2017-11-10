package io.github.hedgehog1029.frame.dispatcher.arguments;

import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.ArrayDeque;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandArgumentsDeque implements ICommandArguments {
	private final ArrayDeque<String> arguments;
	private final Namespace namespace;

	public CommandArgumentsDeque(List<String> arguments, Namespace namespace) {
		this.arguments = new ArrayDeque<>(arguments);
		this.namespace = namespace;
	}

	@Override
	public boolean hasNext() {
		return !this.arguments.isEmpty();
	}

	@Override
	public String next() throws MissingArgumentsException {
		return arguments.poll();
	}

	@Override
	public String peek() {
		return arguments.peek();
	}

	@Override
	public Namespace getNamespace() {
		return this.namespace;
	}
}
