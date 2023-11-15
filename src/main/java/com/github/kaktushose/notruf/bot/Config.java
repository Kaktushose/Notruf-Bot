package com.github.kaktushose.notruf.bot;

import com.github.kaktushose.notruf.bot.language.Languages;

public class Config {

    private String token;
    private long guildId;
    private long reportChannelId;
    private long reportCategoryId;
    private long germanRoleId;
    private long englishRoleId;

    public Config(String token, long guildId, long reportChannelId, long reportCategoryId, long germanRoleId, long englishRoleId) {
        this.token = token;
        this.guildId = guildId;
        this.reportChannelId = reportChannelId;
        this.reportCategoryId = reportCategoryId;
        this.germanRoleId = germanRoleId;
        this.englishRoleId = englishRoleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public long getRoleId(Languages language) {
        if (language == Languages.GERMAN) {
            return getGermanRoleId();
        } else if (language == Languages.ENGLISH) {
            return getEnglishRoleId();
        } else {
            throw new IllegalArgumentException("Unknown language!");
        }
    }

    public long getGermanRoleId() {
        return germanRoleId;
    }

    public void setGermanRoleId(long germanRoleId) {
        this.germanRoleId = germanRoleId;
    }

    public long getEnglishRoleId() {
        return englishRoleId;
    }

    public void setEnglishRoleId(long englishRoleId) {
        this.englishRoleId = englishRoleId;
    }

    public long getGuildId() {
        return guildId;
    }

    public void setGuildId(long guildId) {
        this.guildId = guildId;
    }

    public long getReportChannelId() {
        return reportChannelId;
    }

    public void setReportChannelId(long reportChannelId) {
        this.reportChannelId = reportChannelId;
    }

    public long getReportCategoryId() {
        return reportCategoryId;
    }

    public void setReportCategoryId(long reportCategoryId) {
        this.reportCategoryId = reportCategoryId;
    }
}
