package me.kavin.fastticket.consts;

import java.awt.Color;
import java.io.FileReader;
import java.io.IOException;
import java.text.DecimalFormat;
import java.util.Properties;

public class Constants {

	static {
		Properties prop = new Properties();
		try {
			prop.load(new FileReader("config.properties"));
		} catch (IOException e) {
			e.printStackTrace();
		}

		BOT_TOKEN = prop.getProperty("BOT_TOKEN");
		MONGO_URI = prop.getProperty("MONGO_URI");
		GITHUB_AUTH_HEADER = prop.getProperty("GITHUB_AUTH_HEADER");
		OPEN_EMOJI_ID = Long.parseLong(prop.getProperty("OPEN_EMOJI_ID"));
		CLOSE_EMOJI_ID = Long.parseLong(prop.getProperty("CLOSE_EMOJI_ID"));
		DIVINE_DISCORD_BOTS_TOKEN = prop.getProperty("DIVINE_DISCORD_BOTS_TOKEN");
		DISCORD_BOTS_TOKEN = prop.getProperty("DISCORD_BOTS_TOKEN");
	}

	public static final String BOT_TOKEN;

	public static final String MONGO_URI;

	public static final String GITHUB_AUTH_HEADER;

	public static final long OPEN_EMOJI_ID;

	public static final long CLOSE_EMOJI_ID;

	public static final String DIVINE_DISCORD_BOTS_TOKEN;

	public static final String DISCORD_BOTS_TOKEN;

	public static final Color DEFAULT_COLOR_EMBED = new Color(69, 73, 76);

	public static final Color NO_COLOR_EMBED = new Color(54, 57, 63);

	public static final DecimalFormat df = new DecimalFormat("#,###");
}
