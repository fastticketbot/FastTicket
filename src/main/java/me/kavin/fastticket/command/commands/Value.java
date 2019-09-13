package me.kavin.fastticket.command.commands;

import java.util.List;

import me.kavin.fastticket.command.Command;
import me.kavin.fastticket.utils.EmbedUtils;
import me.kavin.fastticket.utils.PermissionCheck;
import me.kavin.fastticket.utils.SettingsHelper;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

public class Value extends Command {
    public Value() {
        super("-value", "`Allows you to set config values`");
    }

    @Override
    public void onCommand(String message, MessageReceivedEvent event) throws Throwable {

        if (!PermissionCheck.doCheck(event.getMember(), event.getTextChannel()))
            return;

        {
            EmbedBuilder meb = EmbedUtils.getEmptyEmbedBuilder(event.getGuild().getIdLong());
            meb.setTitle("Fast Ticket: Value");
            ValueType chosen = null;
            String[] split = message.split(" ");
            if (split.length > 1)
                for (ValueType category : ValueType.values())
                    if (split[1].equalsIgnoreCase(String.valueOf(category))) {
                        chosen = category;
                        break;
                    }
            if (chosen != null)
                switch (chosen) {
                case COLOR:
                    if (split.length > 2) {

                        // 0 - default
                        // 1 - invisible color
                        // 2 - random

                        switch (split[2].toUpperCase()) {
                        case "DEFAULT":
                            SettingsHelper.getInstance().setColor(event.getGuild().getIdLong(), 0);
                            meb.setDescription("Embed Color set to default.");
                            break;
                        case "INVISIBLE":
                            SettingsHelper.getInstance().setColor(event.getGuild().getIdLong(), 1);
                            meb.setDescription("Embed Color set to invisible.");
                            break;
                        case "RANDOM":
                            SettingsHelper.getInstance().setColor(event.getGuild().getIdLong(), 2);
                            meb.setDescription("Embed Color set to random.");
                            break;
                        default:
                            meb.setDescription("Unknown choice! Valid choices are: " + "\n" + "`Default`" + "\n"
                                    + "`Invisible`" + "\n" + "`Random`");
                            break;
                        }
                    } else
                        meb.setDescription("Please choose one of the these for the value: " + "\n" + "`Default`" + "\n"
                                + "`Invisible`" + "\n" + "`Random`");
                    break;
                case LOGS:
                    if (split.length > 2) {
                        try {
                            long id = getLong(split[2]);
                            TextChannel tc = event.getGuild().getTextChannelById(id);
                            if (tc != null) {
                                SettingsHelper.getInstance().setLogsChannel(tc);
                                meb.setDescription("Set " + tc.getAsMention() + " to be used for logs.");
                            } else
                                meb.setDescription("Please tag / enter the channel id as your argument.");
                        } catch (Exception e) {
                            meb.setDescription("Please tag / enter the channel id as your argument.");
                        }
                    } else
                        meb.setDescription("Please tag / enter the channel id as your argument.");
                    break;
                case TICKET_OPEN_MSG: {
                    String q = null;
                    for (int i = (getPrefix() + " " + ValueType.TICKET_OPEN_MSG.name()).length() + 1; i < message
                            .length(); i++) {
                        if (q == null)
                            q = "";
                        q += message.charAt(i);
                    }

                    if (q != null) {
                        SettingsHelper.getInstance().setTicketOpenMsg(event.getGuild().getIdLong(), q);
                        meb.setDescription("Set " + q + " to be used for open ticket msg.");
                    } else
                        meb.setDescription("Please add a message to set to.");
                }
                    break;
                case ROLE: {
                    String q = null;
                    for (int i = (getPrefix() + " " + ValueType.ROLE.name()).length() + 1; i < message.length(); i++) {
                        if (q == null)
                            q = "";
                        q += message.charAt(i);
                    }
                    if (q == null) {
                        meb.setDescription("Please specify a role by name to set to.");
                    } else if (q.equalsIgnoreCase("none")) {
                        SettingsHelper.getInstance().setTicketRole(event.getGuild().getIdLong(), null);
                        meb.setDescription("Reset the role config.");
                    } else {
                        List<Role> roles = event.getGuild().getRolesByName(q, true);
                        if (!roles.isEmpty()) {
                            Role role = roles.get(0);
                            SettingsHelper.getInstance().setTicketRole(event.getGuild().getIdLong(), role);
                            meb.setDescription("Set the role to " + role.getAsMention() + ".");
                        } else {
                            meb.setDescription("Invalid role specified.");
                        }
                    }
                }
                    break;
                case OPEN_TAG: {
                    String q = null;
                    for (int i = (getPrefix() + " " + ValueType.OPEN_TAG.name()).length() + 1; i < message
                            .length(); i++) {
                        if (q == null)
                            q = "";
                        q += message.charAt(i);
                    }
                    if (q != null) {
                        try {
                            boolean b = Boolean.parseBoolean(q);
                            SettingsHelper.getInstance().setShouldPing(event.getGuild().getIdLong(), b);
                            meb.setDescription("Set the value to " + b + ".");
                        } catch (Exception e) {
                            meb.setDescription("Invalid value. Allowed values are `true` and `false`");
                        }
                    } else
                        meb.setDescription("No value provided! Allowed values are `true` and `false`");
                }
                    break;

                case PING_OPENER: {
                    String q = null;
                    for (int i = (getPrefix() + " " + ValueType.PING_OPENER.name()).length() + 1; i < message
                            .length(); i++) {
                        if (q == null)
                            q = "";
                        q += message.charAt(i);
                    }
                    if (q != null) {
                        try {
                            boolean b = Boolean.parseBoolean(q);
                            SettingsHelper.getInstance().setShouldPingOpener(event.getGuild().getIdLong(), b);
                            meb.setDescription("Set the value to " + b + ".");
                        } catch (Exception e) {
                            meb.setDescription("Invalid value. Allowed values are `true` and `false`");
                        }
                    } else
                        meb.setDescription("No value provided! Allowed values are `true` and `false`");
                }
                    break;

                case CLOSE_INACTIVE: {
                    String q = null;
                    for (int i = (getPrefix() + " " + ValueType.CLOSE_INACTIVE.name()).length() + 1; i < message
                            .length(); i++) {
                        if (q == null)
                            q = "";
                        q += message.charAt(i);
                    }
                    if (q != null) {
                        try {
                            boolean b = Boolean.parseBoolean(q);
                            SettingsHelper.getInstance().setAutoClose(event.getGuild().getIdLong(), b);
                            meb.setDescription("Set the value to " + b + ".");
                        } catch (Exception e) {
                            meb.setDescription("Invalid value. Allowed values are `true` and `false`");
                        }
                    } else
                        meb.setDescription("No value provided! Allowed values are `true` and `false`");
                }
                    break;

                case FORCE_REASON: {
                    String q = null;
                    for (int i = (getPrefix() + " " + ValueType.FORCE_REASON.name()).length() + 1; i < message
                            .length(); i++) {
                        if (q == null)
                            q = "";
                        q += message.charAt(i);
                    }
                    if (q != null) {
                        try {
                            boolean b = Boolean.parseBoolean(q);
                            SettingsHelper.getInstance().setForceReason(event.getGuild().getIdLong(), b);
                            meb.setDescription("Set the value to " + b + ".");
                        } catch (Exception e) {
                            meb.setDescription("Invalid value. Allowed values are `true` and `false`");
                        }
                    } else
                        meb.setDescription("No value provided! Allowed values are `true` and `false`");
                }
                    break;
                }
            else {
                StringBuilder sb = new StringBuilder("Please choose one of the choice as your argument: ");
                for (ValueType type : ValueType.values())
                    sb.append("\n-value **" + String.valueOf(type).toLowerCase() + "**" + " `<value>`");
                meb.setDescription(String.valueOf(sb));
            }

            event.getChannel().sendMessage(meb.build()).queue();
        }
    }

    private long getLong(String s) throws NumberFormatException {
        StringBuilder sb = new StringBuilder();
        char c;
        for (int i = 0; i < s.length(); i++)
            if ((c = s.charAt(i)) >= '0' && c <= '9')
                sb.append(c);
        return Long.parseLong(sb.toString());
    }

    private static enum ValueType {
        COLOR, LOGS, TICKET_OPEN_MSG, ROLE, OPEN_TAG, PING_OPENER, CLOSE_INACTIVE, FORCE_REASON
    }
}