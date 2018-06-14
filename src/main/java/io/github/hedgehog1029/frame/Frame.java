package io.github.hedgehog1029.frame;

import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import io.github.hedgehog1029.frame.dispatcher.CommandInjector;
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandFactory;
import io.github.hedgehog1029.frame.hooks.HookInjector;
import io.github.hedgehog1029.frame.hooks.IHookCallback;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.ModuleLoader;
import io.github.hedgehog1029.frame.util.IBindingProvider;
import io.github.hedgehog1029.frame.util.PrimitiveBindingProvider;

public class Frame {
	private final CommandDispatcher dispatcher;
	private final ModuleLoader loader;
	private final FrameInjector injector;
	private final ICommandFactory commandFactory;
	private final IHookCallback hookCallback;

	private Frame(CommandDispatcher dispatcher, ModuleLoader loader, FrameInjector injector, ICommandFactory commandFactory, IHookCallback hookCallback) {
		this.dispatcher = dispatcher;
		this.loader = loader;
		this.injector = injector;
		this.commandFactory = commandFactory;
		this.hookCallback = hookCallback;

		this.loadBindings(new PrimitiveBindingProvider());
	}

	/**
	 * Load a binding provider, allowing you to register providers for types.
	 *
	 * @param module Binding provider instance
	 */
	public void loadBindings(IBindingProvider module) {
		module.configure(this.dispatcher.getTransformer());
	}

	/**
	 * Load an instance as a Frame module.
	 *
	 * @param module Module to load
	 * @param <T>    Type token
	 */
	public <T> void loadModule(T module) {
		this.loader.loadModule(module);
	}

	/**
	 * Add a class that gets given modules to modify their state.
	 *
	 * @param injector Injection callback to add to Frame's injector
	 */
	public void addInjector(Injector injector) {
		this.injector.injector(injector);
	}

	public ICommandFactory getCommandFactory() {
		return this.commandFactory;
	}

	public CommandDispatcher getDispatcher() {
		return dispatcher;
	}

	/**
	 * Traverses all modules with the injector and builds the command graph.
	 */
	public void go() {
		this.injector
				.injector(new CommandInjector(this.dispatcher, this.commandFactory))
				.injector(new HookInjector(this.hookCallback));

		this.injector.injectAll(this.loader);
	}

	public static class Builder {
		private CommandDispatcher dispatcher;
		private ModuleLoader loader;
		private FrameInjector injector;
		private ICommandFactory commandFactory;
		private IHookCallback hookCallback;

		public CommandDispatcher getDispatcher() {
			return dispatcher;
		}

		public Builder setDispatcher(CommandDispatcher dispatcher) {
			this.dispatcher = dispatcher;
			return this;
		}

		public ModuleLoader getLoader() {
			return loader;
		}

		public Builder setLoader(ModuleLoader loader) {
			this.loader = loader;
			return this;
		}

		public FrameInjector getInjector() {
			return injector;
		}

		public Builder setInjector(FrameInjector injector) {
			this.injector = injector;
			return this;
		}

		public ICommandFactory getCommandFactory() {
			return commandFactory;
		}

		public Builder setCommandFactory(ICommandFactory commandFactory) {
			this.commandFactory = commandFactory;
			return this;
		}

		public IHookCallback getHookCallback() {
			return hookCallback;
		}

		public Builder setHookCallback(IHookCallback hookCallback) {
			this.hookCallback = hookCallback;
			return this;
		}

		public Frame build() {
			if (this.dispatcher == null) this.dispatcher = new CommandDispatcher();
			if (this.loader == null) this.loader = new ModuleLoader();
			if (this.injector == null) this.injector = new FrameInjector();
			if (this.commandFactory == null) {
				throw new IllegalStateException("Frame must be configured with a command factory!");
			}

			return new Frame(dispatcher, loader, injector, commandFactory, hookCallback);
		}
	}
}
