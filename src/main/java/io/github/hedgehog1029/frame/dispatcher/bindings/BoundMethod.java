package io.github.hedgehog1029.frame.dispatcher.bindings;

import io.github.hedgehog1029.frame.annotation.Text;
import io.github.hedgehog1029.frame.dispatcher.arguments.CommandArgumentsDeque;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.NotEnoughArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ArgumentNode;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ExecutionPlan;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Represents a command which has been analyzed and is now ready for invocation
 */
public class BoundMethod {
	private MethodWrapper method;
	private List<ParameterWrapper> parameters;

	public BoundMethod(MethodWrapper method, List<ParameterWrapper> parameters) {
		this.method = method;
		this.parameters = parameters;
	}

	public MethodWrapper getMethod() {
		return method;
	}

	public List<ParameterWrapper> getParameters() {
		return parameters;
	}

	public List<ParameterWrapper> getVisibleParameters() {
		return parameters.stream().filter(p -> p.getWillConsume() > 0).collect(Collectors.toList());
	}

	public List<ParamWithIndex> mapParametersToArity() {
		List<ParamWithIndex> mapped = new ArrayList<>();

		for (ParameterWrapper param : getVisibleParameters()) {
			for (int i = 0; i < param.getWillConsume(); i++) {
				mapped.add(new ParamWithIndex(param, i));
			}
		}

		return mapped;
	}

	public List<Integer> getAllArities() {
		List<Integer> found = new ArrayList<>();

		int arity = 0;
		for (ParameterWrapper p : parameters) {
			if (p.isOptional()) {
				if (!found.contains(arity))
					found.add(arity);
			}

			arity += p.getWillConsume();
		}

		if (!found.contains(arity)) {
			found.add(arity);
		}

		return found;
	}

	public List<ExecutionPlan> getExecutionPlans() {
		List<ParameterWrapper> visibleParams = getVisibleParameters();

		return getAllArities().stream().map(targetArity -> {
			int arity = targetArity;
			List<ArgumentNode> nodes = new ArrayList<>();

			int i = 0;
			while (arity > 0) {
				ParameterWrapper pw = visibleParams.get(i++);
				boolean isGreedy = pw.isAnnotationPresent(Text.class);

				for (int n = 0; n < pw.getWillConsume(); n++) {
					String name = pw.getName() + (n == 0 ? "" : n);

					if (isGreedy) {
						nodes.add(new ArgumentNode.GreedyString(name));
						// do we assert that getWillConsume() == 1 here?
					} else {
						nodes.add(new ArgumentNode.SingleString(name));
					}
				}

				arity -= pw.getWillConsume();
			}

			return new ExecutionPlan(targetArity, nodes);
		}).collect(Collectors.toList());
	}

	protected Object[] transform(Deque<String> arguments, Namespace namespace) throws DispatcherException {
		CommandArgumentsDeque boundArgs = new CommandArgumentsDeque(arguments, namespace);
		List<Object> transformed = new ArrayList<>();

		for (ParameterWrapper param : parameters) {
			if (!boundArgs.hasNext() && param.isOptional()) {
				transformed.add(null);
				continue;
			}

			Object provided = param.getProvider().provide(boundArgs, param);
			if (provided == null && !param.isOptional()) {
				throw new NotEnoughArgumentsException(param);
			}

			transformed.add(provided);
		}

		return transformed.toArray();
	}

	public String getUsage() {
		StringBuilder builder = new StringBuilder();

		// TODO: use arity info to show number of arguments in usage
		for (ParameterWrapper param : parameters) {
			builder.append(param.isOptional() ? '[' : '<')
					.append(param.getName())
					.append(param.isOptional() ? ']' : '>')
					.append(' ');
		}

		return builder.toString().trim();
	}

	public void invoke(Deque<String> arguments, Namespace namespace) throws DispatcherException {
		Object[] transformed = this.transform(arguments, namespace);
		method.invoke(transformed);
	}
}
