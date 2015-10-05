package io.github.hedgehog1029.frame.loader;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

@Retention(RetentionPolicy.RUNTIME)
public @interface Command {
    String[] aliases();
    String usage() default "";
    String desc();
    String permission() default "";
    String helpTopic() default "Commands";
}
