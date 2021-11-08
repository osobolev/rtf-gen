package com.lowagie.text.rtf.document.output;

import java.io.PrintStream;

public class RtfLoggerSimple implements RtfLogger {

    private final PrintStream output;

    public RtfLoggerSimple(PrintStream output) {
        this.output = output;
    }

    @SuppressWarnings("UseOfSystemOutOrSystemErr")
    public RtfLoggerSimple() {
        this(System.err);
    }

    @Override
    public void warn(String message) {
        output.println(message);
    }

    @Override
    public void error(Throwable error) {
        error.printStackTrace(output);
    }
}
