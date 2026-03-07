package io.github.kaktushose.notruf.auditlog.lifecycle;

public interface Subscriber<T extends BotEvent> {

    void accept(T event);

}
