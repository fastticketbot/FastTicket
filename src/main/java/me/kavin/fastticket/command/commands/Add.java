package me.kavin.fastticket.command.commands;

import java.util.Arrays;
import java.util.Collections;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Add extends Command {

	public Add() {
		super("-add", "`Adds a person to your currently open ticket`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {

		String q = null;

		for (int i = getPrefix().length() + 1; i < message.length(); i++) {
			if (q == null)
				q = "";
			q += message.charAt(i);
		}

		long id = -1;

		try {
			id = getLong(q);
		} catch (Exception e) {
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setDescription("Please tag a person like " + getPrefix() + " "
					+ event.getJDA().getSelfUser().getAsMention() + ".");
			event.getChannel().sendMessage(meb.build()).queue();
			return;
		}

		Member member = event.getGuild().getMemberById(id);

		if (member == null) {
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setDescription("You already have an open ticket.");
			event.getChannel().sendMessage(meb.build()).queue();
			return;
		}

		for (TextChannel tc : event.getGuild().getTextChannels())
			if (tc.getName().startsWith("ticket-") && tc.getTopic() != null && tc.getTopic().startsWith("ticket|"))
				if (event.getAuthor().getId().equals(tc.getTopic().split("\\|")[1])) {
					tc.getManager().putPermissionOverride(member,
							Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE), Collections.emptyList())
							.queue();
					EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
					meb.setDescription("Added " + member.getAsMention() + " to the ticket.");
					event.getChannel().sendMessage(meb.build()).queue();
					return;
				}
	}

	private long getLong(String s) throws NumberFormatException {
		StringBuilder sb = new StringBuilder();
		char c;
		for (int i = 0; i < s.length(); i++)
			if ((c = s.charAt(i)) >= '0' && c <= '9')
				sb.append(c);
		return Long.parseLong(sb.toString());
	}
}