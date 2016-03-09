package io.github.hedgehog1029.frame.annotations;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] aliases();
    String usage() default "";
    String desc();
}
