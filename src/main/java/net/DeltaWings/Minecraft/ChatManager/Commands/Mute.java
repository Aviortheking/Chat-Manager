package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Mute implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender s, Command c, String jk, String[] a) {
		if(s instanceof Player && s.hasPermission("chat-manager.mute.chat") || s instanceof ConsoleCommandSender) {
			Player l = null;
			Boolean f = false;
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(a.length > 0)if(p.getName().equalsIgnoreCase(a[0])) {
					l = p;
					f = true;
				}
			}
			if(!f) s.sendMessage(new Config("messages", "mute").getString("player.not-found").replace("&", "§"));
			else if(a.length >= 3) {
				StringBuilder m = new StringBuilder();
				for ( Integer y = 2; a.length > y; y++ ) m.append(a[y]).append(" ");
				API.mute(s, l, Integer.parseInt(a[1]), "chat", m.toString());
			}
			else if(a.length == 2) API.mute(s, l, Integer.parseInt(a[1]), "chat", new Config("config", "mute").getString("default-reason"));
			else if(a.length == 1) API.mute(s, l, -1, "chat", new Config("config", "mute").getString("default-reason"));
			else s.sendMessage(new Config("messages", "global").getString("badly-written").replace("&", "§"));

		} else {
			s.sendMessage("perm usage");
		}
		return false;
	}
}
// /mute Aviortheking [time] [reason]
