package io.github.hedgehog1029.frame.module;

import io.github.hedgehog1029.frame.dispatcher.bindings.TypeToken;

import java.lang.reflect.InvocationTargetException;
import java.util.Collection;
import java.util.HashMap;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class ModuleLoader {
	private HashMap<TypeToken<?>, LoadedModule<?>> loadedModules = new HashMap<>();

	@SuppressWarnings("unchecked")
	public <T> LoadedModule<T> getModule(TypeToken<T> key) {
		return (LoadedModule<T>) this.loadedModules.get(key);
	}

	public Collection<LoadedModule<?>> getAllModules() {
		return this.loadedModules.values();
	}

	public <T> void loadModule(T instance) {
		LoadedModule<T> module = new LoadedModule<T>(instance);

		loadedModules.put(module.getTypeToken(), module);
	}

	public <T> void loadModule(Class<T> clazz) throws IllegalAccessException, InstantiationException, NoSuchMethodException, InvocationTargetException {
		T instance = clazz.getDeclaredConstructor().newInstance();

		this.loadModule(instance);
	}
}
