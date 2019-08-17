package me.kavin.fastticket.utils;

import net.dv8tion.jda.api.EmbedBuilder;

public class EmbedUtils {

	public static EmbedBuilder getEmptyEmbedBuilder(long guildId) {
		EmbedBuilder meb = new EmbedBuilder();
		meb.setTitle("Fast Ticket: ");
		meb.setColor(SettingsHelper.getInstance().getEmbedColor(guildId));
		meb.setFooter("Bot made by Kavin#1488",
				"https://cdn.discordapp.com/avatars/425644988487958528/fde16e409671c8ea8fd8054586c805a9.png?size=128");
		return meb;
	}
}
