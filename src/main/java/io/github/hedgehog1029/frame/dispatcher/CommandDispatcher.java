package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.NotEnoughArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.mapping.CommandMapping;
import io.github.hedgehog1029.frame.util.Namespace;

import java.util.Deque;

public class CommandDispatcher {
	private final ArgumentTransformer transformer = new ArgumentTransformer();

	public void dispatch(CommandMapping command, Deque<String> arguments, Namespace namespace) throws DispatcherException {
		try {
			Object[] transformed = this.transformer.transform(arguments, command.getWrappedMethod().getParameters(), namespace);

			command.getWrappedMethod().invoke(transformed);
		} catch (IndexOutOfBoundsException e) {
			// Problem with arguments. In future this should be handled differently
			throw new NotEnoughArgumentsException(arguments.toArray(new String[0]), null); // TODO: make this not null
		}
	}

	public ArgumentTransformer getTransformer() {
		return transformer;
	}
}
