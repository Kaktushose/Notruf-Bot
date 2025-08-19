package io.github.kaktushose.notruf.serverlog;

import com.github.kaktushose.jda.commands.embeds.EmbedCache;
import io.github.kaktushose.notruf.BotEvent;
import io.github.kaktushose.notruf.config.ConfigService;
import io.github.kaktushose.notruf.config.bot.BotConfig;
import net.dv8tion.jda.api.entities.Guild;

public record Serverlog(Guild guild, EmbedCache embedCache) {

    public void onEvent(BotEvent event) {
        var embed = event.embedSupplier().apply(embedCache);
        var serverlogChannel = event.jda().getTextChannelById(ConfigService.get(BotConfig.SERVERLOG_KANAL).orElse("0"));

        if (serverlogChannel != null) {
            serverlogChannel.sendMessageEmbeds(embed).queue();
        }
    }

}
