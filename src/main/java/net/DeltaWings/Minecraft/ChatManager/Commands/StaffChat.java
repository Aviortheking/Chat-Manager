package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.DeltaWings.Minecraft.ChatManager.Main;

public class StaffChat implements CommandExecutor {

	private final Main m = Main.getInstance();

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		if(c.getName().equalsIgnoreCase("staffchat")) {
			if(s instanceof Player) {
				if(s.hasPermission("chat-manager.staff-chat.send")) sendMessage(a, s.getName());
				else s.sendMessage(new Config("messages", "global").getString("permission").replace("&", "§"));
			} else sendMessage(a, "Console");
		}
		return true;
	}

	private void sendMessage(String[] a, String s) {
		StringBuilder z = new StringBuilder();
		for (String r : a) {
			z.append(r).append(" ");
		}
		for(Player p : Bukkit.getServer().getOnlinePlayers()) {
			if(p.hasPermission("chat-manager.staff-chat.read")){
				m.makeDefaultPlayerFile(p);
				if(new Config("players", p.getName()).getBoolean("switch")){
					Config g = new Config("config", "staffchat");
					p.sendMessage(g.getString("chat.format").replace("[player]", g.getString("colors.player")+s).replace("[message]", g.getString("colors.default")+z).replace("&", "§"));
				}
			}
		}
	}
}

/*
 * Permissions :
 * Staff chat read : chat-manager.staff-chat.read
 * Staff chat send : chat-manager.staff-chat.send
 */