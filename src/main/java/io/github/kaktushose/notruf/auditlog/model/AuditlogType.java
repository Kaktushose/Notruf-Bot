package io.github.kaktushose.notruf.auditlog.model;

import io.github.kaktushose.notruf.auditlog.model.AuditlogPayload.*;
import org.jspecify.annotations.Nullable;

import java.util.Arrays;
import java.util.stream.Collectors;

public enum AuditlogType {

    MODERATION_CREATE(ModerationCreate.class),
    MODERATION_REVERT(ModerationRevert.class),
    MODERATION_DELETE(ModerationDelete.class),
    MESSAGE_PURGE(MessagePurge.class),
    NOTE_CREATE(NoteCreate.class),
    NOTE_DELETE(NoteDelete.class),
    PERMISSIONS_USER_UPDATE(PermissionsUpdate.class),
    PERMISSIONS_ROLE_UPDATE(PermissionsUpdate.class),
    CONFIG_UPDATE(ConfigUpdate.class),
    SLOWMODE_UPDATE(SlowmodePayload.class);

    private final @Nullable Class<? extends AuditlogPayload> payloadType;

    AuditlogType() {
        this(null);
    }

    AuditlogType(@Nullable Class<? extends AuditlogPayload> payloadType) {
        this.payloadType = payloadType;
    }

    public @Nullable Class<? extends AuditlogPayload> payloadType() {
        return payloadType;
    }

    @Override
    public String toString() {
        return Arrays.stream(name().toLowerCase().split("_"))
                .map(it -> it.substring(0, 1).toUpperCase() + it.substring(1))
                .collect(Collectors.joining(" "));
    }
}
