package io.github.hedgehog1029.frame.dispatcher;

import io.github.hedgehog1029.frame.util.Namespace;

public class CommandDispatcher {
	private final ArgumentTransformer transformer = new ArgumentTransformer();

	public void dispatch(String command, String[] arguments, Namespace namespace) {
		// TODO: The dispatcher probably won't actually hold onto the commands,
		// TODO: because they're registered to whatever external service.
		// TODO: For this reason, this signature will change shortly to accept a CommandMapping.
	}

	public ArgumentTransformer getTransformer() {
		return transformer;
	}
}
