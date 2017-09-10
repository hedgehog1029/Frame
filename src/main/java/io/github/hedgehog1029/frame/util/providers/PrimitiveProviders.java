package io.github.hedgehog1029.frame.util.providers;

import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class PrimitiveProviders {
	static abstract class ConsumptionProvider<T> implements Provider<T> {
		@Override
		public boolean consumesArguments() {
			return true;
		}
	}

	public static class StringProvider extends ConsumptionProvider<String> {
		@Override
		public String provide(ICommandArguments args) throws DispatcherException {
			return args.next().toString();
		}
	}

	public static class IntegerProvider extends ConsumptionProvider<Integer> {
		@Override
		public Integer provide(ICommandArguments args) throws MissingArgumentsException, NumberFormatException {
			return Integer.valueOf(args.next().toString());
		}

	}

	public static class FloatProvider extends ConsumptionProvider<Float> {
		@Override
		public Float provide(ICommandArguments args) throws DispatcherException, NumberFormatException {
			return Float.valueOf(args.next().toString());
		}
	}

	public static class DoubleProvider extends ConsumptionProvider<Double> {
		@Override
		public Double provide(ICommandArguments args) throws DispatcherException, NumberFormatException {
			return Double.valueOf(args.next().toString());
		}
	}

	public static class ShortProvider extends ConsumptionProvider<Short> {
		@Override
		public Short provide(ICommandArguments args) throws DispatcherException, NumberFormatException {
			return Short.valueOf(args.next().toString());
		}
	}

	public static class BooleanProvider extends ConsumptionProvider<Boolean> {
		@Override
		public Boolean provide(ICommandArguments args) throws DispatcherException {
			switch (args.next().toString()) {
				case "true":
				case "yes":
				case "y":
					return true;
				case "false":
				case "no":
				case "n":
					return false;
				default:
					throw new DispatcherException(); // TODO: throw an actually informative error
			}
		}
	}
}
