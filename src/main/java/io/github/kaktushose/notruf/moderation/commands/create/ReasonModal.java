package io.github.kaktushose.notruf.moderation.commands.create;

import com.google.inject.Inject;
import io.github.kaktushose.notruf.moderation.act.ModerationActService;
import io.github.kaktushose.notruf.moderation.lock.ModerationActLock;
import io.github.kaktushose.notruf.moderation.act.model.ModerationAct;
import io.github.kaktushose.notruf.moderation.act.model.ModerationActBuilder;
import io.github.kaktushose.notruf.util.SeparatedContainer;
import io.github.kaktushose.jdac.annotations.i18n.Bundle;
import io.github.kaktushose.jdac.annotations.interactions.Interaction;
import io.github.kaktushose.jdac.annotations.interactions.Modal;
import io.github.kaktushose.jdac.dispatching.events.interactions.ModalEvent;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;

import static io.github.kaktushose.notruf.Replies.SUCCESS;
import static io.github.kaktushose.notruf.moderation.commands.create.CreateCommand.BUILDER;
import static io.github.kaktushose.notruf.moderation.commands.create.CreateCommand.REASON_ID;
import static io.github.kaktushose.jdac.message.placeholder.Entry.entry;

@Interaction
@Bundle("create")
public class ReasonModal {

    private final ModerationActLock moderationActLock;
    private final ModerationActService actService;

    @Inject
    public ReasonModal(ModerationActLock moderationActLock, ModerationActService actService) {
        this.moderationActLock = moderationActLock;
        this.actService = actService;
    }

    @Modal("reason-title")
    public void onModerate(ModalEvent event) {
        ModerationAct act = event.kv().get(BUILDER, ModerationActBuilder.class)
                .orElseThrow()
                .reason(event.value(REASON_ID).getAsString())
                .execute(event, actService);

        SeparatedContainer container = new SeparatedContainer(
                TextDisplay.of("executed"),
                Separator.createDivider(Separator.Spacing.SMALL),
                entry("type", act.type().localized(event.getUserLocale())),
                entry("id", act.id()),
                entry("target", act.user()),
                entry("reason", act.reason())
        ).withAccentColor(SUCCESS);
        act.revokeAt().ifPresent(it ->
                container.append(TextDisplay.of("executed.until"), entry("until", it))
        );
        act.paragraph().ifPresent(it ->
                container.append(TextDisplay.of("executed.paragraph"), entry("paragraph", it.shortDisplay()))
        );
        act.messageReference().ifPresent(it ->
                container.append(TextDisplay.of("executed.reference"), entry("message", it.content()))
        );
        event.reply(container);

        moderationActLock.unlock(act.user().getIdLong());
    }
}
