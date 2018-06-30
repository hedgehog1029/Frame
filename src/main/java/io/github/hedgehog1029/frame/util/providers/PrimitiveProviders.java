package io.github.hedgehog1029.frame.util.providers;

import io.github.hedgehog1029.frame.annotation.Text;
import io.github.hedgehog1029.frame.dispatcher.arguments.ICommandArguments;
import io.github.hedgehog1029.frame.dispatcher.exception.DispatcherException;
import io.github.hedgehog1029.frame.dispatcher.exception.MissingArgumentsException;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;

import java.lang.reflect.Parameter;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class PrimitiveProviders {
	static abstract class PrimitiveProvider<T> implements Provider<T> {
		@Override
		public List<String> getSuggestions(String partial) {
			return Collections.emptyList();
		}
	}

	public static class StringProvider extends PrimitiveProvider<String> {
		@Override
		public String provide(ICommandArguments args, Parameter param) throws DispatcherException {
			if (param.isAnnotationPresent(Text.class)) {
				StringBuilder builder = new StringBuilder(32);
				while (args.hasNext()) {
					builder.append(args.next());
					builder.append(' ');
				}

				return builder.toString().trim();
			}

			return args.next();
		}
	}

	public static class IntegerProvider extends PrimitiveProvider<Integer> {
		@Override
		public Integer provide(ICommandArguments args, Parameter param) throws MissingArgumentsException, NumberFormatException {
			return Integer.valueOf(args.next());
		}
	}

	public static class FloatProvider extends PrimitiveProvider<Float> {
		@Override
		public Float provide(ICommandArguments args, Parameter param) throws DispatcherException, NumberFormatException {
			return Float.valueOf(args.next());
		}
	}

	public static class DoubleProvider extends PrimitiveProvider<Double> {
		@Override
		public Double provide(ICommandArguments args, Parameter param) throws DispatcherException, NumberFormatException {
			return Double.valueOf(args.next());
		}
	}

	public static class ShortProvider extends PrimitiveProvider<Short> {
		@Override
		public Short provide(ICommandArguments args, Parameter param) throws DispatcherException, NumberFormatException {
			return Short.valueOf(args.next());
		}
	}

	public static class BooleanProvider extends PrimitiveProvider<Boolean> {
		private static final List<String> suggestions = Arrays.asList(
				"true", "yes",
				"false", "no"
		);

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

		@Override
		public List<String> getSuggestions(String partial) {
			return suggestions.stream().filter(s -> s.startsWith(partial)).collect(Collectors.toList());
		}
	}
}
