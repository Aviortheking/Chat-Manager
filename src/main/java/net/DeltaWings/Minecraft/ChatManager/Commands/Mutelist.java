package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

import java.util.List;

public class Mutelist implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender s, Command command, String label, String[] args) {
		if(s instanceof Player && s.hasPermission("chat-manager.mute.list") || s instanceof ConsoleCommandSender) {
			List<String[]> l = API.mutedlist();
			Config m = new Config("messages", "mute");
			s.sendMessage(m.getString("mutelist.top").replace("&", "§"));
			for(String[] a : l) {
				s.sendMessage(m.getString("mutelist.object").replace("[player]", a[0]).replace("[time]", a[1]).replace("[reason]", a[2]).replace("&", "§"));
			}
			s.sendMessage(m.getString("mutelist.bottom").replace("&", "§"));
		}
		return false;
	}
}
