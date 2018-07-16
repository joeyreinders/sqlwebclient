package net.sqlwebclient.controller.core;

import net.sqlwebclient.util.HttpStatus;

public final class CtrlException extends RuntimeException {
    private static final long serialVersionUID = -4918555322431907196L;
    private final ControllerException controllerException;

    CtrlException(  final ControllerException controllerException,
                    final String message) {
        super(message);
        this.controllerException = controllerException;
    }

    public ControllerException getControllerException() {
        return controllerException;
    }

    public HttpStatus getStatus() {
        return this.controllerException.getStatus();
    }
}
