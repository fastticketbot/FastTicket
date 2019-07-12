package me.kavin.fastticket.utils;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

public class PermissionCheck {

	public static boolean doCheck(Member member, TextChannel tc) {
		if (!member.hasPermission(Permission.ADMINISTRATOR)) {
			EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(tc.getGuild().getIdLong());
			meb.setDescription("You need administrator permissons to use this command!");
			tc.sendMessage(meb.build()).queue();
		}
		return member.hasPermission(Permission.ADMINISTRATOR);
	}

}
