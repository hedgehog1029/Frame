package io.github.hedgehog1029.frame.loader;

import io.github.hedgehog1029.frame.annotations.Command;
import io.github.hedgehog1029.frame.annotations.HelpTopic;
import io.github.hedgehog1029.frame.annotations.Permission;
import io.github.hedgehog1029.frame.loader.exception.InaccessibleMethodException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class CommandMapping {
    private Command command;
    private Permission permission;
    private HelpTopic helpTopic;
    private Method method;
    private Object container;

    public CommandMapping(Command cmd, Method method, Object object) {
        this.command = cmd;
        this.method = method;
        this.container = object;

        if (method.isAnnotationPresent(Permission.class)) {
            this.permission = method.getAnnotation(Permission.class);
        }

        if (method.isAnnotationPresent(HelpTopic.class)) {
            this.helpTopic = method.getAnnotation(HelpTopic.class);
        }
    }

    public Command getCommand() {
        return this.command;
    }

    public Method getMethod() {
        return this.method;
    }

    public String getHelpTopic() {
        if (helpTopic != null)
            return this.helpTopic.value();
        else
            return null;
    }

    public String getPermission() {
        if (permission != null)
            return this.permission.value();
        else
            return null;
    }

    public String[] getAliases() {
        return this.command.aliases();
    }

    public void invoke(Object... args) throws InaccessibleMethodException {
        try {
            method.invoke(container, args);
        } catch (IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            throw new InaccessibleMethodException(method);
        }
    }
}
