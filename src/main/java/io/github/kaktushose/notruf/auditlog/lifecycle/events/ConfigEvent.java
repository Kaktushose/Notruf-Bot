package io.github.kaktushose.notruf.auditlog.lifecycle.events;

import io.github.kaktushose.notruf.auditlog.AuditlogService.UnresolvedSnowflake;
import io.github.kaktushose.notruf.auditlog.lifecycle.BotEvent;
import io.github.kaktushose.notruf.auditlog.model.AuditlogType;
import io.github.kaktushose.notruf.config.ConfigService.BotConfig;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.UserSnowflake;

public record ConfigEvent(
        AuditlogType type,
        UserSnowflake issuer,
        BotConfig config,
        String oldValue,
        String newValue
) implements BotEvent {

    @Override
    public ISnowflake target() {
        return new UnresolvedSnowflake(0);
    }
}
