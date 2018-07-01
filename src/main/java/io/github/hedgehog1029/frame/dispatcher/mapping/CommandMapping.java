package io.github.hedgehog1029.frame.dispatcher.mapping;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import io.github.hedgehog1029.frame.dispatcher.bindings.TypeToken;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.lang.reflect.Parameter;
import java.util.Collections;
import java.util.Deque;
import java.util.List;

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

	@Override
	public String[] getAliases() {
		return this.commandMetadata.aliases();
	}

	@Override
	public String getPrimaryAlias() {
		return this.commandMetadata.aliases()[0];
	}

	@Override
	public String getDescription() {
		return this.commandMetadata.description();
	}

	@Override
	public String getUsage() {
		return this.wrappedMethod.getUsage();
	}

	@Override
	public String getPermission() {
		return this.commandMetadata.permission();
	}

	public MethodWrapper getWrappedMethod() {
		return wrappedMethod;
	}

	@Override
	public void call(Deque<String> arguments, Namespace namespace) throws DispatcherException {
		this.dispatcher.dispatch(this, arguments.toArray(new String[0]), namespace);
	}

	@Override
	public List<String> getCompletions(List<String> current) {
		int param = current.size() - 1;

		List<Parameter> parameters = this.wrappedMethod.getRequiredParameters();
		if (param >= parameters.size()) return Collections.emptyList();

		Provider<?> provider = this.dispatcher.getTransformer().getProvider(
				TypeToken.get(parameters.get(param).getType())
		);
		return provider.getSuggestions(current.get(param));
	}
}
