package me.kavin.fastticket.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

import kong.unirest.Unirest;
import kong.unirest.json.JSONObject;
import me.kavin.fastticket.Main;
import me.kavin.fastticket.consts.Constants;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.Message.Attachment;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.requests.restaction.ChannelAction;

public class TicketUtils {

	public static void postLogs(String reason, long guildId, User closed, User opened, TextChannel tc) {
		TextChannel lc = SettingsHelper.getInstance().getLogsChannel(guildId);

		EmbedBuilder logsBuilder = EmbedUtils.getEmptyEmbedBuilder(guildId);
		logsBuilder.setTitle("Fast Ticket: Ticket Closed");
		StringBuilder sb = new StringBuilder();

		sb.append("# Fast Ticket");
		sb.append("\n");
		sb.append("Name | Message");
		sb.append("\n");
		sb.append("--- | ---");
		sb.append("\n");

		List<Message> msgs = new ArrayList<>();

		for (Message msg : tc.getIterableHistory())
			msgs.add(msg);

		Collections.reverse(msgs);

		for (Message msg : msgs) {
			if (msg.getAuthor().isBot())
				continue;
			String content = MarkdownUtils.escapeString(msg.getContentDisplay());
			List<Attachment> attachments = msg.getAttachments();
			if (attachments.size() > 0)
				for (Attachment attachment : attachments)
					content += " [attachment - " + attachment.getFileName() + "](" + attachment.getUrl() + ")";
			sb.append(MarkdownUtils.escapeString(msg.getAuthor().getName()) + " | " + content);
			sb.append("\n");
		}

		if (opened != null) {
			sb.append("\n\n");

			sb.append("Opened by " + MarkdownUtils.escapeString(opened.getName()));
		}

		if (closed != null) {
			sb.append("\n\n");

			sb.append("Closed by " + MarkdownUtils.escapeString(closed.getName()));
		}

		JSONObject resp = new JSONObject(
				Unirest.post("https://api.github.com/gists").header("Authorization", Constants.GITHUB_AUTH_HEADER)
						.body(new JSONObject().put("description", "Ticket Log").put("public", false).put("files",
								new JSONObject().put("log.md", new JSONObject().put("content", String.valueOf(sb)))))
						.asString().getBody());

		String logsUrl = resp.getString("html_url") + "#fast-ticket";

		logsBuilder.setDescription("Ticket closed by " + MarkdownUtils.escapeString(closed.getAsTag()) + "\n"
				+ "Ticket opened by " + (opened != null ? MarkdownUtils.escapeString(opened.getAsTag()) : "User left")
				+ "\n\n" + "**Reason:**" + "\n"
				+ (reason != null ? MarkdownUtils.escapeString(reason) : "No reason provied") + "\n\n"
				+ "Logs: [click here](" + logsUrl + ")");

		if (lc != null)
			lc.sendMessage(logsBuilder.build()).queue();

		try {
			if (opened != null)
				opened.openPrivateChannel().submit().get().sendMessage(logsBuilder.build()).queue();
		} catch (Exception e) {
			// ignored
		}

	}

	public static TextChannel createTicket(Guild guild, Member member, String reason)
			throws InterruptedException, ExecutionException {

		Role role = SettingsHelper.getInstance().getTicketRole(guild.getIdLong());

		Category category = SettingsHelper.getInstance().getTicketCategory(guild.getIdLong());

		ChannelAction<TextChannel> action = category != null
				? category
						.createTextChannel("ticket-" + SettingsHelper.getInstance().getTicketNumber(guild.getIdLong()))
				: guild.createTextChannel("ticket-" + SettingsHelper.getInstance().getTicketNumber(guild.getIdLong()));

		action.setTopic("ticket" + "|" + member.getId())
				.addPermissionOverride(member, Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE),
						Collections.emptyList())
				.addPermissionOverride(guild.getPublicRole(), Collections.emptyList(),
						Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE))
				.addPermissionOverride(guild.getMember(Main.api.getShards().get(0).getSelfUser()),
						Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE), Collections.emptyList());

		if (role != null) {
			action.addPermissionOverride(role, Arrays.asList(Permission.MESSAGE_READ, Permission.MESSAGE_WRITE),
					Collections.emptyList());
		}

		TextChannel tc = action.submit().get();

		{
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(guild.getIdLong());
			meb.setDescription(SettingsHelper.getInstance().getTicketOpenMsg(guild.getIdLong())
					.replace("%user%", MarkdownUtils.escapeString(member.getUser().getAsTag()))
					.replace("%reason%", reason != null ? reason : "No reason provided!"));

			Message msg = tc.sendMessage(meb.build()).submit().get();

			Emote emote = Main.api.getEmoteById(Constants.CLOSE_EMOJI_ID);

			msg.addReaction(emote).queue();
		}

		if (role != null && SettingsHelper.getInstance().getShouldPing(guild.getIdLong())) {

			role.getManager().setMentionable(true).submit().get();

			tc.sendMessage(role.getAsMention()).submit().get();

			role.getManager().setMentionable(false).submit().get();
		}

		return tc;
	}
}
