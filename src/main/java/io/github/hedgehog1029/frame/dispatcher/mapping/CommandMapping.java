package io.github.hedgehog1029.frame.dispatcher.mapping;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.Deque;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandMapping implements IPipeline {
	private final CommandDispatcher dispatcher;
	private final Command commandMetadata;
	private final MethodWrapper wrappedMethod;

	public CommandMapping(CommandDispatcher dispatcher, Command commandMetadata, MethodWrapper wrappedMethod) {
		this.dispatcher = dispatcher;
		this.commandMetadata = commandMetadata;
		this.wrappedMethod = wrappedMethod;
	}

	public String[] getAliases() {
		return this.commandMetadata.aliases();
	}

	public String getPrimaryAlias() {
		return this.commandMetadata.aliases()[0];
	}

	public String getDescription() {
		return this.commandMetadata.description();
	}

	public MethodWrapper getWrappedMethod() {
		return wrappedMethod;
	}

	@Override
	public void call(Deque<String> arguments, Namespace namespace) throws DispatcherException {
		this.dispatcher.dispatch(this, arguments.toArray(new String[0]), namespace);
	}
}
