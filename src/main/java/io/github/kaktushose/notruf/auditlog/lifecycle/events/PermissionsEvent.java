package io.github.kaktushose.notruf.auditlog.lifecycle.events;

import io.github.kaktushose.notruf.auditlog.lifecycle.BotEvent;
import io.github.kaktushose.notruf.auditlog.model.AuditlogType;
import net.dv8tion.jda.api.entities.ISnowflake;
import net.dv8tion.jda.api.entities.UserSnowflake;

public record PermissionsEvent(
        AuditlogType type, UserSnowflake issuer, ISnowflake target, int oldPermissions, int newPermissions
) implements BotEvent { }
