package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Unmute implements CommandExecutor {
	@Override
	public boolean onCommand(CommandSender s, Command command, String label, String[] a) {
		if(s instanceof Player && s.hasPermission("chat-manager.unmute.chat") || s instanceof ConsoleCommandSender) {
			Player l = null;
			Boolean f = false;
			for(Player p : Bukkit.getServer().getOnlinePlayers()) {
				if(a.length > 0)if(p.getName().equalsIgnoreCase(a[0])) {
					l = p;
					f = true;
				}
			}
			if(!f) s.sendMessage(new Config("messages", "mute").getString("player.not-found").replace("&", "§"));
			else API.unmute(s, l, "chat");
		}
		return false;
	}
}

//unmute <player>