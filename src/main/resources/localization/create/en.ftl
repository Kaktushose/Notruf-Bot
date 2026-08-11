## Act Target Info
act-info =
    ### { $type }
    { $description ->
        *[WARN] You have received a warning on the **Notruf 112** Discord server!
        [TIMEOUT] You have been timeouted on the **Notruf 112** Discord server!
        [KICK] You have been kicked from the **Notruf 112** Discord server!
        [TEMP_BAN] You have been temporarily banned from the **Notruf 112** Discord server!
        [BAN] You have been banned from the **Notruf 112** Discord server!
    }
    .reason =
    { "**ID**" }
    \#{ $id }
    { "**Date**" }
    { $date }
    { "**Reason**" }
    { $reason }
    .revoke =
    { "**Active Until**" }
   { $until }
    .paragraph =
    { "**Rule you violated**" }
    { $paragraph }
    .reference =
    { "**Reference Message**" }
    > { $message }
    .footer = -# If you have questions or problems, please contact a moderator!
