package net.DeltaWings.Minecraft.ChatManager.Scheduler;

import net.DeltaWings.Minecraft.ChatManager.Api.API;
import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.Bukkit;

import java.util.List;
import java.util.Objects;

public class Scheduler {

	private final Config clearchat = new Config("config", "clearchat");
	private final Config broadcast = new Config("config", "broadcast");
	private final List<String> bdlist = broadcast.getStringList("auto-broadcast.messages");
	private Integer ccobjective = 0, bdobjective = 0, bdactual = 0;

	public Scheduler() {
		Bukkit.getScheduler().scheduleSyncRepeatingTask(Main.getInstance(), () -> {
		if(clearchat.getBoolean("auto-clear-chat.enabled", false)) {
			if( Objects.equals(ccobjective, clearchat.getInt("auto-clear-chat.time", 30) * 60) ) {
				API.clearchat("Console", null);
				ccobjective = 0;
			} else {
				ccobjective++;
			}
		}
		if(broadcast.getBoolean("auto-broadcast.enabled", false)) {
			if(Objects.equals(bdobjective, broadcast.getInt("auto-broadcast.time", 30) * 60)) {
				Main.log(bdactual + " et " + bdlist.size());
				API.broadcast("Console", bdlist.get(bdactual));
				bdactual++;
				if( Objects.equals(bdactual, bdlist.size()) ) {
					bdactual = 0;
				}
				bdobjective = 0;
			} else {
				bdobjective++;
			}
		}
		},20L,20L);
	}
}