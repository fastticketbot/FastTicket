package me.kavin.fastticket.utils;

import java.awt.Color;
import java.util.List;

import org.bson.Document;

import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

import me.kavin.fastticket.Main;
import me.kavin.fastticket.consts.Constants;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;

public class SettingsHelper {

	private static final SettingsHelper instance;

	static {
		instance = new SettingsHelper();
	}

	public static SettingsHelper getInstance() {
		return instance;
	}

	private MongoHelper mongoHelper;

	public SettingsHelper() {
		mongoHelper = new MongoHelper(Constants.MONGO_URI);
	}

	public Color getEmbedColor(long guildId) {

		// 0 - default
		// 1 - invisible color
		// 2 - random

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "color");

		String id = String.valueOf(guildId);

		int selected = mongoHelper.containsKey(collection, id) ? mongoHelper.getValueInt(collection, id) : -1;

		Color color;

		switch (selected) {
		case 0:
			color = Constants.DEFAULT_COLOR_EMBED;
			break;
		case 1:
			color = Constants.NO_COLOR_EMBED;
			break;
		case 2:
			color = ColorUtils.getRainbowColor(2000);
			break;
		default:
			mongoHelper.setValueInt(collection, id, 0);
			color = Constants.DEFAULT_COLOR_EMBED;
			break;
		}

		return color;
	}

	public String getSelectedColor(long guildId) {

		// 0 - default
		// 1 - invisible color
		// 2 - random

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "color");

		String id = String.valueOf(guildId);

		int selected = mongoHelper.containsKey(collection, id) ? mongoHelper.getValueInt(collection, id) : -1;

		switch (selected) {
		case 0:
			return "Default";
		case 1:
			return "Invisible";
		case 2:
			return "Random";
		default:
			mongoHelper.setValueInt(collection, id, 0);
			return "Default";
		}
	}

	public void setColor(long guildId, int selected) {

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "color");

		String id = String.valueOf(guildId);

		mongoHelper.setValueInt(collection, id, selected);
	}

	public String getTicketOpenMsg(long guildId) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "open_msg");

		String id = String.valueOf(guildId);

		String selected = mongoHelper.getValueString(collection, id);

		if (selected == null) {
			selected = "Hello %user%!" + "\n\n" + "**Reason:**" + "\n" + "%reason%";
			mongoHelper.setValueString(collection, id, selected);
		}

		return selected;
	}

	public void setTicketOpenMsg(long guildId, String msg) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "open_msg");

		String id = String.valueOf(guildId);

		mongoHelper.setValueString(collection, id, msg);
	}

	public int getTicketNumber(long guildId) {

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "number");

		String id = String.valueOf(guildId);

		int number = mongoHelper.containsKey(collection, id) ? mongoHelper.getValueInt(collection, id) : 0;

		mongoHelper.setValueInt(collection, id, number + 1);

		return number;
	}

	public Role getTicketRole(long guildId) {

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "role");

		String name = mongoHelper.getValueString(collection, String.valueOf(guildId));

		if (name == null)
			return null;

		List<Role> roles = Main.api.getGuildById(guildId).getRolesByName(name, true);

		return roles.isEmpty() ? null : roles.get(0);
	}

	public void setTicketRole(long guildId, Role role) {

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "role");

		mongoHelper.setValueString(collection, String.valueOf(guildId), role != null ? role.getName() : "none");
	}

	public TextChannel getLogsChannel(long guildId) {

		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "logs");

		return Main.api.getTextChannelById(mongoHelper.getValueLong(collection, String.valueOf(guildId)));
	}

	public boolean getShouldPing(long guildId) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "ping");

		return mongoHelper.getValueBoolean(collection, String.valueOf(guildId));
	}

	public void setShouldPing(long guildId, boolean value) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "ping");

		mongoHelper.setValueBoolean(collection, String.valueOf(guildId), value);
	}

	public long getReactionMessageId(long guildId) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "reaction");

		return mongoHelper.getValueLong(collection, String.valueOf(guildId));
	}

	public void setReactionMessageId(long guildId, long id) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "reaction");

		mongoHelper.setValueLong(collection, String.valueOf(guildId), id);
	}

	public void setLogsChannel(TextChannel tc) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "logs");

		mongoHelper.setValueLong(collection, tc.getGuild().getId(), tc.getIdLong());
	}

    public boolean getShouldPingOpener(long guildId) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "pingopener");

		return mongoHelper.getValueBoolean(collection, String.valueOf(guildId));
	}

	public void setShouldPingOpener(long guildId, boolean value) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "pingopener");

		mongoHelper.setValueBoolean(collection, String.valueOf(guildId), value);
	}

    public boolean getAutoClose(long guildId) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "autoclose");

		return mongoHelper.getValueBoolean(collection, String.valueOf(guildId));
	}

	public void setAutoClose(long guildId, boolean value) {
		MongoDatabase db = mongoHelper.getDatabase("guilds");
		MongoCollection<Document> collection = mongoHelper.getCollection(db, "autoclose");

		mongoHelper.setValueBoolean(collection, String.valueOf(guildId), value);
	}
}
