package com.github.kaktushose.notruf.bot.language;

import net.dv8tion.jda.api.interactions.DiscordLocale;

public enum Languages {

    GERMAN,
    ENGLISH;

    public String localize(DiscordLocale locale) {
        if (this == GERMAN) {
            return locale == DiscordLocale.GERMAN ? "Deutsch" : "German";
        }
        return locale == DiscordLocale.GERMAN ? "Englisch" : "English";
    }

    public Languages inverse() {
        if (this == GERMAN) return ENGLISH;
        if (this == ENGLISH) return GERMAN;
        throw new IllegalStateException();
    }
}
