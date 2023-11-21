[![Java CI](https://github.com/Kaktushose/LanguageBot/actions/workflows/ci.yml/badge.svg?branch=master)](https://github.com/Kaktushose/LanguageBot/actions/workflows/ci.yml)
[![license-shield](https://img.shields.io/badge/License-Apache%202.0-lightgrey.svg)]()
<a href="https://discord.gg/Ac2tRYG">
<img src="https://discord.com/api/guilds/547006417542971393/embed.png" alt="discord">
</a>
# Notruf Bot

This bot was created specifically for the official Discord [server](https://discord.gg/Ac2tRYG) of the game [Notruf 112 - Die Feuerwehrsimulation 2](https://store.steampowered.com/app/785770/Notruf_112__Die_Feuerwehr_Simulation_2). 
It's only purpose is to add roles to users and to manage bug reports. 

- Use `/opt out <english|german>` to mute the english/german announcement channels

- Use `/reset` to reset your settings

## Installation

The bot is private, but you can compile and host your own version.

### 0. Prerequisites

Make sure to have the following things up and running:

- Java 17
- Maven
- git

### 1. Clone the repository

```
git clone https://github.com/Kaktushose/Levelbot.git
```

### 2. Configuration

- Open the `config.json` file and insert you values:
```json
{
  "token": "", // the bot token
  "guildId": -1, // id of the guild the bot will be running on
  "reportCategoryId": // id of the parent category of the report channel
  "reportChannelId": the report channel to send reports to
  "englishRoleId": -1, // id of the role used to opt out of english announcments
  "germanRoleId": -1 // id of the role used to opt out of german announcments
}
```

- Copy the files `embeds_de.json` and `embeds_en.json` from the repository folder to the location where the jar will run. 

- Optionally you can configure the localization of the slash commands by editing the `commands_de.properties` located at `NotrufBot/src/main/resources/` or add more localizations.

### 3. Build and run the jar

Once you are done with all configuration steps, you can build the jar and run it:

```
mvn clean package
```

```
java -jar Notruf-Bot-1.0.0.jar
```

## Contributing

If you believe that something is missing, and you want to add it yourself, feel free to open a pull request. 
