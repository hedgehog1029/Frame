package io.github.hedgehog1029.frame;

import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import io.github.hedgehog1029.frame.dispatcher.CommandInjector;
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandFactory;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.ModuleLoader;
import io.github.hedgehog1029.frame.util.IBindingProvider;

class Frame {
	private CommandDispatcher dispatcher;
	private ModuleLoader loader;
	private FrameInjector injector;
	private ICommandFactory commandFactory;

    public Frame() {
    	this.dispatcher = new CommandDispatcher();
    	this.loader = new ModuleLoader();
    	this.injector = new FrameInjector();

    	this.injector.injector(new CommandInjector(this.dispatcher));
    }

	/**
	 * Load a binding provider, allowing you to register providers for types.
	 * @param module Binding provider instance
	 */
	public void loadBindings(IBindingProvider module) {
    	module.configure(this.dispatcher.getTransformer());
    }

	/**
	 * Load an instance as a Frame module.
	 * @param module Module to load
	 * @param <T> Type token
	 */
	public <T> void loadModule(T module) {
    	this.loader.loadModule(module);
    }

	/**
	 * Add a class that gets given modules to modify their state.
	 * @param injector Injection callback to add to Frame's injector
	 */
	public void addInjector(Injector injector) {
    	this.injector.injector(injector);
    }

    public void setCommandFactory(ICommandFactory factory) {
		this.commandFactory = factory;
    }

	/**
	 * Traverses all modules with the injector and builds the command graph.
	 */
	public void go() {
		this.injector.injectAll(this.loader);
    }
}
