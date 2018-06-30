package io.github.hedgehog1029.frame.dispatcher.pipeline;

import io.github.hedgehog1029.frame.dispatcher.exception.CommandNotFoundException;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.UsageException;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.*;
import java.util.stream.Collectors;

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

	public void addAliases(String... additional) {
		int original = this.aliases.length;
		this.aliases = Arrays.copyOf(this.aliases, original + additional.length);

		for (int i = 0; i < additional.length; i++) {
			this.aliases[original + i] = additional[i];
		}
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
	public List<String> getCompletions(List<String> current) {
		if (current.size() == 0) current.add("");
		if (current.size() == 1) {
			String partial = current.get(0);

			return this.pipelines.keySet().stream().filter((s) -> s.startsWith(partial)).collect(Collectors.toList());
		}

		String complete = current.get(0);
		if (pipelines.containsKey(complete)) {
			return pipelines.get(complete).getCompletions(current.subList(1, current.size()));
		}

		return Collections.emptyList();
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

	@Override
	public String getUsage() {
		String commands = String.join("/", pipelines.keySet());

		return String.format("<%s>", commands);
	}
}
