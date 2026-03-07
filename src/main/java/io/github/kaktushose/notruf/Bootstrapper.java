package io.github.kaktushose.notruf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrapper {

    private final static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    static void main() {
        long startTime = System.currentTimeMillis();
        Thread.currentThread().setName("Bot");
        log.info("Starting Notruf-Bot...");
        try {
            NotrufBot.start(System.getenv("BOT_GUILD"), System.getenv("BOT_TOKEN"));
        } catch (Exception e) {
            log.error("Failed to start!", e);
            System.exit(1);
        }
        log.info("Successfully started Notruf-Bot! Took {} ms", System.currentTimeMillis() - startTime);
    }
}
