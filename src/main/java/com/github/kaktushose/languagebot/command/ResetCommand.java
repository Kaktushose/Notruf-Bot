package com.github.kaktushose.languagebot.command;

import com.github.kaktushose.jda.commands.annotations.Command;
import com.github.kaktushose.jda.commands.annotations.CommandController;
import com.github.kaktushose.jda.commands.annotations.Inject;
import com.github.kaktushose.jda.commands.dispatching.CommandEvent;
import com.github.kaktushose.jda.commands.embeds.EmbedCache;

@CommandController(value = "reset", ephemeral = true)
public class ResetCommand {

    @Inject
    private EmbedCache embedCache;
    @Inject
    private RoleService roleService;

    @Command(name = "Reset", desc = "DE: Macht alle Kanäle wieder sichtbar | EN: Makes all channels visible again", isSuper = true)
    public void onReset(CommandEvent event) {
        roleService.removeRole(event.getUser(), Languages.GERMAN);
        roleService.removeRole(event.getUser(), Languages.ENGLISH);

        event.reply(embedCache.getEmbed("reset"));
    }
}
