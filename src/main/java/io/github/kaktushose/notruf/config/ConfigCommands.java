package io.github.kaktushose.notruf.config;

import com.google.inject.Inject;
import io.github.kaktushose.jdac.annotations.interactions.*;
import io.github.kaktushose.notruf.Replies;
import io.github.kaktushose.jdac.annotations.i18n.Bundle;
import io.github.kaktushose.jdac.dispatching.events.interactions.CommandEvent;
import io.github.kaktushose.notruf.config.ConfigService.BotConfig;
import io.github.kaktushose.notruf.permissions.BotPermissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

import static io.github.kaktushose.jdac.message.placeholder.Entry.entry;

@Bundle("config")
@Interaction("config")
@Permissions(BotPermissions.ADMINISTRATOR)
@CommandConfig(enabledFor = Permission.ADMINISTRATOR)
public class ConfigCommands {

    private final ConfigService configService;

    @Inject
    public ConfigCommands(ConfigService configService) {
        this.configService = configService;
    }

    @Command("set serverlog-kanal")
    public void setServerlogConfig(CommandEvent event, TextChannel channel) {
        onConfigSet(event, BotConfig.SERVERLOG_KANAL, channel.getId());
    }

    @Command("set opt-out")
    public void setOptOutConfig(CommandEvent event, @Choices({"german", "english"}) String language, Role role) {
        switch (language) {
            case "german" -> onConfigSet(event, BotConfig.OPT_OUT_GERMAN, role.getId());
            case "english" -> onConfigSet(event, BotConfig.OPT_OUT_ENGLISH, role.getId());
        }
    }

    private void onConfigSet(CommandEvent event, BotConfig config, String value) {
        configService.set(config, value, event.getUser());
        event.reply(Replies.success("config-update"), entry("key", config.toString()), entry("value", value));
    }

    @Command("list")
    public void listConfig(CommandEvent event) {
        var serverlogChannel = configService.get(BotConfig.SERVERLOG_KANAL);

        event.reply(
                Replies.standard("config-list"),
                entry("serverlog", serverlogChannel.orElse("no-value-set")),
                entry("german", configService.get(BotConfig.OPT_OUT_GERMAN).orElse("no-value-set")),
                entry("english", configService.get(BotConfig.OPT_OUT_ENGLISH).orElse("no-value-set"))
        );
    }
}
