package io.github.hedgehog1029.frame.loader.exception;

public class InaccessibleClassException extends Exception {

    private Class offender;

    public InaccessibleClassException(Class clazz) {
        this.offender = clazz;
    }

    public Class getOffender() {
        return this.offender;
    }
}
