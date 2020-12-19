package Discarpet;

import Discarpet.script.events.DiscordEvents;
import carpet.script.exception.InternalExpressionException;
import org.javacord.api.DiscordApi;
import org.javacord.api.DiscordApiBuilder;
import org.javacord.api.entity.activity.ActivityType;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.TextChannel;
import org.javacord.api.entity.message.Reaction;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.user.User;
import org.javacord.api.entity.user.UserStatus;

import java.awt.*;
import java.io.File;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.CompletionException;
import static Discarpet.Discarpet.LOGGER;

public class Bot {
	public DiscordApi api = null;
	public String id;
	public DiscordApi getApi() {
		return api;
	}

	public Bot(String id, String token) {
		this.id = id;
		try {
			api = new DiscordApiBuilder().setToken(token).login().join();
			LOGGER.info("[Discarpet] Bot " + id + " sucessfully logged in");
			api.addMessageCreateListener(event -> {
				DiscordEvents.DISCORD_MESSAGE.onDiscordMessage(this,event.getMessage());
			});

			api.addReactionAddListener(event -> {
				Optional<Reaction> optionalReaction = event.getReaction();
				if(optionalReaction.isPresent()) {
					Reaction reaction = optionalReaction.get();
					event.requestUser().thenAccept(user -> {
						DiscordEvents.DISCORD_REACTION.onDiscordReaction(this,reaction,user);
					});
				}

			});
		} catch (CompletionException ce) {
			LOGGER.warn("[Discarpet] Invalid bot token for bot " + id + "!");
			api = null;
		}
	}

	public String getInvite() {
		return api.createBotInvite();
	}
}
