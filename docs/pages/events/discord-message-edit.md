### `__on_discord_message_edit(message, old_message, is_actual_edit)`

Executes when a message is edited in a channel the bot has access to.


### {event inputs:}

|            Value | Type                          | Description                                  |
|-----------------:|:------------------------------|:---------------------------------------------|
|        `message` | [Message](/values/message.md) | The message after editing.                   |
|    `old_message` | [Message](/values/message.md) | The message before editing.                  |
| `is_actual_edit` | Boolean                       | Whether if the message was actually changed. |