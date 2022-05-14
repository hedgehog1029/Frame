package io.github.hedgehog1029.frame.dispatcher.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one valid argument set for executing a pipeline
 */
public class ExecutionPlan {
	public final int arity;
	private List<ArgumentNode> arguments;

	public ExecutionPlan(int arity, List<ArgumentNode> arguments) {
		this.arity = arity;
		this.arguments = arguments;
	}

	public List<ArgumentNode> getArguments() {
		return arguments;
	}

	public ExecutionPlan withPrepended(ArgumentNode node) {
		ArrayList<ArgumentNode> paramsCopy = new ArrayList<>(arguments);
		paramsCopy.add(0, node);

		return new ExecutionPlan(arity + 1, paramsCopy);
	}
}
