package io.github.hedgehog1029.frame.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Annotate a field with the same type as a Class that implements {@link io.github.hedgehog1029.frame.hook.IPluginHook}.
 * It will be populated by hook values.
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface Hook {
}
