package me.kavin.fastticket.command.commands;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import me.kavin.fastticket.utils.PermissionCheck;
import me.kavin.fastticket.utils.SettingsHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Category;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Config extends Command {
	public Config() {
		super("-config", "`Shows the current configuration`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {

		if (!PermissionCheck.doCheck(event.getMember(), event.getTextChannel()))
			return;

		{
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
			meb.setTitle("Fast Ticket's Configuration:");

			meb.addField("Color", SettingsHelper.getInstance().getSelectedColor(event.getGuild().getIdLong()), false);

			TextChannel tc = SettingsHelper.getInstance().getLogsChannel(event.getGuild().getIdLong());
			meb.addField("Logs Channel", tc != null ? tc.getAsMention() : "Not Set", false);

			meb.addField("Ticket Open Msg", SettingsHelper.getInstance().getTicketOpenMsg(event.getGuild().getIdLong()),
					false);

			Role role = SettingsHelper.getInstance().getTicketRole(event.getGuild().getIdLong());

			meb.addField("Role", role != null ? role.getAsMention() : "Not set", false);

			meb.addField("Role Open Ticket Tag",
					String.valueOf(SettingsHelper.getInstance().getShouldPing(event.getGuild().getIdLong())), false);

			meb.addField("Reaction Message Id",
					String.valueOf(SettingsHelper.getInstance().getReactionMessageId(event.getGuild().getIdLong())),
					false);

			meb.addField("Ticket Opener Tag",
					String.valueOf(SettingsHelper.getInstance().getShouldPingOpener(event.getGuild().getIdLong())),
					false);

			meb.addField("Auto Close Inactive",
					String.valueOf(SettingsHelper.getInstance().getAutoClose(event.getGuild().getIdLong())), false);

			meb.addField("Force Require Reason",
					String.valueOf(SettingsHelper.getInstance().getForceReason(event.getGuild().getIdLong())), false);

			Category category = SettingsHelper.getInstance().getTicketCategory(event.getGuild().getIdLong());
			meb.addField("Category", category != null ? category.getName() : "Not set", false);

			event.getChannel().sendMessage(meb.build()).queue();
		}
	}
}