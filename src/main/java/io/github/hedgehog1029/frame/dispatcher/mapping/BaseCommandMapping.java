package io.github.hedgehog1029.frame.dispatcher.mapping;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.dispatcher.CommandDispatcher;
import io.github.hedgehog1029.frame.util.Namespace;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class BaseCommandMapping {
	private final CommandDispatcher dispatcher;
	private final Command commandMetadata;

	public BaseCommandMapping(CommandDispatcher dispatcher, Command commandMetadata) {
		this.dispatcher = dispatcher;
		this.commandMetadata = commandMetadata;
	}

	public String[] getAliases() {
		return this.commandMetadata.aliases();
	}

	public String getPrimaryAlias() {
		return this.commandMetadata.aliases()[0];
	}

	public String getDescription() {
		return this.commandMetadata.description();
	}

	public void execute(String[] arguments, Namespace namespace) {
		this.dispatcher.dispatch(this.getPrimaryAlias(), arguments, namespace);
	}
}
