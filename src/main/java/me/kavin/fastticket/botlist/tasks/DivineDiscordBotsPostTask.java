package me.kavin.fastticket.botlist.tasks;

import kong.unirest.json.JSONObject;

import kong.unirest.Unirest;
import me.kavin.fastticket.Main;
import me.kavin.fastticket.consts.Constants;

public class DivineDiscordBotsPostTask implements Runnable {
	@Override
	public void run() {
		Unirest.post(
				"https://divinediscordbots.com/bot/" + Main.api.getShards().get(0).getSelfUser().getId() + "/stats")
				.header("Content-Type", "application/json").header("Authorization", Constants.DIVINE_DISCORD_BOTS_TOKEN)
				.body(new JSONObject().put("server_count", Main.api.getGuilds().size())).asEmpty();
	}
}
