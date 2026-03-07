## Config Set
config-update =
    ### Einstellung { $key } gesetzt
    { $value }

## Set Serverlog
config-set-serverlog-kanal-description = Setzt den Textkanal für den Serverlog.
config-set-serverlog-kanal-options-channel-name = kanal
config-set-serverlog-kanal-options-channel-description = Der Textkanal in denen die Bot-Logs gesendet werden sollen.

## Config List
no-value-set = Nicht gesetzt
config-list-description = Listet alle Konfigurationen auf.
config-list =
    ## Einstellungen
    Serverlog-Kanal
    <#{ $serverlog }> ({ $serverlog })
