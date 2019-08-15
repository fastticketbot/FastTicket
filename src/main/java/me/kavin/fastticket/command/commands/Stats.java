package me.kavin.fastticket.command.commands;

import java.io.FileReader;
import java.lang.management.ManagementFactory;
import java.text.DecimalFormat;

import javax.management.Attribute;
import javax.management.AttributeList;
import javax.management.MBeanServer;
import javax.management.ObjectName;

import org.apache.commons.io.IOUtils;

import me.kavin.fastticket.Main;
import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Stats extends Command {

	private DecimalFormat df = new DecimalFormat("#.##");
	private MBeanServer mbs = ManagementFactory.getPlatformMBeanServer();

	public Stats() {
		super("-stats", "`Shows the statistics of the bot`");
	}

	@Override
	public void onCommand(String message, MessageReceivedEvent event) throws Throwable {
		EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());

		meb.setTitle("Bot Statistics");

		long maxMemory = Runtime.getRuntime().maxMemory() / (1024 * 1024);
		long totalMemory = Runtime.getRuntime().totalMemory() / (1024 * 1024);
		long freeMemory = Runtime.getRuntime().freeMemory() / (1024 * 1024);
		long usedMemory = totalMemory - freeMemory;
		String cpuUsage = getProcessCpuLoad();

		meb.addField("Users: ", String.valueOf(Main.api.getUsers().size()) + "\n", false);
		meb.addField("Servers: ", String.valueOf(Main.api.getGuilds().size()), false);

		meb.addBlankField(false);

		meb.addField("CPU Usage: ", String.valueOf(cpuUsage) + "\n", false);
		meb.addField("Max Memory: ", String.valueOf(maxMemory) + "\n", false);
		meb.addField("Total Memory: ", String.valueOf(totalMemory) + "\n", false);
		meb.addField("Free Memory: ", String.valueOf(freeMemory) + "\n", false);
		meb.addField("Used Memory: ", String.valueOf(usedMemory) + "\n", false);

		meb.addField("Version", IOUtils.readLines(new FileReader(".git/FETCH_HEAD")).get(0).substring(0, 7) + "\n",
				false);

		event.getChannel().sendMessage(meb.build()).queue();
	}

	private String getProcessCpuLoad() {

		AttributeList list = null;

		try {
			list = mbs.getAttributes(ObjectName.getInstance("java.lang:type=OperatingSystem"),
					new String[] { "ProcessCpuLoad" });
		} catch (Exception e) {
			e.printStackTrace();
		}

		if (list.isEmpty())
			return "NaN";

		Attribute att = (Attribute) list.get(0);
		Double value = (Double) att.getValue();

		if (value == -1.0)
			return "NaN";
		return df.format((value * 1000) / 10.0);
	}
}