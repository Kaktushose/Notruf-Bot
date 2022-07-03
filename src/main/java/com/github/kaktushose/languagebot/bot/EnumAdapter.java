package com.github.kaktushose.languagebot.bot;

import com.github.kaktushose.jda.commands.annotations.Component;
import com.github.kaktushose.jda.commands.dispatching.CommandContext;
import com.github.kaktushose.jda.commands.dispatching.adapter.TypeAdapter;
import com.github.kaktushose.languagebot.command.Languages;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

@Component
public class EnumAdapter implements TypeAdapter<Languages> {

    @Override
    public Optional<Languages> parse(@NotNull String raw, @NotNull CommandContext context) {
        try {
            return Optional.of(Languages.valueOf(raw.toUpperCase()));
        } catch (IllegalArgumentException ignored) {
            return Optional.empty();
        }
    }
}
