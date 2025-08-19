package io.github.kaktushose.notruf;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Bootstrapper {

    private final static Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    /**
     * Main entry point of the Bot
     */
    public static void main(String[] args) {
        Thread.currentThread().setName("Bot");
        long startTime = System.currentTimeMillis();
        try {
            log.info("Starting Notruf Bot...");
            NotrufBot bot = NotrufBot.start(System.getenv("BOT_GUILD"), System.getenv("BOT_TOKEN"));
            Thread.setDefaultUncaughtExceptionHandler((_, e) -> log.error("An uncaught exception has occurred!", e));
            Runtime.getRuntime().addShutdownHook(new Thread(bot::shutdown));
            log.info("Successfully started Notruf Bot! Took {} ms", System.currentTimeMillis() - startTime);
        } catch (Exception e) {
            log.error("Failed to start!", e);
            System.exit(1);
        }
    }
}
