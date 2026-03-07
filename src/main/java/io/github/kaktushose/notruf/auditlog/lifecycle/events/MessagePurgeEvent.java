package io.github.kaktushose.notruf.auditlog.lifecycle.events;

import io.github.kaktushose.notruf.auditlog.lifecycle.BotEvent;
import io.github.kaktushose.notruf.auditlog.model.AuditlogType;
import net.dv8tion.jda.api.entities.UserSnowflake;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.jspecify.annotations.Nullable;

public record MessagePurgeEvent(
        UserSnowflake issuer,
        MessageChannel target,
        long pivotMessageId,
        @Nullable Integer amount
        ) implements BotEvent {

    @Override
    public AuditlogType type() {
        return AuditlogType.MESSAGE_PURGE;
    }
}
