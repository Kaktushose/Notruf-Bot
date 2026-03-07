package io.github.kaktushose.notruf.auditlog.lifecycle;

import io.github.kaktushose.notruf.auditlog.model.AuditlogType;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.UserSnowflake;

public interface BotEvent {

    AuditlogType type();

    UserSnowflake issuer();

    ISnowflake target();

}
