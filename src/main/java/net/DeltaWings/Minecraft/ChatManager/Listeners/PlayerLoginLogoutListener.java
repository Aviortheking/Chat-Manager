package net.DeltaWings.Minecraft.ChatManager.Listeners;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class PlayerLoginLogoutListener implements Listener {

	@EventHandler
	public void onPlayerJoin(PlayerJoinEvent e) {
		Config joinleave = new Config("config", "events");
		if(joinleave.getBoolean("enabled.join", true)) e.setJoinMessage(new Config("messages", "events").getString("join").replace("[player]", e.getPlayer().getName()).replace("&", "§"));
		else e.setJoinMessage(null);
		if(joinleave.getBoolean("enabled.motd", false)) for(String line : new Config("messages", "joinleave").getStringList("motd")) e.getPlayer().sendMessage(line.replace("&", "§"));
		if (API.isChatLocked()) e.getPlayer().sendMessage(new Config("messages", "lockchat").getString("locked").replace("&", "§"));
	}

	@EventHandler
	public void onPlayerLeave(PlayerQuitEvent e) {
		if(new Config("config", "events").getBoolean("enabled.leave", true)) e.setQuitMessage(new Config("messages", "events").getString("leave").replace("[player]", e.getPlayer().getName()).replace("&", "§"));
		else e.setQuitMessage(null);
	}

}
