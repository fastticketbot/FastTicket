package me.kavin.fastticket.command.commands;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import me.kavin.fastticket.utils.SettingsHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Rename extends Command {

	public Rename() {
		super("-rename", "`Renames the current ticket`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {

		TextChannel tc = event.getTextChannel();

		String q = null;

		for (int i = getPrefix().length() + 1; i < message.length(); i++) {
			if (q == null)
				q = "";
			q += message.charAt(i);
		}

		Role role = SettingsHelper.getInstance().getTicketRole(event.getGuild().getIdLong());

		boolean hasPermission = (role != null && event.getMember().getRoles().contains(role))
				|| event.getMember().hasPermission(Permission.ADMINISTRATOR);

		if (!hasPermission) {
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setTitle("Fast Ticket: Rename");
			meb.setDescription("You do not have permission to use this command!\nYou need to have " + (role != null
					? "either the " + role.getAsMention() + " role or have the Administrator permission."
					: "  the Administrator permission."));
			event.getChannel().sendMessage(meb.build()).queue();
			return;
		}

		if (tc.getName().startsWith("ticket-") && tc.getTopic().startsWith("ticket|")) {
			if (q != null) {
				tc.getManager().setName("ticket-" + q).queue();
				EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
				meb.setTitle("Fast Ticket: Rename");
				meb.setDescription("Ticket renamed successfully!");
				event.getChannel().sendMessage(meb.build()).queue();
			} else {
				EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
				meb.setTitle("Fast Ticket: Rename");
				meb.setDescription("You need to provide a new name as an argument!");
				event.getChannel().sendMessage(meb.build()).queue();
			}
		} else {
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setTitle("Fast Ticket: Rename");
			meb.setDescription("This is not a ticket!");
			event.getChannel().sendMessage(meb.build()).queue();
		}

	}
}