package io.github.hedgehog1029.frame.module.wrappers;

import io.github.hedgehog1029.frame.annotation.Optional;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

public class ParameterWrapper {
	private final Parameter param;
	private Provider<?> provider;
	private boolean optional;
	private int willConsume;

	public ParameterWrapper(Parameter param, Provider<?> provider) {
		this.param = param;
		this.provider = provider;
		this.optional = param.isAnnotationPresent(Optional.class);
		this.willConsume = provider.argsWanted(this);
	}

	public boolean isOptional() {
		return optional;
	}

	public String getName() {
		return param.getName();
	}

	public int getWillConsume() {
		return willConsume;
	}

	public boolean isConsumptionTerminator() {
		return willConsume == 0 || optional;
	}

	public boolean isAnnotationPresent(Class<? extends Annotation> clazz) {
		return param.isAnnotationPresent(clazz);
	}

	public Provider<?> getProvider() {
		return provider;
	}
}
