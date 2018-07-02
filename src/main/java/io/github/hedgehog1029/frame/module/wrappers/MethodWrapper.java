package io.github.hedgehog1029.frame.module.wrappers;

import io.github.hedgehog1029.frame.annotation.Optional;
import io.github.hedgehog1029.frame.annotation.Sender;
import io.github.hedgehog1029.frame.dispatcher.exception.CommandCallException;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class MethodWrapper {
	private final Object parent;
	private final Method wrappedMethod;

	public MethodWrapper(Object parent, Method wrappedMethod) {
		this.parent = parent;
		this.wrappedMethod = wrappedMethod;

		try {
			this.wrappedMethod.setAccessible(true);
		} catch (SecurityException ignored) {}
	}

	public void invoke(Object... args) throws DispatcherException {
		try {
			this.wrappedMethod.invoke(this.parent, args);
		} catch (IllegalAccessException e) {
			throw new CommandCallException("The command handler is inaccessible! Contact the plugin author.", e);
		} catch (InvocationTargetException e) {
			throw new CommandCallException(e);
		}
	}

	public List<Parameter> getParameters() {
		return Arrays.asList(this.wrappedMethod.getParameters());
	}

	public List<Parameter> getRequiredParameters() {
		return this.getParameters().stream()
				.filter(param -> !(
						param.isAnnotationPresent(Optional.class) || param.isAnnotationPresent(Sender.class)
				))
				.collect(Collectors.toList());
	}

	public String getUsage() {
		StringBuilder builder = new StringBuilder();

		for (Parameter parameter : this.getParameters()) {
			if (parameter.isAnnotationPresent(Sender.class)) continue;

			boolean isOptional = parameter.isAnnotationPresent(Optional.class);

			builder.append(isOptional ? '[' : '<')
					.append(parameter.getName())
					.append(isOptional ? ']' : '>')
					.append(' ');
		}

		return builder.toString().trim();
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.wrappedMethod.getAnnotation(clazz);
	}
}
