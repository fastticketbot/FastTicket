package me.kavin.fastticket.command.commands;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.command.CommandManager;
import me.kavin.fastticket.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Help extends Command {
	public Help() {
		super("-help", "`Shows this message`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {
		{
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setTitle("Fast Ticket's Commands:");
			for (Command cmd : CommandManager.commands) {
				if (meb.getFields().size() >= 25) {
					event.getChannel().sendMessage(meb.build()).queue();
					meb.clearFields();
					meb.setTitle(" ");
					meb.addField(cmd.getPrefix(), cmd.getHelp() + '\n', false);
				} else {
					meb.addField(cmd.getPrefix(), cmd.getHelp() + '\n', false);
				}

			}
			if (meb.getFields().size() > 0)
				event.getChannel().sendMessage(meb.build()).queue();
		}
	}
}