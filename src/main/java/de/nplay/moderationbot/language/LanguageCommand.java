package de.nplay.moderationbot.language;

import com.google.inject.Inject;
import de.nplay.moderationbot.config.ConfigService;
import de.nplay.moderationbot.config.ConfigService.BotConfig;
import io.github.kaktushose.jdac.annotations.i18n.Bundle;
import io.github.kaktushose.jdac.annotations.interactions.Button;
import io.github.kaktushose.jdac.annotations.interactions.Command;
import io.github.kaktushose.jdac.annotations.interactions.Interaction;
import io.github.kaktushose.jdac.annotations.interactions.ReplyConfig;
import io.github.kaktushose.jdac.components.container.SeparatedContainer;
import io.github.kaktushose.jdac.dispatching.events.ReplyableEvent;
import io.github.kaktushose.jdac.dispatching.events.interactions.CommandEvent;
import io.github.kaktushose.jdac.dispatching.events.interactions.ComponentEvent;
import io.github.kaktushose.jdac.dispatching.reply.Component;
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
    private boolean hasOptOutGerman;
    private boolean hasOptOutEnglish;

    @Inject
    public LanguageCommand(ConfigService configService) {
        this.configService = configService;
    }

    @Command
    public void onCommand(CommandEvent event) {
        var roles = event.getMember().getUnsortedRoles();
        hasOptOutGerman = roles.contains(event.getGuild().getRoleById(germanRole()));
        hasOptOutEnglish = roles.contains(event.getGuild().getRoleById(englishRole()));

        reply(event);
    }

    @Button(value = "show", style = ButtonStyle.SUCCESS)
    public void onShowGerman(ComponentEvent event) {
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(germanRole())).complete();
        hasOptOutGerman = false;
        reply(event);
    }

    @Button(value = "hide", style = ButtonStyle.DANGER)
    public void onHideGerman(ComponentEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(germanRole())).complete();
        hasOptOutGerman = true;
        reply(event);
    }

    @Button(value = "show", style = ButtonStyle.SUCCESS)
    public void onShowEnglish(ComponentEvent event) {
        event.getGuild().removeRoleFromMember(event.getMember(), event.getGuild().getRoleById(englishRole())).complete();
        hasOptOutEnglish = false;
        reply(event);
    }

    @Button(value = "hide", style = ButtonStyle.DANGER)
    public void onHideEnglish(ComponentEvent event) {
        event.getGuild().addRoleToMember(event.getMember(), event.getGuild().getRoleById(englishRole())).complete();
        hasOptOutEnglish = true;
        reply(event);
    }

    private void reply(ReplyableEvent<?> event) {
        String german = hasOptOutGerman ? "onShowGerman" : "onHideGerman";
        String english = hasOptOutEnglish ? "onShowEnglish" : "onHideEnglish";
        event.reply(SeparatedContainer.of(TextDisplay.of("language"), Separator.createDivider(Spacing.SMALL))
                .add(Section.of(Component.button(german), TextDisplay.of("language.german")))
                .addUnseparated(Section.of(Component.button(english), TextDisplay.of("language.english")))
        );
    }

    private String germanRole() {
        return configService.get(BotConfig.OPT_OUT_GERMAN).orElseThrow();
    }

    private String englishRole() {
        return configService.get(BotConfig.OPT_OUT_ENGLISH).orElseThrow();
    }
}
