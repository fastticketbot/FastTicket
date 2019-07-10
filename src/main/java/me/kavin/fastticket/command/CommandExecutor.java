package me.kavin.fastticket.command;

import me.kavin.fastticket.consts.Constants;
import me.kavin.fastticket.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class CommandExecutor implements Runnable {

	private Command cmd;
	private MessageReceivedEvent event;

	public CommandExecutor(Command cmd, MessageReceivedEvent event) {
		this.cmd = cmd;
		this.event = event;
	}

	@Override
	public void run() {
		try {
			cmd.onCommand(event.getMessage().getContentRaw(), event);
		} catch (Throwable t) {
			t.printStackTrace();
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setColor(Constants.NO_COLOR_EMBED);
			meb.setDescription("An error occoured while executing the command!");
			event.getChannel().sendMessage(meb.build());
		}
	}
}
