package com.github.kaktushose.languagebot.command;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Inject;
import com.github.kaktushose.jda.commands.annotations.interactions.Choices;
import com.github.kaktushose.jda.commands.annotations.interactions.Param;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;
import com.github.kaktushose.jda.commands.embeds.EmbedCache;

import java.util.Locale;

@CommandController(value = "opt out", ephemeral = true)
public class OptOutCommand {

    @Inject
    private EmbedCache embedCache;
    @Inject
    private RoleService roleService;

    @Command(name = "Opt-out", desc = "DE: Macht einen Sprachbereich unsichtbar | EN: Opt out from a language", isSuper = true)
    public void onOptOut(CommandEvent event, @Choices({"english", "german"}) @Param("DE: Die zu entfernende Sprache | EN: The language to opt out from") Languages language) {
        if (roleService.isInvalid(event.getUser(), language)) {
            event.reply(embedCache.getEmbed("error"));
            return;
        }

        roleService.addRole(event.getUser(), language);
        event.reply(embedCache.getEmbed("optOut")
                .injectValue("german", language.localize(Locale.GERMAN))
                .injectValue("english", language.localize(Locale.ENGLISH))
        );
    }
}
