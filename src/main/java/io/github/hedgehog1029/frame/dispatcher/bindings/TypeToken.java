package io.github.hedgehog1029.frame.dispatcher.bindings;

import java.lang.reflect.Type;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public final class TypeToken<T> {
	private Type type;

	private TypeToken(Type type) {
		this.type = type;
	}

	public Type getType() {
		return type;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		TypeToken<?> key = (TypeToken<?>) o;

		return type.equals(key.type);
	}

	@Override
	public int hashCode() {
		return type.hashCode();
	}

	public static <T> TypeToken<T> get(Class<T> type) {
		return new TypeToken<T>(type);
	}

	public static <T> TypeToken<T> get(Type type) {
		return new TypeToken<T>(type);
	}
}
