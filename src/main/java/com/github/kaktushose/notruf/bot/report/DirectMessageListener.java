package com.github.kaktushose.notruf.bot.report;

import com.github.kaktushose.jda.commands.data.EmbedCache;
import com.github.kaktushose.jda.commands.data.EmbedDTO;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.entities.channel.ChannelType;
import net.dv8tion.jda.api.entities.channel.concrete.TextChannel;
import net.dv8tion.jda.api.entities.channel.concrete.ThreadChannel;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.utils.AttachedFile;
import net.dv8tion.jda.api.utils.messages.MessageCreateBuilder;
import net.dv8tion.jda.api.utils.messages.MessageEditBuilder;
import org.jetbrains.annotations.NotNull;

import java.time.Instant;

public class DirectMessageListener extends ListenerAdapter {

    private final EmbedCache embedCache;
    private final TextChannel reportChannel;

    public DirectMessageListener(Guild guild, EmbedCache embedCache) {
        reportChannel = guild.getChannelById(TextChannel.class, 545967082253189121L);
        this.embedCache = embedCache;
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getChannelType() != ChannelType.PRIVATE) {
            return;
        }

        Message message = event.getMessage();
        User author = message.getAuthor();
        EmbedDTO embedDTO = embedCache.getEmbed("report");


        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.setEmbeds(embedDTO.injectValue("id", message.getId())
                        .injectValue("report", message.getContentDisplay())
                        .toEmbedBuilder()
                        .setTimestamp(Instant.now())
                        .build())
                .addActionRow(
                        Button.primary(String.format("contact-%s", author.getId()), "Thread eröffnen")
                                .withEmoji(Emoji.fromFormatted("\uD83D\uDCDD")),
                        Button.success(String.format("done-%s", author.getId()), "Erledigt").withEmoji(Emoji.fromFormatted("✅")),
                        Button.danger("delete", "Report löschen").withEmoji(Emoji.fromFormatted("\uD83D\uDDD1"))
                );

        event.getChannel().sendMessage(embedCache.getEmbed("reportConfirm").toMessageCreateData()).queue();

        reportChannel.sendMessage(builder.build()).queue(success -> {
            for (Message.Attachment attachment : message.getAttachments()) {
                attachment.getProxy().download().thenAccept(download ->
                        success.editMessageAttachments(AttachedFile.fromData(download, attachment.getFileName())).queue()
                );
            }
        });
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String componentId = event.getComponentId();

        if (componentId.equals("delete")) {
            event.getMessage().delete().queue();
            event.reply("*Report gelöscht*").setEphemeral(true).queue();
            return;
        }

        if (componentId.startsWith("done")) {
            String authorId = componentId.split("-")[1];

            MessageEditBuilder builder = MessageEditBuilder.fromMessage(event.getMessage());
            EmbedBuilder embed = EmbedBuilder.fromData(builder.getEmbeds().get(0).toData());

            builder = new MessageEditBuilder();
            builder.setActionRow(Button.primary(String.format("contact-%s", authorId), "Thread eröffnen")
                    .withEmoji(Emoji.fromFormatted("\uD83D\uDCDD")));
            embed.setColor(0x67c94f);
            embed.getFields().clear();
            embed.addField("Status", "✅ bearbeitet", false);
            builder.setEmbeds(embed.build());

            event.editMessage(builder.build()).queue();

            reportChannel.getJDA().retrieveUserById(authorId).flatMap(User::openPrivateChannel).flatMap(channel ->
                    channel.sendMessage(embedCache.getEmbed("reportDone").toMessageCreateData())
            ).queue();
            return;
        }

        if (componentId.startsWith("contact")) {
            String authorId = componentId.split("-")[1];
            event.getMessage().createThreadChannel(String.format("Bug-Report-%s", event.getMessage().getId()))
                    .setAutoArchiveDuration(ThreadChannel.AutoArchiveDuration.TIME_1_WEEK)
                    .queue(thread -> thread.addThreadMemberById(authorId).flatMap(empty ->
                            thread.sendMessage(embedCache.getEmbed("threadOpen")
                                    .injectValue("user", String.format("<@%s>", authorId))
                                    .toMessageCreateData()
                            )
                    ).queue());

            MessageEditBuilder builder = MessageEditBuilder.fromMessage(event.getMessage());
            EmbedBuilder embed = EmbedBuilder.fromData(builder.getEmbeds().get(0).toData());
            embed.setColor(0x67c94f);
            embed.getFields().clear();
            embed.addField("Status", "✅ bearbeitet", false);

            builder = new MessageEditBuilder().setComponents().setEmbeds(embed.build());
            event.editMessage(builder.build()).queue();
        }
    }
}
