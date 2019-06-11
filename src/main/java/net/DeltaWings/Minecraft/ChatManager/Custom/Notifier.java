package net.DeltaWings.Minecraft.ChatManager.Custom;

import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;

public class Notifier {

    public Notifier(Integer r, String b) {
        Bukkit.getScheduler().scheduleSyncDelayedTask( Main.getInstance(), () -> {
            try {
                HttpURLConnection c = (HttpURLConnection) new URL("http://www.spigotmc.org/api/general.php").openConnection();
                c.setDoOutput(true);
                c.setRequestMethod("POST");
                try (OutputStream o = c.getOutputStream()) {
                    o.write(("key=98BE0FE67F88AB82B4C197FAF1DC3B69206EFDCC4D3B80FC83A00037510B99B4&resource=" + r).getBytes("UTF-8"));
                }
                String v;
                try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(c.getInputStream()))) {
                    v = bufferedReader.readLine();
                }
                if (v.equalsIgnoreCase(b)) Main.log("No new Version version available ! Thanks for using our plugin.");
                else {
                    Main.log("New version available ! Version : " + v);
                    for (Player p : Bukkit.getServer().getOnlinePlayers()) if (p.hasPermission("chat-manager.admin.notification")) p.sendMessage("§8[§cChat§8-§cManager§8]§f New version available ! Version : " + v);
                }
            } catch (Exception e) {
                Main.log(e.getMessage());
                Main.log("");
                Main.log("Please contact the developper and give him the code before.");
                for(Player p : Bukkit.getServer().getOnlinePlayers()) if(p.hasPermission("chat-manager.admin.notification")) p.sendMessage("§8[§cChat§8-§cManager§8]§f Error while trying to check for updates ! Please look at the console for more informations.");
            }
        }, 0L);
    }
}