package com.github.kaktushose.notruf.bot.language;

import com.github.kaktushose.notruf.bot.Config;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.User;

@SuppressWarnings("ConstantConditions")
public record RoleService(Config config, Guild guild) {

    public boolean isInvalid(User user, Languages language) {
        return guild.retrieveMember(user)
                .complete()
                .getRoles().stream()
                .anyMatch(role -> role.getIdLong() == config.getRoleId(language.inverse()));
    }

    public void addRole(User user, Languages language) {
        guild.addRoleToMember(user, guild.getRoleById(config.getRoleId(language))).queue();
    }

    public void removeRole(User user, Languages language) {
        guild.removeRoleFromMember(user, guild.getRoleById(config.getRoleId(language))).queue();
    }
}
