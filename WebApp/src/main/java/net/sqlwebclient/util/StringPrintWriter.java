package net.sqlwebclient.util;

import java.io.PrintWriter;
import java.io.StringWriter;

public final class StringPrintWriter extends PrintWriter {
    private StringPrintWriter() {
        super(new StringWriter());
    }

    public String getString() {
        flush();
        return this.out.toString();
    }

    public static StringPrintWriter newInstance() {
        return new StringPrintWriter();
    }
}
