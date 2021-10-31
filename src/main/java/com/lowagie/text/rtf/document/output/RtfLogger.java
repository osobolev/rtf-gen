package com.lowagie.text.rtf.document.output;

public interface RtfLogger {

    void warn(String message);

    void error(Throwable error);
}
