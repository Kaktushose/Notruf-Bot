package com.github.kaktushose.notruf.bot.bot;

import com.github.kaktushose.jda.commands.annotations.Component;
import com.github.kaktushose.jda.commands.dispatching.adapter.TypeAdapter;
import com.github.kaktushose.jda.commands.dispatching.interactions.Context;
import com.github.kaktushose.notruf.bot.command.Languages;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Component
public class EnumAdapter implements TypeAdapter<Languages> {

    @Override
    public Optional<Languages> parse(@NotNull String raw, @NotNull Context context) {
        try {
            return Optional.of(Languages.valueOf(raw.toUpperCase()));
        } catch (IllegalArgumentException ignored) {
            return Optional.empty();
        }
    }
}
