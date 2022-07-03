package com.github.kaktushose.languagebot.command;

import java.util.Locale;

public enum Languages {

    GERMAN,
    ENGLISH;

    public String localize(Locale locale) {
        if (locale == Locale.GERMAN) {
            if (this == GERMAN) return "Deutsch";
            if (this == ENGLISH) return "Englisch";
        }
        if (locale == Locale.ENGLISH) {
            if (this == GERMAN) return "German";
            if (this == ENGLISH) return "English";
        }
        return "N/A";
    }

    public Languages inverse() {
        if (this == GERMAN) return ENGLISH;
        if (this == ENGLISH) return GERMAN;
        throw new IllegalStateException();
    }
}
