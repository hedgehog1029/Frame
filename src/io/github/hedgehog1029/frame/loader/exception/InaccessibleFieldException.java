package io.github.hedgehog1029.frame.loader.exception;

import java.lang.reflect.Field;

public class InaccessibleFieldException extends Exception {

    private Field offender;

    public InaccessibleFieldException(Field f) {
        this.offender = f;
    }

    public InaccessibleFieldException(){}

    public Field getOffender() {
        return offender;
    }
}
