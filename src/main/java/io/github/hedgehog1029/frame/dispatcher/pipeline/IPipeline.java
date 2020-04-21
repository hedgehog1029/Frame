package io.github.hedgehog1029.frame.dispatcher.pipeline;

import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandMapping;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.Deque;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public interface IPipeline extends ICommandMapping {
	void call(Deque<String> arguments, Namespace namespace) throws DispatcherException;
	List<String> getCompletions(List<String> current, Namespace namespace);
	List<ExecutionPlan> getExecutionPlans();
}
