package io.github.hedgehog1029.frame.module;

import io.github.hedgehog1029.frame.dispatcher.bindings.TypeToken;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;

import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 * Helper wrapper for a module which has commands, etc.
 */
public class LoadedModule<T> implements IModule<T> {
	private final T instance;

	public LoadedModule(T instance) {
		this.instance = instance;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Class<T> getModuleClass() {
		return (Class<T>) instance.getClass();
	}

	public TypeToken<T> getTypeToken() {
		return TypeToken.get(this.getModuleClass());
	}

	@Override
	public T getInstance() {
		return instance;
	}

	public MethodWrapper getMethod(String name) {
		try {
			return new MethodWrapper(this, this.getModuleClass().getDeclaredMethod(name));
		} catch (NoSuchMethodException e) {
			return null;
		}
	}

	public List<MethodWrapper> getMethodsWithAnnotation(Class<? extends Annotation> clazz) {
		List<MethodWrapper> methods = new ArrayList<MethodWrapper>();

		for (Method m : this.getModuleClass().getDeclaredMethods()) {
			if (m.isAnnotationPresent(clazz)) {
				methods.add(new MethodWrapper(instance, m));
			}
		}

		return methods;
	}
}
