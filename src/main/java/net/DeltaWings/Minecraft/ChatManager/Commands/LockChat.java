package net.DeltaWings.Minecraft.ChatManager.Commands;

import net.DeltaWings.Minecraft.ChatManager.Api.API;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class LockChat implements CommandExecutor {

    public boolean onCommand(CommandSender s, Command c, String t, String[] a) {
	    if (s instanceof Player && s.hasPermission("chat-manager.lockchat.lock") || s instanceof ConsoleCommandSender) API.lockchat();
        else s.sendMessage("perms usage"); //permission message
        return true;
    }


}
