package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Custom.Config;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class StaffSwitch implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("staffswitch")){
				Config gmessage = new Config("messages", "global");
				Config smessage = new Config("messages", "switch");
				if(p.hasPermission("chat-manager.staff-chat.switch")){
					Config player = new Config("players", p.getName());
					if(player.exist()){
						if(player.getBoolean("switch")){
							player.set("switch", false);
							player.save();
							p.sendMessage(smessage.getString("switch").replace("[state]", "False").replace("&", "§"));
						}else{
							player.set("switch", true);
							player.save();
							p.sendMessage(smessage.getString("switch").replace("[state]", "True").replace("&", "§"));
						}
					}else{
						//basic
						player.create();
						player.set("invite");
						//end basic
						player.set("switch", false);
						player.save();
						p.sendMessage(smessage.getString("switch").replace("[state]", "False").replace("&", "§"));
					}
				}else{
					p.sendMessage(gmessage.getString("permission").replace("&", "§"));
				}
			}
		}
		return true;
	}

}

// /staffswitch