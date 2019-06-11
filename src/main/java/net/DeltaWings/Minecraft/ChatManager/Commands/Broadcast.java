package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Broadcast implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if((s instanceof Player && s.hasPermission("chat-manager.broadcast")) || s instanceof ConsoleCommandSender) {
			if(a.length > 0) {
				StringBuilder m = new StringBuilder();
				for(String r : a) m.append(r).append(" ");
				API.broadcast(s.getName(), m.toString());
				return true;
			} else {
				return false;
			}
		} else s.sendMessage(new Config("messages", "global").getString("permission").replace("&", "§"));
		return true;
	}
}