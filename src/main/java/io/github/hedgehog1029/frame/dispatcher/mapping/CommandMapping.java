package io.github.hedgehog1029.frame.dispatcher.mapping;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import io.github.hedgehog1029.frame.dispatcher.bindings.BoundMethod;
import io.github.hedgehog1029.frame.dispatcher.bindings.ParamWithIndex;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ExecutionPlan;
import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.Collections;
import java.util.Deque;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandMapping implements IPipeline {
	private final Command commandMetadata;
	private final BoundMethod boundMethod;
	private final Object module;

	public CommandMapping(CommandDispatcher dispatcher, Command commandMetadata, MethodWrapper wrappedMethod) {
		this.commandMetadata = commandMetadata;

		this.boundMethod = dispatcher.getTransformer().prepare(wrappedMethod);
		this.module = wrappedMethod.getParent();
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
		return this.boundMethod.getUsage();
	}

	@Override
	public String getPermission() {
		return this.commandMetadata.permission();
	}

	public BoundMethod getBoundMethod() {
		return boundMethod;
	}

	@Override
	public void call(Deque<String> arguments, Namespace namespace) throws DispatcherException {
		this.boundMethod.invoke(arguments, namespace);
	}

	@Override
	public List<String> getCompletions(List<String> current, Namespace namespace) {
		int param = current.size() - 1;
		List<ParamWithIndex> paramMap = this.boundMethod.mapParametersToArity();

		if (param >= paramMap.size()) return Collections.emptyList();

		ParamWithIndex pwi = paramMap.get(param);
		return pwi.parameter.getProvider().getSuggestions(pwi.index, current.get(param), namespace);
	}

	@Override
	public List<ExecutionPlan> getExecutionPlans() {
		return this.boundMethod.getExecutionPlans();
	}

	@Override
	public Class<?> getContainingClass() {
		return module.getClass();
	}
}
