package io.github.hedgehog1029.frame.dispatcher;

public class CommandDispatcher {
	private final ArgumentTransformer transformer = new ArgumentTransformer();

	public ArgumentTransformer getTransformer() {
		return transformer;
	}
}
