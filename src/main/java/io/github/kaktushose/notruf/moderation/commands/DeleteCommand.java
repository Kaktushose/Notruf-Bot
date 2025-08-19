package io.github.kaktushose.notruf.moderation.commands;

import com.github.kaktushose.jda.commands.annotations.interactions.*;
import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent;
import com.github.kaktushose.jda.commands.embeds.EmbedCache;
import com.google.inject.Inject;
import io.github.kaktushose.notruf.embeds.EmbedColors;
import io.github.kaktushose.notruf.moderation.ModerationService;
import io.github.kaktushose.notruf.permissions.BotPermissions;
import io.github.kaktushose.notruf.serverlog.ModerationEvents;
import io.github.kaktushose.notruf.serverlog.Serverlog;
import net.dv8tion.jda.api.Permission;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Interaction
public class DeleteCommand {

    private static final Logger log = LoggerFactory.getLogger(DeleteCommand.class);

    @Inject
    private EmbedCache embedCache;

    @Inject
    private Serverlog serverlog;

    @CommandConfig(enabledFor = Permission.BAN_MEMBERS)
    @Command(value = "mod delete", desc = "Löscht eine Moderationshandlung")
    @Permissions(BotPermissions.MODERATION_DELETE)
    public void deleteModeration(CommandEvent event, @Param("Die ID der Moderationshandlung, die gelöscht werden soll") long moderationId) {
        var moderation = ModerationService.getModerationAct(moderationId);

        if (moderation.isEmpty()) {
            event.reply(embedCache.getEmbed("deletionFailed").injectValue("id", moderationId).injectValue("color", EmbedColors.ERROR));
            return;
        }

        if (!moderation.get().reverted()) {
            moderation.get().revert(event.getGuild(), embedCache, event.getUser(), null);
        }

        log.info("Moderation act {} has been deleted by {}", moderationId, event.getUser().getName());
        ModerationService.deleteModerationAct(moderationId);
        serverlog.onEvent(ModerationEvents.Deleted(event.getJDA(), event.getGuild(), moderation.get(), event.getUser()));
        event.reply(embedCache.getEmbed("deletionSuccessful").injectValue("id", moderationId).injectValue("color", EmbedColors.SUCCESS));
    }

}
