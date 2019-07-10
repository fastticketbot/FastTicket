package me.kavin.fastticket.utils;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedUtils {

	public static EmbedBuilder getEmptyEmbedBuilder(long guildId) {
		EmbedBuilder meb = new EmbedBuilder();
		meb.setTitle("Fast Ticket: ");
		meb.setColor(SettingsHelper.getInstance().getEmbedColor(guildId));
		return meb;
	}
}
