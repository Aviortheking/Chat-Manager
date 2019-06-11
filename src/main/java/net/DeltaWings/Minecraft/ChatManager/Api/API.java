package net.DeltaWings.Minecraft.ChatManager.Api;

import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import net.DeltaWings.Minecraft.ChatManager.Custom.FileManager;
import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class API {
	
	/**
	 * Replace each
	 * @param text text to replace
	 * @param replacement map to replace the key by the value
	 * @return the replaced text
	 */
	public static String multiReplace(String text, HashMap<String, String> replacements) {
		for(Map.Entry<String, String> entry : replacements.entrySet()) {
			Main.log(entry.getKey());
			Main.log(entry.getValue());
			text = text.replace(entry.getKey(), entry.getValue());
		}
		return text.replace("&", "§");
	}

	/**
	 * Send a broadcast message
	 * @param Sender Command sender or desired sender
	 * @param Message message broadcasted
	 */
	public static void broadcast(String Sender, String Message) {
		Config a = new Config("config", "broadcast");
		HashMap<String, String> replacements = new HashMap<>();
		replacements.put("[player]", Sender);
		replacements.put("[message]", Message);
		Bukkit.broadcastMessage(multiReplace(a.getString("format"), replacements));
	}

	/**
	 * Clearchat
	 * @param Sender the Sender is the name
	 * @param Receiver the receiver. if "null" will send to everyone
	 */
	public static void clearchat(String Sender, String Receiver) {
		if(Receiver != null) {
			for ( Player p : Bukkit.getServer().getOnlinePlayers() ) if ( p.getName().equalsIgnoreCase(Receiver) ) cca(p, Sender);
		} else for ( Player p : Bukkit.getServer().getOnlinePlayers() ) cca(p, Sender);
	}

	private static void cca(Player p, String c) {
		for ( int i = 0; i < new Config("config", "clearchat").getInt("lines-to-clear"); i++ ) p.sendMessage(" ");
		p.sendMessage(new Config("messages", "clearchat").getString("clear").replace("[cleaner]", c).replace("&", "§"));
	}

	private static Boolean locked = false;

	/**
	 *  lock or unlock the chat
	 */
	public static void lockchat() {
		Config config = new Config("config", "lockchat");
		if ( locked ) {
			locked = false;
			Bukkit.broadcastMessage(config.getString("unlocked").replace("&", "§"));
		} else {
			locked = true;
			Bukkit.broadcastMessage(config.getString("locked").replace("&", "§"));
		}
	}

	/**
	 * Get if the chat is locked or not
	 * @return the state of the chat
	 */
	public static Boolean isChatLocked() {
		return locked;
	}

	/**
	 *
	 * @param Sender Sender
	 * @param Player Player muted
	 * @param Time Time muted(-1 for perma)
	 * @param Type Type(all, chat, cmd)
	 * @param Reason reason
	 */
	public static void mute(CommandSender Sender, Player Player, Integer Time, String Type, String Reason) {
		Config c = new Config("players", Player.getName());
		Config m = new Config("messages", "mute");
		if(Type.equalsIgnoreCase("all")) {
			if ( c.getBoolean("mute.chat") && c.getBoolean("mute.cmd") ) Sender.sendMessage("");
			if(!c.getBoolean("mute.chat")) mua(Player, Time, Reason, Type, Sender);
			if(!c.getBoolean("mute.cmd")) mub(Player, Time, Reason, Type, Sender);
		} else if(!c.getBoolean("mute." + Type)) {
			if(Type.equalsIgnoreCase("chat")) mua(Player, Time, Reason, Type, Sender);
			else mub(Player, Time, Reason, Type, Sender);
		} else Sender.sendMessage(m.getString("already-muted").replace("[type]", Type).replace("&", "§"));
	}

	//mute from chat
	private static void mua(Player p, Integer i, String r,String t, CommandSender s) {
		Config c = new Config("players", p.getName());
		Config m = new Config("messages", "mute");
		c.set("mute.chat", true);
		if(i == -1) c.set("mute.from", -1);
		else c.set("mute.from", i * 60000 + System.currentTimeMillis());
		c.set("mute.time", i);
		c.set("mute.reason", r);
		c.save();
		p.sendMessage(m.getString("muted.self").replace("[from]", "chat").replace("[reason]", r).replace("&", "§"));
		s.sendMessage(m.getString("muted.other").replace("[player]", p.getName()).replace("[type]", t).replace("[reason]", r).replace("&", "§"));
	}

	private static void mub(Player p, Integer i, String r,String t, CommandSender s) {
		Config c = new Config("players", p.getName());
		Config m = new Config("messages", "mute");
		c.set("mute.cmd", true);
		if(i == -1) c.set("mute.time", new Long(i));
		else c.set("mute.time", System.currentTimeMillis() + (i * 60000));
		c.set("mute.reason", r);
		c.save();
		p.sendMessage(m.getString("muted.self").replace("[from]", "commands").replace("[reason]", r).replace("&", "§"));
		s.sendMessage(m.getString("muted.other").replace("[player]", p.getName()).replace("[type]", t).replace("[reason]", r).replace("&", "§"));
	}

	/**
	 *
	 * @param Sender Sender of the command
	 * @param Player Player unmutted
	 * @param Type Type unmutted(chat, cmd, all)
	 */
	public static void unmute(CommandSender Sender, Player Player, String Type) {
		Config c = new Config("players", Player.getName());
		if(Type.equalsIgnoreCase("all") || Type.equalsIgnoreCase("chat")) {
			c.set("mute.chat", false);
		}
		if(Type.equalsIgnoreCase("all") || Type.equalsIgnoreCase("cmd")) {
			c.set("mute.cmd", false);
		}
		c.save();
		Config m = new Config("messages", "mute");
		Sender.sendMessage(m.getString("unmutted.other").replace("[player]", Player.getName()).replace("[type]", Type).replace("&", "§"));
		Player.sendMessage(m.getString("unmutted.self").replace("[type]", Type).replace("&", "§"));
	}

	/**
	 *
	 * @return String list of muted players ex : [Aviortheking,1489446063044,"banned"]
	 */
	public static List<String[]> mutedlist() {
		List<String[]> r = new ArrayList<>();
		for(String f : FileManager.listFiles(Main.getInstance().getDataFolder() + File.separator + "players")) {
			f = f.replace(".yml", "");
			Config c = new Config("players", f);
			if(c.getBoolean("mute.chat")) {
				String[] l = new String[3];
				l[0] = f;
				if(c.getLong("mute.from") == -1) l[1] = "permanent";
				else l[1] = c.getInt("mute.time") + " Minutes";
				l[2] = c.getString("mute.reason");
				r.add(l);
			}
		}
		return r;
	}

	static public void logMessage(String message) {
		try {
			File l = new File(Main.getInstance().getDataFolder(), "logs.txt");
			FileManager.createFile(l);
			PrintWriter p = new PrintWriter(new FileWriter(l, true));
			p.println(message);
			p.close();
		} catch (IOException e) {
			Main.error("Error !");
			e.printStackTrace();
		}

	}
}
