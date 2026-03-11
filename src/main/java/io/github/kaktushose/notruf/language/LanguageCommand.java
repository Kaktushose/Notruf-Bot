package io.github.kaktushose.notruf.language;

import com.google.inject.Inject;
import io.github.kaktushose.jdac.annotations.i18n.Bundle;
import io.github.kaktushose.jdac.annotations.interactions.Button;
import io.github.kaktushose.jdac.annotations.interactions.Command;
import io.github.kaktushose.jdac.annotations.interactions.Interaction;
import io.github.kaktushose.jdac.annotations.interactions.ReplyConfig;
import io.github.kaktushose.jdac.dispatching.events.ReplyableEvent;
import io.github.kaktushose.jdac.dispatching.events.interactions.CommandEvent;
import io.github.kaktushose.jdac.dispatching.events.interactions.ComponentEvent;
import io.github.kaktushose.jdac.dispatching.reply.Component;
import io.github.kaktushose.notruf.config.ConfigService;
import io.github.kaktushose.notruf.config.ConfigService.BotConfig;
import io.github.kaktushose.notruf.util.SeparatedContainer;
import net.dv8tion.jda.api.components.buttons.ButtonStyle;
import net.dv8tion.jda.api.components.section.Section;
import net.dv8tion.jda.api.components.separator.Separator;
import net.dv8tion.jda.api.components.separator.Separator.Spacing;
import net.dv8tion.jda.api.components.textdisplay.TextDisplay;
import net.dv8tion.jda.api.entities.Guild;

@Bundle("language")
@Interaction("sprachbereich")
@ReplyConfig(ephemeral = true, keepComponents = false)
public class LanguageCommand {

    private final ConfigService configService;

    @Inject
    public LanguageCommand(ConfigService configService) {
        this.configService = configService;
    }

    @Command
    public void onCommand(CommandEvent event) {
        reply(event);
    }

    @Button(value = "show", style = ButtonStyle.SUCCESS)
    public void onShowGerman(ComponentEvent event) {
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(germanRole())).complete();
        reply(event);
    }

    @Button(value = "hide", style = ButtonStyle.DANGER)
    public void onHideGerman(ComponentEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(germanRole())).complete();
        reply(event);
    }

    @Button(value = "show", style = ButtonStyle.SUCCESS)
    public void onShowEnglish(ComponentEvent event) {
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(englishRole())).complete();
        reply(event);
    }

    @Button(value = "hide", style = ButtonStyle.DANGER)
    public void onHideEnglish(ComponentEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(englishRole())).complete();
        reply(event);
    }

    private void reply(ReplyableEvent<?> event) {
        Guild guild = event.getGuild();
        var roles = event.getMember().getUnsortedRoles();
        String german = roles.contains(guild.getRoleById(germanRole())) ? "onShowGerman" : "onHideGerman";
        String english = roles.contains(guild.getRoleById(englishRole())) ? "onShowEnglish" : "onHideEnglish";
        event.reply(
                new SeparatedContainer(TextDisplay.of("language"), Separator.createDivider(Spacing.SMALL))
                        .append(Section.of(Component.button(german), TextDisplay.of("language.german"))
                        ).add(Section.of(Component.button(english), TextDisplay.of("language.english")))
                        .locale(event.getUserLocale().toLocale())
        );
    }

    private String germanRole() {
        return configService.get(BotConfig.OPT_OUT_GERMAN).orElseThrow();
    }

    private String englishRole() {
        return configService.get(BotConfig.OPT_OUT_ENGLISH).orElseThrow();
    }
}
