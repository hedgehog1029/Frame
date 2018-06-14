package io.github.hedgehog1029.frame.hooks;

import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.LoadedModule;
import io.github.hedgehog1029.frame.util.ClassUtils;

import java.lang.reflect.Field;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class HookInjector implements Injector {
	private final IHookCallback hookCallback;

	public HookInjector(IHookCallback hookCallback) {
		this.hookCallback = hookCallback;
	}

	@Override
	public void inject(LoadedModule<?> module) throws InstantiationException, IllegalAccessException {
		for (Field f : module.getFieldsWithAnnotation(Hook.class)) {
			Hook hook = f.getDeclaredAnnotation(Hook.class);

			if (!f.isAccessible())
				f.setAccessible(true);

			Object instance;
			if (this.hookCallback.shouldHookLoad(hook.value())) {
				instance = ClassUtils.createInstance(hook.target());
			} else {
				instance = ClassUtils.createInstance(f.getType());
			}

			f.set(module.getInstance(), instance);
		}
	}
}
