package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.dispatcher.bindings.BindingList;
import io.github.hedgehog1029.frame.dispatcher.bindings.BoundMethod;
import io.github.hedgehog1029.frame.dispatcher.bindings.TypeToken;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;

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

	/**
	 * Prepare a wrapped method for later invocation
	 * @param method Method to prepare
	 * @return BoundMethod instance with providers worked out
	 */
	public BoundMethod prepare(MethodWrapper method) {
		List<ParameterWrapper> boundParams = method.getParameters().stream().map(param -> {
			TypeToken<?> token = TypeToken.get(param.getType());
			Provider<?> p = this.bindings.getProvider(token);

			return new ParameterWrapper(param, p);
		}).collect(Collectors.toList());

		return new BoundMethod(method, boundParams);
	}
}
