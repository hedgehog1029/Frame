package io.github.hedgehog1029.frame.dispatcher.bindings;

import io.github.hedgehog1029.frame.dispatcher.provider.Provider;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class BindingEntry<T> {
	private final TypeToken<T> key;
	private final Provider<T> value;

	public BindingEntry(TypeToken<T> key, Provider<T> value) {
		this.key = key;
		this.value = value;
	}

	public TypeToken<T> getKey() {
		return key;
	}

	public Provider<T> getValue() {
		return value;
	}
}
