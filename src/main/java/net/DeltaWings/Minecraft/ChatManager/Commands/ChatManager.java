package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import net.DeltaWings.Minecraft.ChatManager.Main;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class ChatManager implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender s, Command c, String l, String[] a) {
		Main p = Main.getInstance();
		if (s instanceof Player) {
			if (s.hasPermission("chat-manager.admin.reload")) {
				p.getServer().getPluginManager().disablePlugin(p);
				p.getServer().getPluginManager().enablePlugin(p);
				s.sendMessage("§8[§cChat§8-§cManager§8] §fPlugin reloaded !");
				Main.log("Plugin reloaded !");
			} else {
				s.sendMessage(new Config("messages", "global").getString("permission").replace("&", "§"));
			}
		} else {
			p.getServer().getPluginManager().disablePlugin(p);
			p.getServer().getPluginManager().enablePlugin(p);
			Main.log("Plugin reloaded !");
		}
		return true;
	}
}
