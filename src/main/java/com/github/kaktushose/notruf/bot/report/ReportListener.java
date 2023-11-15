package com.github.kaktushose.notruf.bot.report;

import com.github.kaktushose.jda.commands.data.EmbedCache;
import com.github.kaktushose.jda.commands.data.EmbedDTO;
import com.github.kaktushose.notruf.bot.NotrufBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.MessageEmbed;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ReportListener extends ListenerAdapter {

    private final ScheduledExecutorService executorService;
    private final Map<Long, Long> reportCache;
    private final EmbedCache embedCache;
    private final TextChannel reportChannel;

    public ReportListener(TextChannel reportChannel, NotrufBot.EmbedCacheContainer container) {
        this.reportChannel = reportChannel;
        this.embedCache = container.germanCache();
        ;
        reportCache = new HashMap<>();
        executorService = new ScheduledThreadPoolExecutor(5);
    }

    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if (event.getAuthor().isBot()) {
            return;
        }

        if (event.getChannelType() != ChannelType.PRIVATE) {
            return;
        }

        long authorId = event.getAuthor().getIdLong();
        if (reportCache.containsKey(authorId)) {
            updateReport(event, reportCache.get(authorId));
        } else {
            createReport(event, authorId);
        }
    }

    private void updateReport(MessageReceivedEvent event, long reportMessageId) {
        reportChannel.retrieveMessageById(reportMessageId).queue(message -> {
            MessageEditBuilder builder = MessageEditBuilder.fromMessage(message);
            MessageEmbed oldEmbed = message.getEmbeds().get(0);
            EmbedBuilder newEmbed = new EmbedBuilder(oldEmbed);

            String report = String.format("%s\n%s", oldEmbed.getDescription(), event.getMessage().getContentDisplay());
            if (report.length() > MessageEmbed.DESCRIPTION_MAX_LENGTH) {
                report = report.substring(0, MessageEmbed.DESCRIPTION_MAX_LENGTH - 4) + "...";
            }
            newEmbed.setDescription(report);
            builder.setEmbeds(newEmbed.build());

            message.editMessage(builder.build()).queue(success -> addAttachments(success, event.getMessage().getAttachments()));
        });

        event.getChannel().sendMessage(embedCache.getEmbed("reportUpdate").toMessageCreateData()).queue();
    }

    private void createReport(MessageReceivedEvent event, long authorId) {
        Message message = event.getMessage();
        EmbedDTO embedDTO = embedCache.getEmbed("report");

        String report = message.getContentDisplay();
        if (report.length() > MessageEmbed.DESCRIPTION_MAX_LENGTH) {
            report = report.substring(0, MessageEmbed.DESCRIPTION_MAX_LENGTH - 4) + "...";
        }

        MessageCreateBuilder builder = new MessageCreateBuilder();
        builder.setEmbeds(embedDTO.injectValue("id", message.getId())
                        .injectValue("report", report)
                        .toEmbedBuilder()
                        .setTimestamp(Instant.now())
                        .build())
                .addActionRow(
                        Button.primary(String.format("contact-%d", authorId), "Thread eröffnen")
                                .withEmoji(Emoji.fromFormatted("\uD83D\uDCDD")),
                        Button.success(String.format("done-%d", authorId), "Erledigt").withEmoji(Emoji.fromFormatted("✅")),
                        Button.danger(String.format("delete-%d", authorId), "Report löschen").withEmoji(Emoji.fromFormatted("\uD83D\uDDD1"))
                );

        EmbedDTO confirm = message.getAttachments().isEmpty() ? embedCache.getEmbed("reportConfirmNoAttachments") : embedCache.getEmbed("reportConfirm");
        event.getChannel().sendMessage(confirm.toMessageCreateData()).queue();

        reportChannel.sendMessage(builder.build()).queue(success -> {
            reportCache.put(authorId, success.getIdLong());
            executorService.schedule(() -> reportCache.remove(authorId), 1, TimeUnit.HOURS);

            addAttachments(success, message.getAttachments());
        });
    }

    private void addAttachments(Message message, List<Message.Attachment> attachments) {
        for (Message.Attachment attachment : attachments) {
            attachment.getProxy().download().thenAccept(download ->
                    message.editMessageAttachments(AttachedFile.fromData(download, attachment.getFileName())).queue()
            );
        }
    }

    @Override
    public void onButtonInteraction(@NotNull ButtonInteractionEvent event) {
        String componentId = event.getComponentId();

        if (componentId.startsWith("delete")) {
            String authorId = componentId.split("-")[1];
            event.getMessage().delete().queue();
            event.reply("*Report gelöscht*").setEphemeral(true).queue();
            reportCache.remove(Long.valueOf(authorId));
            return;
        }

        if (componentId.startsWith("done")) {
            String authorId = componentId.split("-")[1];

            MessageEditBuilder builder = MessageEditBuilder.fromMessage(event.getMessage());
            EmbedBuilder embed = new EmbedBuilder(builder.getEmbeds().get(0));

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

            reportCache.remove(Long.valueOf(authorId));
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
            EmbedBuilder embed = new EmbedBuilder(builder.getEmbeds().get(0));
            embed.setColor(0x67c94f);
            embed.getFields().clear();
            embed.addField("Status", "✅ bearbeitet", false);

            builder = new MessageEditBuilder().setComponents().setEmbeds(embed.build());
            event.editMessage(builder.build()).queue();
        }
    }
}
