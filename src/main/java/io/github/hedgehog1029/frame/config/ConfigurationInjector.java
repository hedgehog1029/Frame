package io.github.hedgehog1029.frame.config;

import io.github.hedgehog1029.frame.annotations.InjectConfig;
import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.logger.Logger;
import io.github.hedgehog1029.frame.module.ModuleLoader;

import java.lang.reflect.Field;

public class ConfigurationInjector implements Injector {
	@Override
	public void inject(Class<?> c, Object instance) {
		for (Field f : c.getDeclaredFields()) {
			if (f.isAnnotationPresent(InjectConfig.class)) {
				Object config = ConfigurationBuilder.get(f.getType());

				if (config != null) {
					try {
						f.setAccessible(true);
						f.set(instance, config);
					} catch (IllegalAccessException e) {
						Logger.err("Failed to inject a configuration into field " + f.getName() + " in " + c.getName());
					}
				}
			}
		}
	}
}
