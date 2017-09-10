package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.LoadedModule;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandInjector implements Injector {
	private final CommandDispatcher dispatcher;

	public CommandInjector(CommandDispatcher dispatcher) {
		this.dispatcher = dispatcher;
	}

	@Override
	public void inject(LoadedModule<?> module) {
		for (MethodWrapper method : module.getMethodsWithAnnotation(Command.class)) {
			Command commandAnnotation = method.getAnnotation(Command.class);
		}
	}
}
