package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import net.DeltaWings.Minecraft.ChatManager.Custom.FileManager;
import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;


public class Chat implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if(sender instanceof Player){
			Player p = (Player)sender;
			if(cmd.getName().equalsIgnoreCase("chat")){
				Config messages = new Config("messages", "channel");
				Config gmsg = new Config("messages", "global");
				Config gconfig = new Config("config", "channel");
				if(p.hasPermission("chat-manager.channel.chat")){
					if(args.length >= 2){
						Config config = new Config("channels", args[0]);
						if( FileManager.listFiles(Main.getInstance().getDataFolder() + File.separator + "channels").contains(args[0]+".yml")){
							StringBuilder message = new StringBuilder();
							for (int i = 1; i < args.length; i++) {
								message.append(args[i]).append(" ");
							}
							if(config.getStringList("admins").contains(p.getName())){
								String msg = gconfig.getString("chat.format").replace("[channel]", args[0]).replace("[player]", gconfig.getString("colors.admin-name")+p.getName()).replace("[message]", gconfig.getString("colors.default")+message);
								//loop players qui font parti du channel
								for(Player player : Bukkit.getServer().getOnlinePlayers()) {
									if(config.getStringList("admins").contains(p.getName()) || config.getStringList("users").contains(p.getName())){
										player.sendMessage(msg.replace("&", "§"));
									}
								}
							}else if(config.getStringList("users").contains(p.getName())){
								String msg = gconfig.getString("chat.format").replace("[channel]", args[0]).replace("[player]", gconfig.getString("colors.user-name")+p.getName()).replace("[message]", gconfig.getString("colors.default")+message);
								//loop players qui font parti du channel
								for(Player player : Bukkit.getServer().getOnlinePlayers()) {
									if(config.getStringList("admins").contains(p.getName()) || config.getStringList("users").contains(p.getName())){
										player.sendMessage(msg.replace("&", "§"));
									}
								}
							}else{
								//message tu ne peux pas envoyer de message dans un groupe dont tu ne fais pas parti
								p.sendMessage(messages.getString("cant-message").replace("&", "§"));
							}
						}else{
							//message le channel que tu cherche n'existe pas
							p.sendMessage(messages.getString("dont-exist").replace("&","§"));
						}
					}else{
						p.sendMessage(gmsg.getString("badly-written").replace("&","§"));
					}
				}else{
					//message permission
					p.sendMessage(gmsg.getString("permission").replace("&", "§"));
				}
			}
		}
		return true;
	}
}
// /c <channel> <message>
/*message :
 *[channel] [player] [message]
 *[message] contain base color set by config file with different by player eg :
 *	admin = &c
 *	user = &b
 *[channel] is set by each channel
 *[player] is the player's name with a color like
 *"&f[&cadmin&f]"
 *"&f[&buser&f]"
 */
