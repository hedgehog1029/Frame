package io.github.hedgehog1029.frame.module;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public interface IModule<T> {
	Class<T> getModuleClass();
	T getInstance();
}
