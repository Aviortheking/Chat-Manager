package net.DeltaWings.Minecraft.ChatManager;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.DeltaWings.Minecraft.ChatManager.Commands.*;
import net.DeltaWings.Minecraft.ChatManager.Custom.*;
import net.DeltaWings.Minecraft.ChatManager.Listeners.*;
import net.DeltaWings.Minecraft.ChatManager.Scheduler.Scheduler;

import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginDescriptionFile;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;


public class Main extends JavaPlugin {

	private static Main instance;
	private PluginDescriptionFile desc;

	public static void log(String message) {
		Main.getInstance().getLogger().info(message);
	}

	public static void debug(String message) {
		if(new Config("config", "global").getBoolean("debug", false))Main.getInstance().getLogger().info("[Debug] " + message);
	}

	public static void error(String message) {
		Main.getInstance().getLogger().severe(message);
	}


	public static Main getInstance() {
		return instance;
	}

	@Override
	public void onEnable() {
		desc = getDescription();
		instance = this;
		PluginManager pm = getServer().getPluginManager();
		//Commands
		getCommand("StaffChat").setExecutor(new StaffChat());
		getCommand("StaffSwitch").setExecutor(new StaffSwitch());
		getCommand("Channel").setExecutor(new Channel());
		getCommand("Chat").setExecutor(new Chat());
		getCommand("Broadcast").setExecutor(new Broadcast());
		getCommand("Clearchat").setExecutor(new Clearchat());
		getCommand("Chat-Manager").setExecutor(new ChatManager());
		getCommand("LockChat").setExecutor(new LockChat());
		getCommand("Mute").setExecutor(new Mute());
		getCommand("Unmute").setExecutor(new Unmute());
		getCommand("Mutelist").setExecutor(new Mutelist());

		//Listeners
		pm.registerEvents(new PlayerLoginLogoutListener(), this);
		pm.registerEvents(new ChatListener(), this);
		pm.registerEvents(new DeathListener(), this);

		//make config files
		genconfig();

		//Start Schedulers
		new Scheduler();

		//new Notifier(25929, desc.getVersion());
		//Metrics
		Metrics metrics = new Metrics(this);
		metrics.addCustomChart(new Metrics.SimplePie("auto_broadcast") {
			@Override
			public String getValue() {
				return ""+new Config("config", "broadcast").getBoolean("auto-broadcast.enabled", false);
			}
		});

		metrics.addCustomChart(new Metrics.SimplePie("auto_clearchat") {
			@Override
			public String getValue() {
				return ""+new Config("config", "clearchat").getBoolean("auto-clear-chat.enabled", false);
			}
		});

		metrics.addCustomChart(new Metrics.SingleLineChart("muted_players") {
			@Override
			public int getValue() {
				Integer r = 0;
				try {
					List<String> files = FileManager.listFiles(new File(getDataFolder() + File.separator + "players"));
					for(String file : files) {
						if(new Config("players", file.replace(".yml", "")).getBoolean("mute.chat")) r++;
					}
				} catch ( Exception e ) {
					e.printStackTrace();
				}

				return r;
			}
		});
		metrics.addCustomChart(new Metrics.SimplePie("name_customization") {
			@Override
			public String getValue() {
				return new Config("config", "global").getBoolean("name.enabled", false) + "";
			}
		});

		log("Successfully enabled");
	}

	private void genconfig() {
		Config c;
		c = new Config("config", "global");
		Boolean ver = !c.getString("version", "0.0.0").equalsIgnoreCase(desc.getVersion());
		if(ver) {
			log("Because it's a new update we will now archive all your configuration and generate a new one.");
			log("Before repasting into the new files check the changelogs to see what files have changed !");
			FileManager.archive(Main.getInstance().getDataFolder().toString(), Main.getInstance().getDataFolder().toString() + File.separator + "Backup-" + c.getString("version", "unknown") + ".zip");
		}

		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.header("Please don't touch the version or you will break some functions");
			c.set("debug", false);
			c.set("format.chat", "[prefix] [player] [suffix] > [message]");
			c.set("name.enabled", true);
			c.set("name.color", "&9&o");
			c.set("version", desc.getVersion());
			c.save();
		}
		
		c = new Config("config", "prefix");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.header("More infos on https://gitlab.com/Delta-Wings/Chat-Manager/snippets/1574373");
			c.set("prefix");
			c.set("suffix");
			c.set("name-color");
			c.save();
		}

		c = new Config("messages", "lockchat");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.header("[state] stand for chat state (locked for locked chat, unlocked for unlocked chat)");
			c.set("locked", "&8 >&c The chat is locked !");
			c.set("unlocked", "&8 >&c The chat has been unlocked !");
			c.set("ignored", "&8 >&c The chat is locked but you can speak !");
			c.set("already", "&8 >&c Chat is already [state] !");
			c.save();
		}

		c = new Config("messages", "channel");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("dont-exist", "&8 >&c The channel don't exist");
			c.set("list-start", "&8 >&f Channels list(&bBlue&f = user &cRed&f = Admin");
			c.set("list-end", "&8 >&c End");
			c.set("already-exist", "&8 >&c There is already a channel named \"[channel]\"");
			c.set("channel-created", "&8 >&c The channel has been created !");
			c.set("one-word-channel", "&8 >&c Your channel name must be one word");
			c.set("no-chanel-name", "&8 >&c You need to tell the channel name !");
			c.set("deleted-from-channel", "&8 >&c You have been deleted from the channel");
			c.set("last-admin-delete", "&8 >&c Because you were the last admin. The channel has been deleted too");
			c.set("channel-joined", "&8 >&c You have successfully joined the channel !");
			c.set("cant-join", "&8 >&c You can't join this channel");
			c.set("already-in", "&8 >&c You are already in this channel");
			c.set("no-channel-name", "&8 >&c There is no channels with this name");
			c.set("you-invited", "&8 >&c You have successfully invited [player]");
			c.set("channel-disbanded", "&8 >&c You have successfully disbanded the channel");
			c.set("cant-disband", "&8 >&c You can't disband this channel because you are not an admin of this channel");
			c.set("cant-kick-admin", "&8 >&c You can't kick an other admin");
			c.set("kicked", "&8 >&c [player] has been kicked from the channel");
			c.set("not-in-your-channel", "&8 >&c [player] is not on your channel");
			c.set("cant-do-if-not-admin", "&8 >&c You can't do that's if you aren't a channel admin");
			c.set("promoted", "&8 >&c [player] have been promoted");
			c.set("player-is-not-channel", "&8 >&c [player] is not in the channel");
			c.set("cant-promoted", "&8 >&c You can only promote if you are an admin of a channel");
			c.set("demoted", "&8 >&c [player] have been demoted");
			c.set("is-not-user", "&8 >&c the player is not an admin of this channel");
			c.set("cant-message", "&8 >&c You can't send messages in this channel");
			c.set("not-in-channel", "&8 > &c You aren't in this channel");
			c.set("invited", "&8 >&c You have been invited in the channel : [channel]");
			c.set("one-word-channel", "&8 >&c You have misspelled the channel name !");
			c.set("console-cant", "The Console can't do this command !");
			c.set("not-a-user", "&8 >&c You aren't a user of this channel");
			c.save();
		}

		c = new Config("config", "channel");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("colors.admin-name", "&c");
			c.set("colors.user-name", "&b");
			c.set("colors.default", "&f");
			c.set("chat.format", "[channel] [player] [message]");
			c.save();
		}

		c = new Config("messages", "global");
		if(!c.exist()) {
			c.create();
			c.set("permission", "&8 >&c You don't have the permission to do that !");
			c.set("badly-written", "&8 >&c You have badly written somethings");
			c.set("not-online", "&8 > &c The player isn't online");
			c.save();
		}

		c = new Config("messages", "switch");
		if(!c.exist() || ver) {
			c.create();
			c.set("switch", "&8 >&c You have changed your receive setting to \"[state]\"");
			c.save();
		}

		Config sconf = new Config("messages", "staffchat");
		if(!sconf.exist() || ver) {
			sconf.create();
			sconf.set("no-message", "&8 >&c You have to put a message after the command");
			sconf.save();
		}

		c = new Config("config", "staffchat");
		if(!c.exist() || ver) {
			c.create();
			c.set("colors.player", "&c");
			c.set("colors.default", "&f");
			c.set("chat.format", "&8[&cStaffchat&8] [player] [message]");
			c.save();
		}

		c = new Config("config", "lockchat");
		if(!c.exist() || ver) {
			c.create();
			c.set("locked", "The chat is now locked");
			c.set("unlocked", "The chat is now unlocked");
			c.save();
		}

		c = new Config("config", "clearchat");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.header("Time in minutes");
			c.set("lines-to-clear", 200);
			c.set("auto-clear-chat.enabled", true);
			c.set("auto-clear-chat.time", 30);
			c.save();
		}

		c = new Config("messages", "clearchat");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("clear", "&8 >&c Your chat has been cleared by [cleaner]");
			c.save();
		}

		c = new Config("config", "broadcast");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("format", "<Broadcast> [player]&8 > &c[message]");
			c.set("auto-broadcast.enabled", true);
			List<String> t = new ArrayList<>();
			t.add("&cWelcome new players !");
			c.set("auto-broadcast.messages", t);
			c.set("auto-broadcast.time", 10);
			c.save();
		}

		c = new Config("config", "mute");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("enabled", true);
			c.set("default-reason", "You did something bad !");
			c.save();
		}

		c = new Config("messages", "mute");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("muted.self", "You are muted ! Reason : [reason]");
			c.set("muted.other", "&8 >&c You have muted [player] with the reason [reason]");
			c.set("unmutted.self", "&8 >&c You have been unmutted");
			c.set("unmutted.other", "&8 >&c You have unmutted [player]");
			c.set("already-muted", "&8 >&c The player is already muted");
			c.set("player.not-found", "&8 >&c The player has not been found !");
			c.set("mutelist.top", "&8 >&c Mutelist&8 <");
			c.set("mutelist.object", "&c[player] &b[time] &areason : [reason]");
			c.set("mutelist.bottom", "&8 >&c Mutelist&8 <");
			c.save();
		}

		c = new Config("config", "events");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("enabled.join", true);
			c.set("enabled.death", true);
			c.set("enabled.motd", true);
			c.set("enabled.leave", true);
			c.save();
		}
		
		c = new Config("messages", "events");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.set("join", "&bWelcome [player] !");
			c.set("leave", "&cGoodbye [player] ! I'll miss you !");
			c.set("death.player", "&9[player] was killed from [killer] using [item]");
			c.set("death.mob", "&9 ahah ! [player] was killed from [mob]");
			List<String> t = new ArrayList<>();
			t.add("Yo");
			t.add("gg");
			c.set("motd", t);
			
			c.save();
		}
		
		/*
		c = new Config("messages", "joinleave");
		if(!c.exist() || ver) {
			c.delete();
			c.create();
			c.save();
		}
		*/
		
	}

	public void makeDefaultPlayerFile(Player p) {
		Config c = new Config("players", p.getName());
		if(!c.exist()) {
			c.create();
			c.set("switch", true);
			c.set("invite");
			c.set("mute.chat", false);
			c.set("mute.cmd", false);
			c.save();
		}
	}
}