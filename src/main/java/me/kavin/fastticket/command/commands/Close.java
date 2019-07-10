package me.kavin.fastticket.command.commands;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import me.kavin.fastticket.utils.TicketUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Close extends Command {

	public Close() {
		super("-close", "`Closes the ticket you type the command in`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {
		TextChannel tc = event.getTextChannel();
		if (tc.getName().startsWith("ticket-") && tc.getTopic().startsWith("ticket|")) {
			String q = null;

			for (int i = getPrefix().length() + 1; i < message.length(); i++) {
				if (q == null)
					q = "";
				q += message.charAt(i);
			}

			TicketUtils.postLogs(q, event.getGuild().getIdLong(), event.getAuthor(),
					event.getJDA().getUserById(tc.getTopic().split("\\|")[1]), tc);
			tc.delete().queue();

		} else {
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setTitle("Fast Ticket: Close");
			meb.setDescription("This is not a ticket!");
			event.getChannel().sendMessage(meb.build()).queue();
		}
	}
}