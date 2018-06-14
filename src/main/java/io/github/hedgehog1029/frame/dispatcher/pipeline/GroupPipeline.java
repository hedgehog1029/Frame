package io.github.hedgehog1029.frame.dispatcher.pipeline;

import io.github.hedgehog1029.frame.dispatcher.exception.CommandNotFoundException;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.UsageException;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.Deque;
import java.util.HashMap;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class GroupPipeline implements IPipeline {
	private HashMap<String, IPipeline> pipelines = new HashMap<>(); // TODO: A better command graph
	private String[] aliases;

	public GroupPipeline(String... aliases) {
		this.aliases = aliases;
	}

	public void addPipeline(String key, IPipeline pipeline) {
		this.pipelines.put(key, pipeline);
	}

	public void addPipelines(String[] keys, IPipeline pipeline) {
		for (String key : keys)
			this.addPipeline(key, pipeline);
	}

	@Override
	public void call(Deque<String> arguments, Namespace namespace) throws DispatcherException {
		if (arguments.isEmpty()) {
			throw new UsageException();
		}

		String key = arguments.pop();

		if (pipelines.containsKey(key))
			pipelines.get(key).call(arguments, namespace);
		else {
			throw new CommandNotFoundException(key);
		}
	}

	@Override
	public String[] getAliases() {
		return aliases;
	}

	@Override
	public String getPrimaryAlias() {
		return aliases[0];
	}

	@Override
	public String getDescription() {
		return "A group command"; // TODO: this
	}
}
