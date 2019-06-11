package net.DeltaWings.Minecraft.ChatManager.Listeners;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;

import java.util.HashMap;

import org.bukkit.entity.Entity;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.entity.EntityDamageEvent.DamageCause;

public class DeathListener implements Listener {

	@EventHandler
	public void onPlayerDeath(PlayerDeathEvent e) {
		if(new Config("config", "events").getBoolean("enabled.death", true)) {
			Config msg = new Config("messages", "events");

			e.getEntity().getLastDamageCause();
			DamageCause cause = e.getEntity().getLastDamageCause().getCause();
			Entity killer = e.getEntity().getKiller();
			HashMap<String, String> replacements = new HashMap<>();
			replacements.put("[player]", e.getEntity().getName());
			if(killer != null) replacements.put("[killer]", killer.getName());
			replacements.put("[item]", e.getEntity().getDisplayName().replace("&", "§"));
			e.setDeathMessage(API.multiReplace(msg.getString("death.causes."+cause.toString().toLowerCase()), replacements));
		}
	}
}
