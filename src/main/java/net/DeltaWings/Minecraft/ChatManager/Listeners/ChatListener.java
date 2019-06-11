package net.DeltaWings.Minecraft.ChatManager.Listeners;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import net.DeltaWings.Minecraft.ChatManager.Custom.Custom;
import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.ArrayList;

public class ChatListener implements Listener {

	private Integer d = 0, e = 0, r = 0, g = -1;
	private Config gconfig = new Config("config", "global");


	@EventHandler(priority = EventPriority.HIGH)
	public void AsyncPlayerChat(AsyncPlayerChatEvent e){
		e.setCancelled(true);
		Config p = new Config("players", e.getPlayer().getName());
		//Calculator
		if(e.getMessage().split("")[0].equalsIgnoreCase("=")) calculator(e);
		//Muted Player
		else if(p.getBoolean("mute.chat", false)) {
			if(p.getInt("mute.from") == -1 || p.getLong("mute.time") > System.currentTimeMillis()) {
				e.getPlayer().sendMessage(new Config("messages", "mute").getString("muted.self").replace("[reason]", p.getString("mute.reason")).replace("&", "§"));
			}
			else {
				p.set("mute.chat", false);
				p.save();
			}
		}
		//Locked chat
		if(API.isChatLocked() && !p.getBoolean("mute.chat", false) && !(e.getMessage().split("")[0].equalsIgnoreCase("="))) {
			Config lockchat = new Config("messages", "lockchat");
			if(e.getPlayer().hasPermission("chat-manager.lockchat.ignore")) {
				e.getPlayer().sendMessage(lockchat.getString("ignored").replace("&", "§"));
				sendMessage(e.getPlayer(), e.getMessage());
			} else e.getPlayer().sendMessage(lockchat.getString("locked").replace("&", "§"));
		//Send Message
		} else if(!p.getBoolean("mute.chat", false) && !(e.getMessage().split("")[0].equalsIgnoreCase("="))) sendMessage(e.getPlayer(), e.getMessage());
	}
	/*
	Calculator
	 */
	private void calculator(AsyncPlayerChatEvent g) {
		String a = g.getMessage();
		StringBuilder f = new StringBuilder();
		for ( String b : a.split(" ") ) {
			Integer l = -1;
			for ( String q : b.split("") ) {
				if ( Custom.isInteger(q) ) {
					if ( l == -1 || l == 1 ) l = 1;
					else {
						l = 1;
						f.append(" ");
					}
				} else {
					if ( l == -1 || l == 0 ) l = 0;
					else {
						l = 0;
						f.append(" ");
					}
				}
				f.append(q);
			}
			f.append(" ");
		}
		String[] c = f.toString().split(" ");
		if ( c[0].equalsIgnoreCase("=") ) start(c, g.getPlayer());
	}

	private void start(String[] b, Player p) {
		r = 0;
		if (b[0].equalsIgnoreCase("=")) {
			for (String c : b) {
				if (c.equalsIgnoreCase("+")) g = 0;
				else if (c.equalsIgnoreCase("-")) g = 1;
				else if (c.equalsIgnoreCase("*") || c.equalsIgnoreCase("x")) g = 2;
				else if (c.equalsIgnoreCase("/")) g = 3;
				if (Custom.isInteger(c)) {
					if (g != -1) {
						e = Integer.parseInt(c);
						calc(g);
						g = -1;
					} else d = Integer.parseInt(c);
				}
			}
			p.sendMessage("[Result] "+ r);
		}
	}

	private void calc(Integer g) {
		if (g == 0) r += d + e;
		else if (g == 1) r += d - e;
		else if (g == 2) {
			if(d != 0) r += d * e;
			else r*= e;
		}
		else if (g == 3) {
			if(d != 0) r += d / e;
			else r /= e;
		}
		d = 0;
		e = 0;
	}
	/*
	End Calculator
	 */

	private void sendMessage(Player p, String msg) {
		Config config = new Config("config", "prefix");
		ArrayList<String> plist = config.getSection("prefix");
		String prefix = "";

		if(!plist.isEmpty()) for ( String a : plist ) if ( p.hasPermission("chat-manager.chat.prefix." + a) ) prefix = config.getString("prefix." + a);

		ArrayList<String> slist = config.getSection("suffix");
		String suffix = "";

		if(!slist.isEmpty()) for ( String a : slist ) if ( p.hasPermission("chat-manager.chat.suffix." + a) ) suffix = config.getString("suffix." + a);

		ArrayList<String> nlist = config.getSection("name-color");
		String namecolor = "&f";
		if(!nlist.isEmpty()) for ( String a : nlist ) if ( p.hasPermission("chat-manager.chat.color." + a) ) namecolor = config.getString("name-color." + a);
		

		if(!nlist.isEmpty()) for ( String a : nlist ) if ( p.hasPermission("chat-manager.chat.name-color." + a) ) namecolor = config.getString("name-color." + a);

		if(!p.hasPermission("chat-manager.chat.colors")) msg = msg.replaceAll("&([0-9a-f])", "");

		msg = "&f" + msg + "&f";
		String res = gconfig.getString("format.chat");
		res = res.replace("[prefix]", prefix).replace("[player]", namecolor+p.getName()).replace("[suffix]", suffix);
		Main.log(res.replace("[message]", msg));
		API.logMessage(res.replace("[message]", msg));
		for(Player pl : Bukkit.getServer().getOnlinePlayers()) {
			String temp;
			if(gconfig.getBoolean("name.enabled") && msg.contains(pl.getName())) temp = res.replace("[message]", msg.replace(pl.getName(), (gconfig.getString("name.color") + pl.getName() + "&f")));
			else temp = res.replace("[message]", msg);
			pl.sendMessage(temp.replace("&", "§"));
		}
	}
}

/*
permissions :
colors in chat : chat-manager.chat.colors
name in certain color : chat-manager.chat.name-color.NAME
prefix : chat-manager.chat.prefix.NAME
suffix : chat-manager.chat.suffix.NAME
lockchat ignore : chat-manager.lock-chat.ignore
 */