package Discarpet.script.functions;

import Discarpet.script.parsable.Parser;
import Discarpet.script.parsable.parsables.webhooks.WebhookProfileParsable;
import Discarpet.script.parsable.parsables.webhooks.WebhookProfileUpdaterParsable;
import Discarpet.script.util.ValueUtil;
import carpet.script.annotation.ScarpetFunction;
import carpet.script.value.Value;
import org.javacord.api.entity.channel.Channel;
import org.javacord.api.entity.channel.ServerTextChannel;
import org.javacord.api.entity.channel.ServerThreadChannel;
import org.javacord.api.entity.channel.ServerThreadChannelBuilder;
import org.javacord.api.entity.webhook.Webhook;
import org.javacord.api.entity.webhook.WebhookBuilder;
import org.javacord.api.entity.webhook.WebhookUpdater;

import java.util.concurrent.CompletableFuture;

@SuppressWarnings("unused")
public class Channels {
	
	@ScarpetFunction
	public boolean dc_set_channel_topic(Channel channel, String str) {
		if(!(channel instanceof ServerTextChannel textChannel)) return false;
        return ValueUtil.awaitFutureBoolean(textChannel.updateTopic(str),"Error updating channel topic");
	}

	@ScarpetFunction
	public Webhook dc_create_webhook(Channel channel, Value webhookBuilder) {
		if(!(channel instanceof ServerTextChannel textChannel)) return null;
		WebhookBuilder builder = new WebhookBuilder(textChannel);
		Parser.parseType(webhookBuilder, WebhookProfileParsable.class).apply(builder);
		return ValueUtil.awaitFuture(builder.create(),"Error creating webhook");
	}

	@ScarpetFunction
	public Webhook dc_update_webhook(Webhook webhook, Value webhookBuilder) {
		WebhookUpdater updater = webhook.createUpdater();
		Parser.parseType(webhookBuilder, WebhookProfileUpdaterParsable.class).apply(updater);
		return ValueUtil.awaitFuture(updater.update(),"Error updating webhook");
	}
	
	@ScarpetFunction
	public Channel dc_create_thread(Value thread) {
		CompletableFuture<ServerThreadChannel> serverThreadChannelCompletableFuture = Parser.parseType(thread, ServerThreadChannelBuilder.class).create();
		return ValueUtil.awaitFuture(serverThreadChannelCompletableFuture, "Error creating thread channel");

	}
}
