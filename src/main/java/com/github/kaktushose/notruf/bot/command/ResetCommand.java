package com.github.kaktushose.notruf.bot.command;

import com.github.kaktushose.jda.commands.annotations.Inject;
import com.github.kaktushose.jda.commands.annotations.interactions.Interaction;
import com.github.kaktushose.jda.commands.annotations.interactions.SlashCommand;
import com.github.kaktushose.jda.commands.data.EmbedCache;
import com.github.kaktushose.jda.commands.dispatching.interactions.commands.CommandEvent;

@Interaction(value = "reset", ephemeral = true)
public class ResetCommand {

    @Inject
    private EmbedCache embedCache;
    @Inject
    private RoleService roleService;

    @SlashCommand(desc = "DE: Macht alle Kanäle wieder sichtbar | EN: Makes all channels visible again")
    public void onReset(CommandEvent event) {
        roleService.removeRole(event.getUser(), Languages.GERMAN);
        roleService.removeRole(event.getUser(), Languages.ENGLISH);

        event.reply(embedCache.getEmbed("reset"));
    }
}
