package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Clearchat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String label, String[] a) {
		if(a.length == 0) if ( s instanceof Player && s.hasPermission("chat-manager.clear-chat.global") || s instanceof ConsoleCommandSender) API.clearchat(s.getName(), null);
		else if(a.length == 1) if( s instanceof Player && s.hasPermission("chat-manager.clear-chat.player") || s instanceof ConsoleCommandSender)  API.clearchat(s.getName(), a[0]);
		return true;
	}
}