package io.github.hedgehog1029.frame.dispatcher.bindings;

import io.github.hedgehog1029.frame.dispatcher.provider.Provider;

import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class BindingList {
	private final HashMap<Type, BindingEntry> bindings = new HashMap<>();

	public <T> void addBinding(TypeToken<T> type, Provider<T> provider) {
		bindings.put(type.getType(), new BindingEntry<T>(type, provider));
	}

	@SuppressWarnings("unchecked")
	public <T> BindingEntry<T> getBinding(TypeToken<T> key) {
		return bindings.get(key.getType());
	}

	public <T> Provider<T> getProvider(TypeToken<T> key) {
		return this.getBinding(key).getValue();
	}
}
