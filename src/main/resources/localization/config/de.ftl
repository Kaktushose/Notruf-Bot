## Config Set
config-update =
    ### Einstellung { $key } gesetzt
    { $value }

## Set Serverlog
config-set-serverlog-kanal-description = Setzt den Textkanal für den Serverlog.
config-set-serverlog-kanal-options-channel-name = kanal
config-set-serverlog-kanal-options-channel-description = Der Textkanal in denen die Bot-Logs gesendet werden sollen.

## Set Optout
config-set-opt-out-description = Setzt die Opt-Out Rolle zum Ausblenden von Sprachbereichen.
config-set-opt-out-options-language-description = Der Sprachbereich der konfiguriert werden soll.
config-set-opt-out-options-role-description = Die Rolle, mit dem der Sprachbereich ausgeblendert wird.

## Config List
no-value-set = Nicht gesetzt
config-list-description = Listet alle Konfigurationen auf.
config-list =
    ## Einstellungen
    Serverlog-Kanal
    <#{ $serverlog }> ({ $serverlog })
    Opt-out German
    <@&{ $german }> ({ $german })
    Opt-out English
    <@&{ $english }> ({ $english })
