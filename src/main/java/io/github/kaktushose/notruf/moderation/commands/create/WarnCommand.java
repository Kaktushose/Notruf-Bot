package io.github.kaktushose.notruf.moderation.commands.create;

import io.github.kaktushose.notruf.Helpers;
import io.github.kaktushose.notruf.messagelink.MessageLink;
import io.github.kaktushose.notruf.moderation.lock.Lock;
import io.github.kaktushose.notruf.moderation.act.model.ModerationActBuilder;
import io.github.kaktushose.notruf.permissions.BotPermissions;
import io.github.kaktushose.notruf.rules.RuleService.RuleParagraph;
import io.github.kaktushose.jdac.annotations.i18n.Bundle;
import io.github.kaktushose.jdac.annotations.interactions.Command;
import io.github.kaktushose.jdac.annotations.interactions.Interaction;
import io.github.kaktushose.jdac.annotations.interactions.Param;
import io.github.kaktushose.jdac.annotations.interactions.Permissions;
import io.github.kaktushose.jdac.dispatching.events.interactions.CommandEvent;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.interactions.commands.OptionType;

@Interaction
@Bundle("create")
@Permissions(BotPermissions.MODERATION_CREATE)
public class WarnCommand extends CreateCommand {

    @Lock("target")
    @Command("mod warn")
    public void warnMember(
            CommandEvent event,
            Member target,
            @Param(optional = true, type = OptionType.INTEGER) RuleParagraph paragraph,
            @Param(optional = true) MessageLink messageLink
    ) {
        event.kv().put(BUILDER, ModerationActBuilder.warn(target, event.getUser())
                .paragraph(paragraph)
                .messageReference(Helpers.retrieveMessage(event, messageLink)));

        replyModal(event, "Warn");
    }
}
