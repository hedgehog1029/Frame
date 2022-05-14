package io.github.hedgehog1029.frame.dispatcher.pipeline;

public abstract class ArgumentNode {
	private String name;

	public ArgumentNode(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public static class Literal extends ArgumentNode {
		private String literal;

		public Literal(String literal) {
			super(literal);
			this.literal = literal;
		}

		public String getLiteral() {
			return literal;
		}
	}

	public static class SingleString extends ArgumentNode {
		public SingleString(String name) {
			super(name);
		}
	}

	public static class GreedyString extends ArgumentNode {
		public GreedyString(String name) {
			super(name);
		}
	}
}
