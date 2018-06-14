package io.github.hedgehog1029.frame.dispatcher.mapping;

import io.github.hedgehog1029.frame.dispatcher.pipeline.IPipeline;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public interface ICommandFactory {
	void registerCommand(IPipeline pipeline);
}
