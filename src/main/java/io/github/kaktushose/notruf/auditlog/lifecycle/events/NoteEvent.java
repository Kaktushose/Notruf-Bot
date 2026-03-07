package io.github.kaktushose.notruf.auditlog.lifecycle.events;

import io.github.kaktushose.notruf.auditlog.lifecycle.BotEvent;
import io.github.kaktushose.notruf.auditlog.model.AuditlogType;
import io.github.kaktushose.notruf.notes.NotesService.Note;
import net.dv8tion.jda.api.entities.UserSnowflake;

public record NoteEvent(
        AuditlogType type, UserSnowflake issuer, UserSnowflake target, Note note
) implements BotEvent { }
