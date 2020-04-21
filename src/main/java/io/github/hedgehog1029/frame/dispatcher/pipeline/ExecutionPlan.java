package io.github.hedgehog1029.frame.dispatcher.pipeline;

import java.util.ArrayList;
import java.util.List;

/**
 * Represents one valid argument set for executing a pipeline
 */
public class ExecutionPlan {
	public final int arity;
	private List<String> parameterNames;

	public ExecutionPlan(int arity, List<String> parameterNames) {
		this.arity = arity;
		this.parameterNames = parameterNames;
	}

	public List<String> getParameterNames() {
		return parameterNames;
	}

	public ExecutionPlan withPrepended(String name) {
		ArrayList<String> paramsCopy = new ArrayList<>(parameterNames);
		paramsCopy.add(0, name);

		return new ExecutionPlan(arity + 1, paramsCopy);
	}
}
