package com.github.kaktushose.languagebot;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.security.auth.login.LoginException;
import java.io.FileReader;
import java.io.IOException;

public class Bootstrapper {

    private static final Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    public static void main(String[] args) {
        log.debug("Starting bot...");
        Config config;
        try (FileReader reader = new FileReader("./config.json")) {
            config = new Gson().fromJson(reader, Config.class);
            log.debug("Loaded config");
        } catch (IOException e) {
            log.error("Unable to load config file!", e);
            System.exit(1);
            return;
        }

        Bot bot = new Bot(config);
        try {
            bot.start();
        } catch (LoginException | InterruptedException e) {
            log.error("Unable to start the bot!", e);
            System.exit(1);
            return;
        }

        Thread.setDefaultUncaughtExceptionHandler((t, e) -> log.error("An uncaught exception has occurred!", e));
        Runtime.getRuntime().addShutdownHook(new Thread(bot::stop));

        log.info("Successfully started bot!");
    }
}
