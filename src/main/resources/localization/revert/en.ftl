## Revert Command
revert-info =
    ### { $type } reverted
    Your { $type } on the **Notruf 112** Discord server has been reverted!
    .body =
    { "**ID**" }
    \#{ $id }
    { "**Date**" }
    { $date }
    { "**Reason**" }
    { $reason }
    .reverter =
    { "**Reverting Moderator**" }
    { $revertedBy }
