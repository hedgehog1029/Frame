package io.github.hedgehog1029.frame.util;

import io.github.hedgehog1029.frame.dispatcher.ArgumentTransformer;
import io.github.hedgehog1029.frame.dispatcher.provider.Provider;
import io.github.hedgehog1029.frame.util.providers.PrimitiveProviders;

/**
 * Written by @offbeatwitch.
 * Licensed under MIT.
 */
public class PrimitiveBindingProvider implements IBindingProvider {
	@Override
	public void configure(ArgumentTransformer transformer) {
		Provider<Integer> integerProvider = new PrimitiveProviders.IntegerProvider();
		Provider<Float> floatProvider = new PrimitiveProviders.FloatProvider();
		Provider<Double> doubleProvider = new PrimitiveProviders.DoubleProvider();
		Provider<Short> shortProvider = new PrimitiveProviders.ShortProvider();
		Provider<Boolean> booleanProvider = new PrimitiveProviders.BooleanProvider();
		Provider<String> stringProvider = new PrimitiveProviders.StringProvider();

		transformer.bind(int.class, integerProvider);
		transformer.bind(Integer.class, integerProvider);
		transformer.bind(float.class, floatProvider);
		transformer.bind(Float.class, floatProvider);
		transformer.bind(double.class, doubleProvider);
		transformer.bind(Double.class, doubleProvider);
		transformer.bind(short.class, shortProvider);
		transformer.bind(Short.class, shortProvider);
		transformer.bind(boolean.class, booleanProvider);
		transformer.bind(Boolean.class, booleanProvider);
		transformer.bind(String.class, stringProvider);
	}
}
