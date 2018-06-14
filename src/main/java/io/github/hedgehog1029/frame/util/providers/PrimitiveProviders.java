package io.github.hedgehog1029.frame.util.providers;

import io.github.hedgehog1029.frame.annotation.Text;
import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;

import java.lang.reflect.Parameter;

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
		public String provide(ICommandArguments args, Parameter param) throws DispatcherException {
			if (param.isAnnotationPresent(Text.class)) {
				StringBuilder builder = new StringBuilder(32);
				while (args.hasNext()) builder.append(args.next());

				return builder.toString();
			}

			return args.next();
		}
	}

	public static class IntegerProvider extends ConsumptionProvider<Integer> {
		@Override
		public Integer provide(ICommandArguments args, Parameter param) throws MissingArgumentsException, NumberFormatException {
			return Integer.valueOf(args.next());
		}
	}

	public static class FloatProvider extends ConsumptionProvider<Float> {
		@Override
		public Float provide(ICommandArguments args, Parameter param) throws DispatcherException, NumberFormatException {
			return Float.valueOf(args.next());
		}
	}

	public static class DoubleProvider extends ConsumptionProvider<Double> {
		@Override
		public Double provide(ICommandArguments args, Parameter param) throws DispatcherException, NumberFormatException {
			return Double.valueOf(args.next());
		}
	}

	public static class ShortProvider extends ConsumptionProvider<Short> {
		@Override
		public Short provide(ICommandArguments args, Parameter param) throws DispatcherException, NumberFormatException {
			return Short.valueOf(args.next());
		}
	}

	public static class BooleanProvider extends ConsumptionProvider<Boolean> {
		@Override
		public Boolean provide(ICommandArguments args, Parameter param) throws DispatcherException {
			switch (args.next()) {
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
