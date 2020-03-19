package io.github.hedgehog1029.frame.dispatcher.arguments;

import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandArgumentsDeque implements ICommandArguments {
	private final Deque<String> arguments;
	private final Namespace namespace;

	public CommandArgumentsDeque(Deque<String> arguments, Namespace namespace) {
		this.arguments = arguments;
		this.namespace = namespace;
	}

	public CommandArgumentsDeque(List<String> arguments, Namespace namespace) {
		this(new ArrayDeque<>(arguments), namespace);
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
