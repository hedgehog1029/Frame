package io.github.hedgehog1029.frame.dispatcher.arguments;

import java.lang.annotation.Annotation;
import java.lang.reflect.Parameter;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class Argument {
	private final Parameter bound;
	private final String argument;

	public Argument(Parameter bound, String argument) {
		this.bound = bound;
		this.argument = argument;
	}

	public boolean isAnnotated(Class<? extends Annotation> clazz) {
		return this.bound.isAnnotationPresent(clazz);
	}

	public String getName() {
		return this.bound.getName();
	}

	@Override
	public String toString() {
		return this.argument;
	}

	public int toInt() {
		return Integer.valueOf(this.argument);
	}
}
