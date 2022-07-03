package com.github.kaktushose.languagebot;

public class Config {

    private String token;
    private long guildId;
    private long germanRoleId;
    private long englishRoleId;

    public Config(String token, long guildId, long germanRoleId, long englishRoleId) {
        this.token = token;
        this.guildId = guildId;
        this.germanRoleId = germanRoleId;
        this.englishRoleId = englishRoleId;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
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
}
