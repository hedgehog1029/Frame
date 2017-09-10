package io.github.hedgehog1029.frame.module.wrappers;

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
		} catch (IllegalAccessException | InvocationTargetException e) {
			throw new DispatcherException("Could not call method " + this.wrappedMethod.getName() +
			" of class " + this.parent.getClass().getName());
		}
	}

	public List<Parameter> getParameters() {
		return Arrays.asList(this.wrappedMethod.getParameters());
	}

	public <T extends Annotation> T getAnnotation(Class<T> clazz) {
		return this.wrappedMethod.getAnnotation(clazz);
	}
}
