package com.github.kaktushose.languagebot;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;

public class Bootstrapper {

    private static final Logger log = LoggerFactory.getLogger(Bootstrapper.class);

    public static void main(String[] args) {
        Config config;
        try (FileReader reader = new FileReader("./config.json")) {
            config = new Gson().fromJson(reader, Config.class);
        } catch (IOException e) {
            log.error("Unable to load config file!", e);
            System.exit(1);
            return;
        }
    }
}
