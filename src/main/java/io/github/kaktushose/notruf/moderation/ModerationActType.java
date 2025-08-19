package io.github.kaktushose.notruf.moderation;

public enum ModerationActType {
    WARN("Verwarnung"),
    TIMEOUT("Timeout"),
    KICK("Kick"),
    TEMP_BAN("Temporärer Bann"),
    BAN("Bann");

    public final String humanReadableString;

    ModerationActType(String humanReadableString) {
        this.humanReadableString = humanReadableString;
    }

    public boolean isBan() {
        return this == BAN || this == TEMP_BAN;
    }

    public boolean isTemp() {
        return this == TEMP_BAN || this == TIMEOUT;
    }
}
