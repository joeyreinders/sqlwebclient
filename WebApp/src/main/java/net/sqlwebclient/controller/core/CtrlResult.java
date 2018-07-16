package net.sqlwebclient.controller.core;

import net.sqlwebclient.util.Builder;
import net.sqlwebclient.util.HttpStatus;

import java.io.Serializable;

public final class CtrlResult implements Serializable {
    private static final long serialVersionUID = 8520436550076556226L;

    private Object responseObject;

    private CtrlException exception;

    private HttpStatus status;

    private CtrlResult() {}

    public Object getResponseObject() {
        return responseObject;
    }

    public HttpStatus getStatus() {
        return status;
    }

    public CtrlException getException() {
        return exception;
    }

    public boolean exceptionOccurred() {
        return exception != null;
    }

    public static CtrlResultBuilder newBuilder() {
        return new CtrlResultBuilder();
    }

    public static class CtrlResultBuilder implements Builder<CtrlResult> {
        private Object responseObject;
        private CtrlException exception;
        private HttpStatus status;

        private CtrlResultBuilder() {}

        @Override
        public CtrlResult build() {
            final CtrlResult res = new CtrlResult();
            res.responseObject = this.responseObject;
            res.exception = this.exception;
            res.status = this.status;
            return res;
        }

        public CtrlResultBuilder addResponseObject(final Object json) {
            this.responseObject = json;
            return this;
        }

        public CtrlResultBuilder addException(final CtrlException ce) {
            this.exception = ce;
            this.status = ce.getStatus();
            return this;
        }

        public CtrlResultBuilder addStatus(final HttpStatus status) {
            this.status = status;
            return this;
        }
    }
}
