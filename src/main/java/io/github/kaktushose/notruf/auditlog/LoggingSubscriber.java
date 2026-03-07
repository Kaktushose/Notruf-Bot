package io.github.kaktushose.notruf.auditlog;

import io.github.kaktushose.notruf.auditlog.lifecycle.BotEvent;
import io.github.kaktushose.notruf.auditlog.lifecycle.Subscriber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggingSubscriber implements Subscriber<BotEvent> {

    private static final Logger log = LoggerFactory.getLogger(LoggingSubscriber.class);

    @Override
    public void accept(BotEvent event) {
        log.info("User {} performed {} on target {}", event.issuer(), event.type().name(), event.target());
    }
}
