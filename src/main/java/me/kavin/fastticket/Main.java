package me.kavin.fastticket;

import me.kavin.fastticket.consts.Constants;
import me.kavin.fastticket.listener.DiscordListener;
import net.dv8tion.jda.api.sharding.DefaultShardManagerBuilder;
import net.dv8tion.jda.api.sharding.ShardManager;

public class Main {

	public static ShardManager api;

	public static void main(String[] args) throws Exception {
		DefaultShardManagerBuilder builder = new DefaultShardManagerBuilder(Constants.BOT_TOKEN);
		builder.setAutoReconnect(true);
		builder.addEventListeners(new DiscordListener());
		api = builder.build();
	}
}