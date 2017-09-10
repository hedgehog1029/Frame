package io.github.hedgehog1029.frame.util;

import java.util.HashMap;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 * Holds values.
 */
public class Namespace {
	private final HashMap<String, Object> namespace = new HashMap<>();

	public Object get(String key) {
		return namespace.get(key);
	}

	public void set(String key, Object value) {
		namespace.put(key, value);
	}
}
