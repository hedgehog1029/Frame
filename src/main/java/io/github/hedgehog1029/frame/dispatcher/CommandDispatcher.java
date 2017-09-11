package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.UnsupportedTypeException;
import io.github.hedgehog1029.frame.dispatcher.mapping.CommandMapping;
import io.github.hedgehog1029.frame.util.Namespace;

public class CommandDispatcher {
	private final ArgumentTransformer transformer = new ArgumentTransformer();

	public void dispatch(CommandMapping command, String[] arguments, Namespace namespace) {
		try {
			Object[] transformed = this.transformer.transform(arguments, command.getWrappedMethod().getParameters(), namespace);

			command.getWrappedMethod().invoke(transformed);
		} catch (UnsupportedTypeException e) {
			// TODO: Not sure how to get these out. Possibly an interface so the user can do whatever with it?
			e.printStackTrace();
		} catch (DispatcherException e) {
			e.printStackTrace();
		}
	}

	public ArgumentTransformer getTransformer() {
		return transformer;
	}
}
