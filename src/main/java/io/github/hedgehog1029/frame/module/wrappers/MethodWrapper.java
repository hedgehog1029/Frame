package io.github.hedgehog1029.frame.module.wrappers;

import io.github.hedgehog1029.frame.dispatcher.exception.CommandCallException;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;

import java.lang.annotation.Annotation;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.List;

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
			throw new CommandCallException(e.getCause().getMessage(), e);
		}
	}

	public List<Parameter> getParameters() {
		return Arrays.asList(this.wrappedMethod.getParameters());
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.wrappedMethod.getAnnotation(clazz);
	}

	public Object getParent() {
		return parent;
	}
}
