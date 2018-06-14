package io.github.hedgehog1029.frame.dispatcher.mapping;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public interface ICommandMapping {
	String[] getAliases();
	String getPrimaryAlias();
	String getDescription();
	String getUsage();
}
