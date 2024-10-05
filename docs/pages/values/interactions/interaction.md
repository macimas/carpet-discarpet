Represents an interaction. All specialized interactions have these properties, along with their additional properties.

Used for getting its details, and responding with [`dc_respond_interaction()`](/functions/interactions/respond-interaction.md).

| Property             | Type                          | Description                                                                                                             |
|----------------------|-------------------------------|-------------------------------------------------------------------------------------------------------------------------|
| `id`                 | String                        | The ID of the interaction.                                                                                              |
| `channel`            | [Channel](/values/channel.md) | The channel this interaction was made in.                                                                               |
| `user`               | [User](/values/user.md)       | The user who made the interaction.                                                                                      |
| `token`              | String                        | The continuation token internally used for responding to this interaction.                                              |
| `server`             | [Server](/values/server.md)   | The server this interaction was made in.                                                                                |
| `locale`             | String                        | The [locale](https://discord.com/developers/docs/reference#locales) of the user executing the interaction (e.g. en-US). |
| `creation_timestamp` | Number                        | The timestamp as epoch milliseconds when this interaction was made.                                                     |