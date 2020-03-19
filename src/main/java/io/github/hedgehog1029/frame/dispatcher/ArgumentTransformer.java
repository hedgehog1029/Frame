package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.annotation.Optional;
import io.github.hedgehog1029.frame.dispatcher.arguments.CommandArgumentsDeque;
import io.github.hedgehog1029.frame.dispatcher.bindings.BindingList;
import io.github.hedgehog1029.frame.dispatcher.bindings.TypeToken;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.exception.UnsupportedTypeException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Deque;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class ArgumentTransformer {
	private final BindingList bindings = new BindingList();

	public <T> void bind(Class<T> clazz, Provider<T> provider) {
		this.bindings.addBinding(TypeToken.get(clazz), provider);
	}

	protected Object[] transform(Deque<String> arguments, List<Parameter> parameters, Namespace namespace) throws DispatcherException, IndexOutOfBoundsException {
		CommandArgumentsDeque boundArgs = new CommandArgumentsDeque(arguments, namespace);
		List<Object> transformed = new ArrayList<>();

		for (Parameter param : parameters) {
			TypeToken<?> token = TypeToken.get(param.getType());
			Provider<?> p = this.bindings.getProvider(token);

			if (!boundArgs.hasNext() && param.isAnnotationPresent(Optional.class)) {
				transformed.add(null);
				continue;
			}

			if (p == null) {
				throw new UnsupportedTypeException("Parameter type " + token.getType() + " has no provider.");
			}

			Object provided = p.provide(boundArgs, param);

			if (provided == null && !param.isAnnotationPresent(Optional.class)) {
				throw new MissingArgumentsException(param); // TODO: this might not be necessary
			}

			transformed.add(provided);
		}

		return transformed.toArray();
	}

	public List<Parameter> getRequiredParameters(MethodWrapper method) {
		return method.getParameters().stream().filter(param -> {
			TypeToken<?> token = TypeToken.get(param.getType());
			Provider<?> p = this.bindings.getProvider(token);

			return p.willConsume(param);
		}).collect(Collectors.toList());
	}

	public String getUsage(MethodWrapper method) {
		StringBuilder builder = new StringBuilder();

		for (Parameter parameter : method.getParameters()) {
			TypeToken<?> type = TypeToken.get(parameter.getType());
			Provider<?> provider = this.bindings.getProvider(type);

			if (provider != null && !provider.willConsume(parameter)) continue;

			boolean isOptional = parameter.isAnnotationPresent(Optional.class);

			builder.append(isOptional ? '[' : '<')
					.append(parameter.getName())
					.append(isOptional ? ']' : '>')
					.append(' ');
		}

		return builder.toString().trim();
	}

	public <T> Provider<T> getProvider(TypeToken<T> token) {
		return this.bindings.getProvider(token);
	}
}
