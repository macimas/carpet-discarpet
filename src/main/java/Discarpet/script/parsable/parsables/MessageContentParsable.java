package Discarpet.script.parsable.parsables;

import Discarpet.script.parsable.Applicable;
import Discarpet.script.parsable.DirectParsable;
import Discarpet.script.parsable.Optional;
import Discarpet.script.parsable.ParsableClass;
import Discarpet.script.util.content.ContentApplier;
import carpet.script.value.MapValue;
import carpet.script.value.Value;
import org.javacord.api.entity.message.Message;
import org.javacord.api.entity.message.component.ActionRow;
import org.javacord.api.entity.message.component.LowLevelComponent;
import org.javacord.api.entity.message.embed.EmbedBuilder;
import org.javacord.api.entity.message.mention.AllowedMentions;

import java.util.List;
import java.util.stream.Collectors;

@ParsableClass(name = "message_content")
public class MessageContentParsable implements Applicable<ContentApplier>, DirectParsable {
    
    String content;
    @Optional List<AttachmentParsable> attachments = List.of();
    @Optional List<EmbedBuilder> embeds = List.of();
    @Optional List<List<LowLevelComponent>> components = List.of();
    @Optional AllowedMentions allowed_mentions;
    @Optional Message reply_to;
    @Optional String nonce;
    @Optional Boolean tts = false;
    
    @Override
    public void apply(ContentApplier contentApplier) {
        contentApplier.setContent(content);
        for (AttachmentParsable attachment : attachments) {
            attachment.apply(contentApplier);
        }
        for (EmbedBuilder embed : embeds) {
            contentApplier.addEmbed(embed);
        }
        List<ActionRow> actionRows = components.stream().map(ActionRow::of).collect(Collectors.toList());
        for (ActionRow actionRow : actionRows) {
            contentApplier.addComponent(actionRow);
        }
        if(allowed_mentions != null) contentApplier.setAllowedMentions(allowed_mentions);
        if(reply_to != null) contentApplier.replyTo(reply_to);
        contentApplier.setNonce(nonce);
        contentApplier.setTts(tts);
    }

    @Override
    public boolean tryParseDirectly(Value value) {
        if(!(value instanceof MapValue)) {
            this.content = value.getString();
            return true;
        }
        return false;
    }
}