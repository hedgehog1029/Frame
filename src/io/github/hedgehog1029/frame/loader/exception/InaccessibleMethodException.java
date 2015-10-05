package io.github.hedgehog1029.frame.loader.exception;

import java.lang.reflect.Method;

public class InaccessibleMethodException extends Exception {

    private Method offender;

    public InaccessibleMethodException(Method m) {
        this.offender = m;
    }

    public Method getOffender() {
        return this.offender;
    }
}
