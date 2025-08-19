package io.github.kaktushose.notruf.config;

import com.github.kaktushose.jda.commands.annotations.interactions.*;
import com.google.inject.Inject;
import com.github.kaktushose.jda.commands.dispatching.events.interactions.CommandEvent;
import com.github.kaktushose.jda.commands.embeds.EmbedCache;
import io.github.kaktushose.notruf.config.bot.BotConfig;
import io.github.kaktushose.notruf.embeds.EmbedColors;
import io.github.kaktushose.notruf.permissions.BotPermissions;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;

@Interaction
@Permissions(BotPermissions.ADMINISTRATOR)
@CommandConfig(enabledFor = Permission.ADMINISTRATOR)
public class ConfigCommands {

    @Inject
    private EmbedCache embedCache;

    @Command(value = "config set serverlog-kanal", desc = "Setzt eine Konfigurations-Variable")
    public void setConfig(CommandEvent event, @Param("Der Text-Kanal in denen die Bot-Logs gesendet werden sollen.") TextChannel channel) {
        onConfigSet(event, BotConfig.SERVERLOG_KANAL, channel.getId());
    }

    void onConfigSet(CommandEvent event, BotConfig config, String value) {
        ConfigService.set(config, value);
        event.reply(embedCache.getEmbed("configSet").injectValue("key", config).injectValue("value", value).injectValue("color", EmbedColors.SUCCESS));
    }

    @Command(value = "config list", desc = "Listet alle Konfigurations-Variablen auf")
    public void listConfig(CommandEvent event) {
        var configs = BotConfig.getConfigs();

        var embed = embedCache.getEmbed("configList").injectValue("color", EmbedColors.DEFAULT).toEmbedBuilder();

        configs.forEach(config -> {
            var value = ConfigService.get(config);
            embed.addField(config.toString(), value.orElse("Nicht gesetzt"), false);
        });

        event.jdaEvent().replyEmbeds(embed.build()).queue();
    }

}
