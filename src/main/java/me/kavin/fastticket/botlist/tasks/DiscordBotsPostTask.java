package me.kavin.fastticket.botlist.tasks;

import kong.unirest.json.JSONObject;

import kong.unirest.Unirest;
import kong.unirest.UnirestException;
import me.kavin.fastticket.Main;
import me.kavin.fastticket.consts.Constants;

public class DiscordBotsPostTask implements Runnable {

	@Override
	public void run() {
		JSONObject obj = new JSONObject().put("server_count", Main.api.getGuilds().size());
		try {
			Unirest.post("https://top.gg/api/bots/" + Main.api.getShards().get(0).getSelfUser().getId() + "/stats")
					.header("Authorization", Constants.DISCORD_BOTS_TOKEN).header("Content-Type", "application/json")
					.body(obj.toString()).asEmpty();
		} catch (UnirestException e) {
			e.printStackTrace();
		}
	}
}
