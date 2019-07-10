package me.kavin.fastticket.command.commands;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import me.kavin.fastticket.utils.TicketUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class New extends Command {

	public New() {
		super("-new", "`Opens a new ticket`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {

		String q = null;

		for (int i = getPrefix().length() + 1; i < message.length(); i++) {
			if (q == null)
				q = "";
			q += message.charAt(i);
		}

		for (TextChannel tc : event.getGuild().getTextChannels())
			if (tc.getName().startsWith("ticket-") && tc.getTopic().startsWith("ticket|"))
				if (event.getAuthor().getId().equals(tc.getTopic().split("\\|")[1])) {
					EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
					meb.setDescription("You already have an open ticket.");
					event.getChannel().sendMessage(meb.build()).queue();
					return;
				}

		TextChannel tc = TicketUtils.createTicket(event.getGuild(), event.getMember(), q);

		{
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setDescription("Opened a new ticket in " + tc.getAsMention() + ".");
			event.getChannel().sendMessage(meb.build()).queue();
		}
	}
}