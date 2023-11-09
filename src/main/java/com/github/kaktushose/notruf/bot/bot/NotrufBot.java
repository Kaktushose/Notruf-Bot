package com.github.kaktushose.notruf.bot.bot;


import com.github.kaktushose.jda.commands.JDACommands;
import com.github.kaktushose.jda.commands.data.EmbedCache;
import com.github.kaktushose.notruf.bot.Bootstrapper;
import com.github.kaktushose.notruf.bot.command.ResetCommand;
import com.github.kaktushose.notruf.bot.command.RoleService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.interactions.DiscordLocale;
import net.dv8tion.jda.api.interactions.commands.localization.ResourceBundleLocalizationFunction;

import javax.security.auth.login.LoginException;

public class NotrufBot {

    private final Config config;
    private EmbedCache embedCache;
    private RoleService roleService;

    public NotrufBot(Config config) {
        this.config = config;
    }

    public void start() throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(config.getToken()).build().awaitReady();

        embedCache = new EmbedCache("./embeds.json");
        roleService = new RoleService(config, jda.getGuildById(config.getGuildId()));

        JDACommands.start(jda,
                Bootstrapper.class,
                ResourceBundleLocalizationFunction.fromBundles("commands", DiscordLocale.GERMAN).build(),
                "com.github.kaktushose.notruf.bot");
    }

    public EmbedCache getEmbedCache() {
        return embedCache;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
