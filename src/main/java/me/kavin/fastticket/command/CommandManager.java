package me.kavin.fastticket.command;

import java.util.ArrayList;

import me.kavin.fastticket.command.commands.Add;
import me.kavin.fastticket.command.commands.Close;
import me.kavin.fastticket.command.commands.Config;
import me.kavin.fastticket.command.commands.EmojiOpen;
import me.kavin.fastticket.command.commands.Help;
import me.kavin.fastticket.command.commands.New;
import me.kavin.fastticket.command.commands.Rename;
import me.kavin.fastticket.command.commands.Stats;
import me.kavin.fastticket.command.commands.Value;

public class CommandManager {

	public static ArrayList<Command> commands = new ArrayList<Command>();

	public CommandManager() {
		commands.add(new Help());
		commands.add(new New());
		commands.add(new Add());
		commands.add(new Close());
		commands.add(new Config());
		commands.add(new Value());
		commands.add(new EmojiOpen());
        commands.add(new Stats());
        commands.add(new Rename());
	}
}