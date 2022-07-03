package com.github.kaktushose.languagebot;

public class Config {

    private String token;
    private long germanRoleId;
    private long englishRoleId;

    public Config(String token, long germanRoleId, long englishRoleId) {
        this.token = token;
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
}
