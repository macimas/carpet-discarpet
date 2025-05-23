package net.replaceitem.discarpet.script.values;

import carpet.script.value.BooleanValue;
import net.replaceitem.discarpet.script.exception.DiscordThrowables;
import net.replaceitem.discarpet.script.util.ValueUtil;
import net.replaceitem.discarpet.script.values.common.MessageableValue;
import net.replaceitem.discarpet.script.values.common.Renamable;
import carpet.script.value.ListValue;
import carpet.script.value.StringValue;
import carpet.script.value.Value;
import org.javacord.api.entity.Mentionable;
import org.javacord.api.entity.Nameable;
import org.javacord.api.entity.channel.*;

public class ChannelValue extends MessageableValue<Channel> implements Renamable {
    public ChannelValue(Channel channel) {
        super(channel);
    }

    @Override
    protected String getDiscordTypeString() {
        return "channel";
    }

    public Value getProperty(String property) {
        return switch (property) {
            case "name" -> StringValue.of(delegate instanceof Nameable nameableChannel ? nameableChannel.getName() : null);
            case "type" -> StringValue.of(delegate.getType().toString());
            case "topic" -> StringValue.of(delegate instanceof ServerTextChannel serverTextChannel ? serverTextChannel.getTopic() : null);
            case "id" -> StringValue.of(delegate.getIdAsString());
            case "mention_tag" -> StringValue.of(delegate instanceof Mentionable mentionableChannel ? mentionableChannel.getMentionTag() : null);
            case "server" -> new ServerValue(delegate instanceof ServerChannel serverChannel ? serverChannel.getServer() : null);
            case "webhooks" -> delegate instanceof ServerTextChannel serverTextChannel ? ListValue.wrap(ValueUtil.awaitFuture(serverTextChannel.getWebhooks(), "Error getting webhooks from channel").stream().map(WebhookValue::of)) : Value.NULL;
            case "nsfw" -> BooleanValue.of(
                    delegate instanceof ServerTextChannel serverTextChannel && serverTextChannel.isNsfw()
                    || delegate instanceof ServerVoiceChannel serverVoiceChannel && serverVoiceChannel.isNsfw()
                    || delegate instanceof ChannelCategory channelCategory && channelCategory.isNsfw()
            );
            default -> super.getProperty(property);
        };
    }

    @Override
    public void rename(String name) {
        if(!(delegate instanceof ServerChannel serverChannel)) throw DiscordThrowables.genericCode(DiscordThrowables.Codes.CANNOT_EXECUTE_ACTION_ON_CHANNEL_TYPE);
        ValueUtil.awaitFuture(serverChannel.updateName(name), "Could not rename channel");
    }
}
