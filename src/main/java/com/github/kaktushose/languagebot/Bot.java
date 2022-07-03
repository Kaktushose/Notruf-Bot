package com.github.kaktushose.languagebot;


import com.github.kaktushose.jda.commands.JDACommands;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class Bot {

    private final Config config;
    private JDA jda;
    private JDACommands jdaCommands;

    public Bot(Config config) {
        this.config = config;
    }

    public void start() throws LoginException, InterruptedException {
        jda = JDABuilder.createDefault(config.getToken()).build().awaitReady();

        jdaCommands = JDACommands
                .slash(jda, Bootstrapper.class, "com.github.kaktushose.languagebot")
                .guilds(config.getGuildId())
                .startGuild();
    }

    public void stop() {
        jdaCommands.shutdown();
        jda.shutdown();
        System.exit(0);
    }


}
