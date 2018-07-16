package net.sqlwebclient.controller.core;

import net.sqlwebclient.util.HttpStatus;

public enum ControllerException {
    CONTROLLER_NOT_FOUND(HttpStatus.NOT_FOUND, "Controller not found"),
    CONTROLLER_NOT_ACCEPTING_METHOD(HttpStatus.METHOD_NOT_ALLOWED, "Controller does not accept method"),
    INTERNAL_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, "Internal Server Error"),
    PARAM_MISSING(HttpStatus.BAD_REQUEST, "Parameter %s missing"),
    TIME_OUT(HttpStatus.REQUEST_TIMEOUT, "Timeout"),
    REQUEST_METHOD_NOT_IMPLEMENTED(HttpStatus.NOT_IMPLEMENTED, "Request Method %s not implemented"),
    INVALID_REQUEST(HttpStatus.BAD_REQUEST, "Invalid request"),
    NOT_FOUND(HttpStatus.NOT_FOUND, "Object not found"),
    CONNECTION_DETAILS_NOT_PROVIDED(HttpStatus.BAD_REQUEST, "No connection details id was provided")
    ;

    private final String message;
    private final HttpStatus status;

    ControllerException(final HttpStatus status, final String message) {
        this.status = status;
        this.message = message;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public CtrlException throwNew() {
        return new CtrlException(this, message);
    }

    public CtrlException throwNew(final String msg) {
        return new CtrlException(this, msg);
    }

    public CtrlException throwNewFormatted(final Object ... toResolve) {
        return new CtrlException(this, String.format(message, toResolve));
    }
}
