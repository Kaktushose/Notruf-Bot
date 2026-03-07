package io.github.kaktushose.notruf.moderation.commands.create;

import com.google.inject.Inject;
import io.github.kaktushose.notruf.Helpers;
import io.github.kaktushose.notruf.Replies;
import io.github.kaktushose.notruf.duration.DurationMax;
import io.github.kaktushose.notruf.messagelink.MessageLink;
import io.github.kaktushose.notruf.moderation.act.ModerationActService;
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

import java.time.Duration;
import java.time.temporal.ChronoUnit;


@Interaction
@Bundle("create")
@Permissions(BotPermissions.MODERATION_CREATE)
public class TimeoutCommand extends CreateCommand {

    private final ModerationActService actService;

    @Inject
    public TimeoutCommand(ModerationActService actService) {
        this.actService = actService;
    }

    @Lock("target")
    @Command("mod timeout")
    public void timeoutMember(
            CommandEvent event,
            Member target,
            @DurationMax(amount = 28, unit = ChronoUnit.DAYS) Duration until,
            @Param(optional = true, type = OptionType.INTEGER) RuleParagraph paragraph,
            @Param(optional = true) MessageLink messageLink
    ) {

        if (actService.isTimeOuted(target)) {
            event.reply(Replies.error("already-timeout"));
            return;
        }

        event.kv().put(BUILDER, ModerationActBuilder.timeout(target, event.getUser())
                .duration(until)
                .paragraph(paragraph)
                .messageReference(Helpers.retrieveMessage(event, messageLink)));

        replyModal(event, "Timeout");
    }
}
