# Error Messages
## Type Adapting Failed
adapting-failed-title =
    ## Invalid Input
    ### Command
    { $command }
adapting-failed-details =
    ### Details
    { "**Expected Type**" }
    `{ $expected }`
    { "**Actual Type**" }
    `{ $actual }`
    { "**Input**" }
    `{ $raw }`
adapting-failed-message =
    ### Error Message
    { $message }
## Insufficient Permissions
insufficient-permissions =
    ## Missing Permissions
    `{ $interaction }` requires certain permissions to be executed
    ### Required Permissions
    `{ $permissions }`
## Constraint Failed
constraint-failed =
    ### Invalid Input
    { $ message }
## Interaction Execution Failed
execution-failed-title =
    ## Command Execution Failed
    An unexpected error occurred during execution. Please forward the error message to <@393843637437464588>.
execution-failed-message =
    ### Error Message
    User: { $ user }
    Type: `{ $interaction }`
    Timestamp: { DATETIME($timestamp, dateStyle: "medium", timeStyle: "medium") }

## Unknown Interaction
unknown-interaction =
    ### Unknown Interaction
    This interaction has expired and is no longer available

# Constraints
member-missing-permission = The user is missing at least one required permission.
member-has-unallowed-permission = The user has at least one permission that is not allowed.
# Type Adapter
member-required-got-user = A server member is required, but only a Discord user was provided.
# Command Building
no-description = no description