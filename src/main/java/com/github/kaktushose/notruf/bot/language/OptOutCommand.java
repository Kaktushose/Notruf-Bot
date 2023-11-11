package com.github.kaktushose.notruf.bot.language;

import com.github.kaktushose.jda.commands.annotations.Inject;
import com.github.kaktushose.jda.commands.annotations.interactions.Choices;
import com.github.kaktushose.jda.commands.annotations.interactions.Interaction;
import com.github.kaktushose.jda.commands.annotations.interactions.Param;
import com.github.kaktushose.jda.commands.annotations.interactions.SlashCommand;
import com.github.kaktushose.jda.commands.data.EmbedCache;
import com.github.kaktushose.jda.commands.dispatching.interactions.commands.CommandEvent;

import java.util.Locale;

@Interaction(value = "opt out", ephemeral = true)
public class OptOutCommand {

    @Inject
    private EmbedCache embedCache;
    @Inject
    private RoleService roleService;

    @SlashCommand(desc = "Opt out from a language")
    public void onOptOut(CommandEvent event, @Choices({"english", "german"}) @Param("The language to opt out from") Languages language) {
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
