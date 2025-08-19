package io.github.kaktushose.notruf.config.bot;

import java.util.Arrays;
import java.util.Collection;

public enum BotConfig {
    SERVERLOG_KANAL();

    public static Collection<BotConfig> getConfigs() {
        return Arrays.stream(BotConfig.values()).toList();
    }
}
