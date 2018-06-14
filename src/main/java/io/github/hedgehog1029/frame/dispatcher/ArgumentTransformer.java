package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.annotation.Optional;
import io.github.hedgehog1029.frame.dispatcher.arguments.CommandArgumentsDeque;
import io.github.hedgehog1029.frame.dispatcher.bindings.BindingList;
import io.github.hedgehog1029.frame.dispatcher.bindings.TypeToken;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.exception.UnsupportedTypeException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.util.Namespace;

import java.lang.reflect.Parameter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class ArgumentTransformer {
	private final BindingList bindings = new BindingList();

	public <T> void bind(Class<T> clazz, Provider<T> provider) {
		this.bindings.addBinding(TypeToken.get(clazz), provider);
	}

	// Problem: optional args don't work here at all
	public Object[] transform(String[] arguments, List<Parameter> parameters, Namespace namespace) throws DispatcherException, IndexOutOfBoundsException {
		CommandArgumentsDeque boundArgs = new CommandArgumentsDeque(Arrays.asList(arguments), namespace);
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
}
