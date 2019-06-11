package net.DeltaWings.Minecraft.ChatManager.Custom;

import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Config {
	private final FileConfiguration config;
	private final File root = Main.getInstance().getDataFolder();
	private final File folder;
	private final File file;
	private final FileManager fm = new FileManager();

	public Config(String folder, String file) {
		this.folder = new File(folder);
		this.file = new File(root.toString() + File.separator + this.folder.toString() + File.separator + file + ".yml");
		config = YamlConfiguration.loadConfiguration(this.file);
	}

	public Boolean exist() {
		return file.exists();
	}

	public void set(String path, Object value) {
		config.set(path, value);
	}

	public void set(String path) {
		config.createSection(path);
	}

	public void header(String header) {
		config.options().header(header);
	}

	public int getInt(String path, Integer def) {
		return config.getInt(path, def);
	}

	public int getInt(String path) {
		return config.getInt(path);
	}

	public Double getDouble(String path, Double def) {
		return config.getDouble(path, def);
	}

	public Double getDouble(String path) {
		return config.getDouble(path);
	}

	public String getString(String path, String def) {
		return config.getString(path, def);
	}

	public String getString(String path) {
		return config.getString(path);
	}

	public Long getLong(String path) {
		return config.getLong(path);
	}

	public Long getLong(String path, Long def) {
		return config.getLong(path, def);
	}

	public void save() {
		try {
			config.save(file);
		} catch ( IOException e ) {
			e.printStackTrace();
		}
	}

	public ArrayList<String> getSection(String path) {
		return new ArrayList<>(config.getConfigurationSection(path).getKeys(false));
	}

	public List<String> getStringList(String path) {
		return config.getStringList(path);
	}

	public void create() {
		if(!exist()) {
			if(!root.exists()) root.mkdirs();
			File rfolder = new File(root.toString() + File.separator + folder.toString());
			if(!rfolder.exists()) rfolder.mkdirs();
			try {
				file.createNewFile();
			} catch ( IOException e ) {
				e.printStackTrace();
			}
		}
	}

	public boolean isSet(String path) {
		return config.isSet(path);
	}

	public boolean getBoolean(String path, Boolean def) {
		return config.getBoolean(path, def);
	}

	public boolean getBoolean(String path) {
		return config.getBoolean(path);
	}

	public void delete() {
		FileManager.delete(file);
	}
}