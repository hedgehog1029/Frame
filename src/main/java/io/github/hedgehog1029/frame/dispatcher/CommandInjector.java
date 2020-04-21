package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.annotation.Command;
import io.github.hedgehog1029.frame.annotation.Group;
import io.github.hedgehog1029.frame.dispatcher.mapping.CommandMapping;
import io.github.hedgehog1029.frame.dispatcher.mapping.ICommandFactory;
import io.github.hedgehog1029.frame.dispatcher.pipeline.GroupPipeline;
import io.github.hedgehog1029.frame.inject.Injector;
import io.github.hedgehog1029.frame.module.LoadedModule;
import io.github.hedgehog1029.frame.module.wrappers.MethodWrapper;

import java.util.HashMap;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class CommandInjector implements Injector {
	private CommandDispatcher dispatcher;
	private ICommandFactory factory;
	private HashMap<String, GroupPipeline> groups;

	public CommandInjector(CommandDispatcher dispatcher, ICommandFactory factory) {
		this.dispatcher = dispatcher;
		this.factory = factory;
		this.groups = new HashMap<>();
	}

	@Override
	public void inject(LoadedModule<?> module) {
		Group moduleGroupInfo = null;
		if (module.getModuleClass().isAnnotationPresent(Group.class)) {
			moduleGroupInfo = module.getModuleClass().getAnnotation(Group.class);
		}

		for (MethodWrapper method : module.getMethodsWithAnnotation(Command.class)) {
			Command commandAnnotation = method.getAnnotation(Command.class);
			Group groupInfo = method.getAnnotation(Group.class);
			if (groupInfo == null && moduleGroupInfo != null)
				groupInfo = moduleGroupInfo;

			CommandMapping mapping = new CommandMapping(dispatcher, commandAnnotation, method);

			if (groupInfo != null) {
				String[] aliases = groupInfo.value();
				String name = aliases[0];

				if (!groups.containsKey(name)) {
					GroupPipeline group = new GroupPipeline(aliases);
					groups.put(name, group);
				}

				groups.get(name).addPipelines(mapping.getAliases(), mapping);
			} else {
				this.factory.registerCommand(mapping);
			}
		}
	}

	@Override
	public void cleanup() {
		this.groups.forEach((key, mapping) ->
				this.factory.registerCommand(mapping));
		this.groups.clear();
	}
}
