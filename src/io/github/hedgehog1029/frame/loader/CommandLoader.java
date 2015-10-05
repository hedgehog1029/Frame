package io.github.hedgehog1029.frame.loader;

import io.github.hedgehog1029.frame.dispatcher.exception.CommandExistsException;
import io.github.hedgehog1029.frame.inject.FrameInjector;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleClassException;
import org.bukkit.Bukkit;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

public class CommandLoader {

    private static Set<Class<?>> annotated = new HashSet<>();

    public static void add(Class clazz) {
        annotated.add(clazz);
    }

    public void load() throws CommandExistsException, InaccessibleClassException {


        for (Class<?> c : annotated) {
            Object instance;

            try {
                instance = c.newInstance();
            } catch (InstantiationException | IllegalAccessException e) {
                throw new InaccessibleClassException(c);
            }

            final Method[] methods = c.getDeclaredMethods();

            for (Method method : methods) {
                if (method.isAnnotationPresent(Command.class)) {
                    Command details = method.getAnnotation(Command.class);

                    FrameInjector.getDispatcher().registerCommand(details, method, instance);
                }
            }
        }

        FrameInjector.getDispatcher().registerManagedCommandsWithBukkit();
    }
}
