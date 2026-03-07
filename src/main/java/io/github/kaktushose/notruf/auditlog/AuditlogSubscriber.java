package io.github.kaktushose.notruf.auditlog;

import io.github.kaktushose.notruf.auditlog.AuditlogService.AuditlogCreateData;
import io.github.kaktushose.notruf.auditlog.lifecycle.BotEvent;
import io.github.kaktushose.notruf.auditlog.lifecycle.Subscriber;
import io.github.kaktushose.notruf.auditlog.lifecycle.events.*;
import io.github.kaktushose.notruf.auditlog.lifecycle.events.*;
import io.github.kaktushose.notruf.auditlog.model.AuditlogPayload;
import io.github.kaktushose.notruf.auditlog.model.AuditlogPayload.*;

public class AuditlogSubscriber implements Subscriber<BotEvent> {

    private final AuditlogService service;

    public AuditlogSubscriber(AuditlogService service) {
        this.service = service;
    }

    @Override
    public void accept(BotEvent botEvent) {
        AuditlogPayload payload = switch (botEvent) {
            case NoteEvent event -> new NoteCreate(event.note());
            case PermissionsEvent event -> new PermissionsUpdate(event.oldPermissions(), event.newPermissions());
            case ConfigEvent event -> new ConfigUpdate(event.config(), event.oldValue(), event.newValue());
            case SlowmodeEvent event -> new SlowmodePayload(event.durationMillis());
            case ModerationEvent.Create event -> new ModerationCreate(event.act());
            case ModerationEvent.Revert event -> new ModerationRevert(event.act(), event.automatic());
            case ModerationEvent.Delete event -> new ModerationDelete(event.act().id());
            case MessagePurgeEvent event -> new MessagePurge(event);
            default -> null;
        };

        service.create(new AuditlogCreateData(botEvent.type(), botEvent.issuer(), botEvent.target(), payload));
    }
}
