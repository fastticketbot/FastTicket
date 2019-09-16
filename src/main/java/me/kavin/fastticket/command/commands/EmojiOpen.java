package me.kavin.fastticket.command.commands;

import me.kavin.fastticket.Main;
import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.consts.Constants;
import me.kavin.fastticket.utils.EmbedUtils;
import me.kavin.fastticket.utils.PermissionCheck;
import me.kavin.fastticket.utils.SettingsHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class EmojiOpen extends Command {
	public EmojiOpen() {
		super("-emojiopen", "`Sends a message with a reaction that can be used to open tickets!`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {

		if (!PermissionCheck.doCheck(event.getMember(), event.getTextChannel()))
			return;

		{
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setTitle("Fast Ticket: Open Ticket");

			Emote emote = Main.api.getEmoteById(Constants.OPEN_EMOJI_ID);

			meb.setDescription("Click the " + emote.getAsMention() + " to open a ticket!");

			Message msg = event.getChannel().sendMessage(meb.build()).submit().get();
			SettingsHelper.getInstance().setReactionMessageId(event.getGuild().getIdLong(), msg.getIdLong());
			msg.addReaction(emote).queue();
		}
	}
}