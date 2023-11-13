package com.github.kaktushose.notruf.bot;


import com.github.kaktushose.jda.commands.JDACommands;
import com.github.kaktushose.jda.commands.data.EmbedCache;
import com.github.kaktushose.notruf.bot.report.ReportListener;
import com.github.kaktushose.notruf.bot.language.RoleService;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.component.GenericComponentInteractionCreateEvent;
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

    @SuppressWarnings("ConstantConditions")
    public void start() throws LoginException, InterruptedException {
        JDA jda = JDABuilder.createDefault(config.getToken()).build().awaitReady();

        Guild guild = jda.getGuildById(config.getGuildId());
        embedCache = new EmbedCache("./embeds.json");
        roleService = new RoleService(config, guild);

        jda.addEventListener(new ReportListener(guild.getTextChannelById(config.getReportChannelId()), embedCache));

        JDACommands jdaCommands = JDACommands.start(jda,
                Bootstrapper.class,
                ResourceBundleLocalizationFunction.fromBundles("commands", DiscordLocale.GERMAN).build(),
                "com.github.kaktushose.notruf.bot");

        // we handle components on our own
        jdaCommands.getDispatcherSupervisor().unregister(GenericComponentInteractionCreateEvent.class);
    }

    public EmbedCache getEmbedCache() {
        return embedCache;
    }

    public RoleService getRoleService() {
        return roleService;
    }
}
