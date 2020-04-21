package io.github.hedgehog1029.frame.dispatcher.bindings;

import io.github.hedgehog1029.frame.module.wrappers.ParameterWrapper;

public class ParamWithIndex {
	public final ParameterWrapper parameter;
	public final int index;

	public ParamWithIndex(ParameterWrapper parameter, int index) {
		this.parameter = parameter;
		this.index = index;
	}
}
