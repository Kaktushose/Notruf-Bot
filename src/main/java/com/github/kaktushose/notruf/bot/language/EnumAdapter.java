package com.github.kaktushose.notruf.bot.language;

import com.github.kaktushose.jda.commands.annotations.Implementation;
import com.github.kaktushose.jda.commands.dispatching.adapter.TypeAdapter;
import com.github.kaktushose.jda.commands.dispatching.interactions.Context;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Implementation
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
