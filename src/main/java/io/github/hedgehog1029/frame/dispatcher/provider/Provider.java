package io.github.hedgehog1029.frame.dispatcher.provider;

import io.github.hedgehog1029.frame.annotation.Text;
import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.pipeline.ArgumentNode;
import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.List;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 *
 *
 */
public interface Provider<T> {
	T provide(ICommandArguments args, ParameterWrapper param) throws DispatcherException;

	List<String> getSuggestions(int index, String partial, Namespace namespace);
	int argsWanted(ParameterWrapper param);

	/**
	 * Called when the execution planner wants an argument node.
	 * @param name Name to use for this argument. Use this rather than {@link ParameterWrapper#getName}!
	 * @param param Parameter this is operating on
	 * @return An argument node for this parameter, used by implementors for hinting
	 */
	default ArgumentNode makeNode(String name, ParameterWrapper param) {
		if (param.isAnnotationPresent(Text.class)) {
			return new ArgumentNode.GreedyString(name);
		} else {
			return new ArgumentNode.SingleString(name);
		}
	}
}
