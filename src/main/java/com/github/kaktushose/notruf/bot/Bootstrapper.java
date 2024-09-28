package com.github.kaktushose.notruf.bot;

import com.github.kaktushose.jda.commands.annotations.Produces;
import com.github.kaktushose.notruf.bot.language.RoleService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;

public class Bootstrapper {

    private static final Logger log = LoggerFactory.getLogger(Bootstrapper.class);
    private static NotrufBot bot;

    public static void main(String[] args) {
        log.debug("Starting bot...");
        Config config = new Config(
                System.getenv("BOT_TOKEN"),
                System.getenv("GUILD_ID"),
                System.getenv("REPORT_CHANNEL_ID"),
                System.getenv("REPORT_CATEGORY_ID"),
                System.getenv("ENGLISH_ROLE_ID"),
                System.getenv("GERMAN_ROLE_ID")
        );

        bot = new NotrufBot(config);
        try {
            bot.start();
        } catch (LoginException | InterruptedException e) {
            log.error("Unable to start the bot!", e);
            System.exit(1);
            return;
        }

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> log.error("An uncaught exception has occurred!", e));

        log.info("Successfully started bot!");
    }

    @Produces
    public NotrufBot.EmbedCacheContainer getEmbedCacheContainer() {
        return bot.getEmbedCacheContainer();
    }

    @Produces
    public RoleService getRoleService() {
        return bot.getRoleService();
    }
}
