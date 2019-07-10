package me.kavin.fastticket.command;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public abstract class Command {

	private String help;
	private String prefix;

	public Command(String prefix, String help) {
		this.prefix = prefix;
		this.help = help;
	}

	public String getHelp() {
		return help;
	}

	public String getPrefix() {
		return prefix;
	}

	public abstract void onCommand(String string, MessageReceivedEvent event) throws Throwable;
}