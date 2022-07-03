package com.github.kaktushose.languagebot.bot;


import com.github.kaktushose.jda.commands.JDACommands;
import com.github.kaktushose.jda.commands.embeds.EmbedCache;
import com.github.kaktushose.languagebot.Bootstrapper;
import com.github.kaktushose.languagebot.command.RoleService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;

import javax.security.auth.login.LoginException;

public class LanguageBot {

    private final Config config;
    private EmbedCache embedCache;
    private RoleService roleService;

    public LanguageBot(Config config) {
        this.config = config;
    }

    public void start() throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(config.getToken()).build().awaitReady();

        embedCache = new EmbedCache("./embeds.json");
        roleService = new RoleService(config, jda.getGuildById(config.getGuildId()));

        JDACommands.slash(jda, Bootstrapper.class, "com.github.kaktushose.languagebot")
                .guilds(config.getGuildId())
                .startGuild();
    }

    public EmbedCache getEmbedCache() {
        return embedCache;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
