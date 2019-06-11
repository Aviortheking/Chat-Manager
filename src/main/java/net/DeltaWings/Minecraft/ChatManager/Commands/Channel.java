package net.DeltaWings.Minecraft.ChatManager.Commands;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import net.DeltaWings.Minecraft.ChatManager.Custom.Config;
import net.DeltaWings.Minecraft.ChatManager.Custom.FileManager;
import net.DeltaWings.Minecraft.ChatManager.Main;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;

public class Channel implements CommandExecutor {

	private Config a = new Config("messages", "channel");
	private Config b = new Config("config", "channel");
	private FileManager c = new FileManager();
	private Main i = Main.getInstance();
	private String j = File.separator;
	private final File d = new File(i.getDataFolder() + j + "channels");
	private Config h = new Config("messages", "global");

	@Override
	public boolean onCommand(CommandSender e, Command f, String label, String[] g) {
		if ( f.getName().equalsIgnoreCase("channel") ) {
// /channels list
			if ( g.length == 1 && g[0].equalsIgnoreCase("list") ) {
				if ( e instanceof Player ) {
					if ( e.hasPermission("chat-manager.channel.list") ) a(e);
					else e(e);
				} else a(e);
// /channel create <channel> WORK
			} else if ( g.length == 2 && g[0].equalsIgnoreCase("create") ) {
				if ( e instanceof Player ) {
					if ( e.hasPermission("chat-manager.channel.create") ) b(g, e);
					else e(e);
				} else e.sendMessage(a.getString("console-cant"));
// /channel leave <channel> WORK
			} else if ( g.length == 2 && g[0].equalsIgnoreCase("leave") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.leave")) c(g, e);
					else e(e);
				} else e.sendMessage(a.getString("console-cant"));
// /channel join <channel> WORK
			} else if ( g.length == 2 && g[0].equalsIgnoreCase("join") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.join")) d(g, e);
					else e(e);
				} else e.sendMessage(a.getString("console-cant"));
// /channel invite <player> <channel> need multiplayers p isn't online et invited messages
			} else if ( g.length == 3 && g[0].equalsIgnoreCase("invite") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.invite")) f(g, e);
					else e(e);
				} else e.sendMessage(a.getString("console-cant"));
// /channel disband <channel> WORK
			} else if ( g.length == 2 && g[0].equalsIgnoreCase("disband") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.disband")) g(g, e);
					else e(e);
				} else g(g, e);
// /channel kick <player> <channel> WORK
			} else if ( g.length == 3 && g[0].equalsIgnoreCase("kick") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.kick")) h(g, e);
					else e(e);
				} else h(g, e);
// /channel promote <player> <channel> WORK
			} else if ( g.length == 3 && g[0].equalsIgnoreCase("promote") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.promote")) i(g, e);
					else e(e);
				} else e.sendMessage(a.getString("console-cant"));
// /channel demote <player> <channel> WORK
			} else if ( g.length == 3 && g[0].equalsIgnoreCase("demote") ) {
				if(e instanceof Player) {
					if(e.hasPermission("chat-manager.channel.demote")) j(g, e);
					else e(e);
				} else e.sendMessage(a.getString("console-cant"));
// / channel infos <channel>
			} else if ( g.length == 2 && g[0].equalsIgnoreCase("infos") ) {
				if ( e.hasPermission("chat-manager.channel.info") ) {
					e.sendMessage("Work In Progress");
						/*if(config.getStringList("channels-list").contains(args[1])){
							/*
							List<String> avadmins = config.getStringList("channels."+args[1]+"admins");
							String admins = "";
							for (int i = 0; i < avadmins.size(); i++) {
								String avadminst = avadmins.get(i);
								admins = String.valueOf(admins) + avadminst + " ";
							}
							List<String> avusers = config.getStringList("channels."+args[1]+"users");
							String users = "";
							for (int i = 0; i < avusers.size(); i++) {
								users = String.valueOf(users) + avusers.get(i)+ " ";
							}

							String admins = "";
							String users = "";
							String msg = "§fChannel Infos :\nName : "+args[1]+"\n§fprefix : "+config.getString("channels."+args[1]+".prefix")+"\n§fAdmins : "+admins+"\n§fUsers : "+users+"\n§fStatus : "+config.getString("channels."+args[1]+".status");
							p.sendMessage(msg.replace("&", "§"));
						}*/
				} else e.sendMessage(new Config("messages", "global").getString("permission").replace("&", "§"));

// /channel help [<numero>]
			} else if ( g.length == 2 && g[0].equalsIgnoreCase("help") ) {
				//help
				e.sendMessage("Work In Progress");
// /channel admin
			} else if ( g.length == 1 && g[0].equalsIgnoreCase("admin") ) {
				e.sendMessage("Work In Progress");
					/*if(pl.getPermissionsForCmd(p, cmd, args)){
						//join
						if(args[1].equalsIgnoreCase("join")){
							//check si le groupe existe 
								//check si il fait déjà parti du groupe
									//check si c'est le même rang que le sien
										//message tu es déjà dans ce channel et ce rang
									//vire de son rang et on le fout dans l'autre
								//rejoindre le groupe
							if(config.getStringList("channels-list").contains(args[1])){
								if(args[2].equalsIgnoreCase("admins")||args[2].equalsIgnoreCase("users")){
									if(config.getStringList("channels."+args[1]+".admins").contains(p.getName()) || config.getStringList("channels."+args[2]+".users").contains(p.getName())){
										if(config.getStringList("channels."+args[1]+"."+args[2]).contains(p.getName())){
											//message tu es déjà dans ce channel
											p.sendMessage(config.getString("messages.channel.already-in").replace("&", "§"));
										}else{
											List<String> list = config.getStringList("channels."+args[1]+"."+args[2]);
											list.add(p.getName());
											config.set("channels."+args[1]+"."+args[2], list);
											//message tu as rejoins le channel en tant que args [2]
											p.sendMessage(config.getString("messages.channel.channel-joined").replace("&", "§"));
											if(args[2].equalsIgnoreCase("admins")){
												List<String> remove = config.getStringList("channels."+args[1]+".users");
												for ( int i = 0;  i < remove.size(); i++){
													String tempNamea = remove.get(i);
													if(tempNamea.equals(p.getName())){
														remove.remove(i);
													}
												}
											}else{
												List<String> removea = config.getStringList("channels."+args[1]+".admins");
												for ( int ia = 0;  ia < removea.size(); ia++){
													String tempName = removea.get(ia);
													if(tempName.equals(p.getName())){
														removea.remove(ia);
													}
												}
											}
										}
									}else{
										List<String> list = config.getStringList("channels."+args[1]+"."+args[2]);
										list.add(p.getName());
										config.set("channels."+args[1]+"."+args[2], list);
										//message tu as rejoins le channel
										p.sendMessage(config.getString("messages.channel.channel-joined").replace("&", "§"));

									}
								}else{
									//il y a uniquement deux rangs ! (admins, users)
									p.sendMessage(config.getString("messages.badly-written").replace("&", "§"));

								}
							}else{
								//message ce groupe n'existe pas
								p.sendMessage(config.getString("messages.channel.dont-exist").replace("&", "§"));

							}
						}else if(args[1].equalsIgnoreCase("disband")){
							//disband
							config.set("channels."+args[1]+"status", null);
							config.set("channels."+args[1]+".prefix", null);
							config.set("channels."+args[1]+".users", null);
							config.set("channels."+args[1]+".admins", null);
							config.set("channels."+args[1], null);
							//message channel disbanded
							p.sendMessage(config.getString("messages.channel.channel-disbanded").replace("&", "§"));

						}else{
							//Message Badly Written
							p.sendMessage(config.getString("messages.badly-written").replace("&", "§"));
						}
					}*/
			} else {
				e.sendMessage(new Config("messages", "global").getString("badly-written").replace("&", "§"));
			}
		}
		return true;
	}

	/*
	//local vars
	k : channels list message
	l : CommandSender
	m : for loop channel String
	n : for loop channel config
	 */
	private void a(CommandSender l) {
		StringBuilder k = new StringBuilder();
		if( FileManager.listFiles(d).size() != 0) {
			for (String m : FileManager.listFiles(d)) {
				Config n = new Config("channels", m.replace(".yml", ""));
				if (n.getStringList("users").contains(l.getName())) {
					k.append(b.getString("colors.user-name")).append(m.replace(".yml", "")).append(" ");
				} else if (n.getStringList("admins").contains(l.getName())) {
					k.append(b.getString("colors.admin-name")).append(m.replace(".yml", "")).append(" ");
				} else {
					k.append(b.getString("colors.default")).append(m.replace(".yml", "")).append(" ");
				}
			}
		}
		l.sendMessage(a.getString("list-start").replace("&", "§")+"\n"+ k.toString().replace("&", "§")+"\n"+a.getString("list-end").replace("&", "§"));
	}

	/*
	k = args
	l = CommandSender
	m = Channel
	 */
	private void b(String[] k, CommandSender l) {
		if(k.length != 2) l.sendMessage(a.getString("one-word-channel").replace("&", "§"));
		else {
			if(!d.exists()) FileManager.createFolder(d);
			Config m = new Config("channels", k[1]);
			if(m.exist()) l.sendMessage(a.getString("already-exist").replace("[channel]", k[1]).replace("&", "§"));
			else {
				m.create();
				m.set("users");
				List<String> n = new ArrayList<>();
				n.add(l.getName());
				m.set("admins", n);
				m.set("prefix", "[&8"+k[1]+"&f]");
				m.set("status", "open");
				m.save();
				l.sendMessage(a.getString("channel-created").replace("&", "§"));
			}
		}
	}

	/*
	k = commandSender
	 */
	private void e(CommandSender k) {
		k.sendMessage(h.getString("permission").replace("&", "§"));
	}

	/*
	k = args
	l = CommandSender
	m = Channel
	n = list
	o = for loop
	 */
	private void c(String[] k, CommandSender l) {
		if ( k.length != 2 ) l.sendMessage(a.getString("one-word-channel").replace("&", "§"));
		else {
			if ( FileManager.listFiles(d).contains(k[1] + ".yml") ) {
				Config m = new Config("channels", k[1]);
				if ( m.getStringList("admins").contains(l.getName()) ) {
					List<String> n = m.getStringList("admins");
					for ( int o = 0; o < n.size(); o++ ) {
						if ( n.get(o).equals(l.getName()) ) {
							n.remove(o);
							l.sendMessage(a.getString("deleted-from-channel").replace("&", "§"));
							if ( n.size() == 0 ) {
								FileManager.delete(d + j + k[1] + ".yml");
								l.sendMessage(a.getString("last-admin-delete").replace("&", "§"));
							} else {
								m.set("admins", n);
								m.save();
							}

						}
					}
				} else if ( m.getStringList("channels." + k[1] + ".users").contains(l.getName()) ) {
					List<String> n = m.getStringList("users");
					for ( int o = 0; o < n.size(); o++ ) {
						if ( n.get(o).equals(l.getName()) ) {
							n.remove(o);
							l.sendMessage(a.getString("deleted-from-channel").replace("&", "§"));
							m.set("users", n);
							m.save();
						}
					}
				} else l.sendMessage(a.getString("not-a-user").replace("&", "§"));
			} else l.sendMessage(a.getString("dont-exist").replace("&", "§"));
		}
	}

	/*
	k = args
	l = CommandSender
	m = Channel
	n = list
	o = for loop
	p = pconfig
	q = name list
    */
	private void d(String[] k, CommandSender l) {
		if ( FileManager.listFiles(d).contains(k[1] + ".yml") ) {
			Config m = new Config("channels", k[1]);
			if ( !m.getString("admins").contains(l.getName()) && !m.getString("users").contains(l.getName()) ) {
				if ( m.getString("status").equalsIgnoreCase("public") ) {
					List<String> n = m.getStringList("users");
					n.add(l.getName());
					m.set("users", n);
					m.save();
					l.sendMessage(a.getString("channel-joined").replace("&", "§"));
				} else {
					Config p = new Config("players", l.getName());
					if ( p.getStringList("invite").contains(k[1]) ) {
						List<String> q = p.getStringList("invite");
						for ( int ic = 0; ic < q.size(); ic++ ) if ( q.get(ic).equals(k[1]) ) q.remove(ic);
						p.set("invite", q);
						p.save();
						List<String> n = m.getStringList("users");
						n.add(l.getName());
						m.set("users", n);
						m.save();
						l.sendMessage(a.getString("channel-joined").replace("&", "§"));
					} else l.sendMessage(a.getString("cant-join").replace("&", "§"));
				}
			} else l.sendMessage(a.getString("already-in").replace("&", "§"));
		}
	}

	/*
	k = args
	l = CommandSender
	m = players
	n = player config
	o = list
	 */
	private void f(String[] k, CommandSender l) {
		if ( k.length != 3 ) l.sendMessage(h.getString("badly-written").replace("&", "§"));
		else {
			if ( new Config("channels", k[2]).getStringList("users").contains(l.getName()) || new Config("channels", k[2]).getStringList("admins").contains(l.getName()) ) {
				for ( Player m : Bukkit.getServer().getOnlinePlayers() ) {
					if ( m.getName().equalsIgnoreCase(k[1]) ) {
						Config n = new Config("players", m.getName());
						if ( !n.exist() ) n.create();
							List<String> o = n.getStringList("invite");
							o.add(k[2]);
							n.set("invite", o);
							n.save();
							l.sendMessage(a.getString("you-invited").replace("[player]", k[1]).replace("&", "§"));
							m.sendMessage(a.getString("invited").replace("[channel]", k[2]).replace("[player]", k[1]).replace("&", "§"));
					}
				}
			} else l.sendMessage(a.getString("not-in-channel").replace("&", "§"));
		}
	}

	/*
	k = args
	l = CommandSender
	 */
	private void g(String[] k, CommandSender l) {
		if ( k.length == 2 ) {
			if ( FileManager.listFiles(d).contains(k[1] + ".yml") ) {
				if ( l instanceof ConsoleCommandSender ||new Config("channels", k[1]).getStringList("admins").contains(l.getName()) ) {
					FileManager.delete(d + j + k[1] + ".yml");
					l.sendMessage(a.getString("channel-disbanded").replace("&", "§"));
				} else l.sendMessage(a.getString("cant-disband").replace("&", "§"));
			} else l.sendMessage(a.getString("dont-exist").replace("&", "§"));
		} else l.sendMessage(a.getString("one-word-channel").replace("&", "§"));
	}

	/*
	k = args
	l = CommandSender
	m = channel config
	n = users list
	o = loop
	*/
	private void h(String[] k, CommandSender l) {
		if ( k.length != 3 ) l.sendMessage(new Config("messages", "global").getString("badly-written").replace("&", "§"));
		else if ( FileManager.listFiles(d).contains(k[2] + ".yml") ) {
			Config m = new Config("channels", k[2]);
			if (l instanceof ConsoleCommandSender || m.getStringList("admins").contains(l.getName()) ) {
				if (!(l instanceof ConsoleCommandSender) && m.getStringList("admins").contains(k[1]) ) l.sendMessage(a.getString("cant-do-admin").replace("&", "§"));
				else if ( m.getStringList("users").contains(k[1]) ) {
					List<String> n = m.getStringList("users");
					for ( int o = 0; o < n.size(); o++ ) if ( n.get(o).equals(k[1]) ) n.remove(o);
					m.set("users", n);
					m.save();
					l.sendMessage(a.getString("kicked").replace("[player]", k[1]).replace("&", "§"));
				} else l.sendMessage(a.getString("not-in-your-cannel").replace("[player]", k[1]).replace("&", "§"));
			} else l.sendMessage(a.getString("cant-kick-if-not-admin").replace("&", "§"));
		} else l.sendMessage(a.getString("dont-exist").replace("&", "§"));
	}

	/*
	k = args
	l = CommandSender
	m = channel config
	n = users list
	o = loop
	*/
	private void i(String[] k, CommandSender l) {
		if ( k.length != 3 ) l.sendMessage(new Config("messages", "global").getString("badly-written").replace("&", "§"));
		else if ( FileManager.listFiles(d).contains(k[2] + ".yml") ) {
			Config m = new Config("channels", k[2]);
			if ( m.getStringList("admins").contains(l.getName()) ) {
				if ( m.getStringList("users").contains(k[1]) ) {
					List<String> n = m.getStringList("users");
					for ( int o = 0; o < n.size(); o++ ) if ( n.get(o).equals(k[1]) ) n.remove(o);
					m.set("users", n);
					List<String> admins = m.getStringList("admins");
					admins.add(k[1]);
					m.set("admins", admins);
					m.save();
					l.sendMessage(a.getString("promoted").replace("[player]", k[1]).replace("&", "§"));
				} else l.sendMessage(a.getString("player-is-not-channel").replace("[player]", k[1]).replace("&", "§"));
			} else l.sendMessage(a.getString("cant-promote").replace("&", "§"));
		} else l.sendMessage(a.getString("dont-exist").replace("&", "§"));
	}

	/*
	k = args
	l = CommandSender
	m = channel config
	n = users list
	o = loop
	*/
	private void j(String[] k, CommandSender l) {
		if ( k.length != 3 ) l.sendMessage(new Config("messages", "global").getString("badly-written").replace("&", "§"));
		else if ( FileManager.listFiles(d).contains(k[2] + ".yml") ) {
			Config m = new Config("channels", k[2]);
			if ( m.getStringList("admins").contains(l.getName()) ) {
				if ( m.getStringList("admins").contains(k[1]) ) {
					List<String> n = m.getStringList("admins");
					for ( int o = 0; o < n.size(); o++ ) {
						if ( n.get(o).equals(k[1]) ) {
							n.remove(o);
						}
					}
					if ( n.size() == 0 ) {
						FileManager.delete(d + j + k[2] + ".yml");
						l.sendMessage(a.getString("last-admin-delete").replace("&", "§"));
					} else {
						m.set("admins", n);
						List<String> admins = m.getStringList("users");
						admins.add(k[1]);
						m.set("users", admins);
						m.save();
						l.sendMessage(a.getString("demoted").replace("[player]", k[1]).replace("&", "§"));
					}
				} else l.sendMessage(a.getString("is-not-user").replace("&", "§"));
			} else l.sendMessage(a.getString("cant-do-admin").replace("&", "§"));
		} else l.sendMessage(a.getString("dont-exist").replace("&", "§"));
	}
	/*
	gen var
	a : new Config("messages", "channel"); // ou en général
	b : new Config("config", "channel");
	c : new FileManager();
	d : channels folder
	e : CommandSender
	f : Command
	g : args
	h : new Config("messages", "global");
	i : Main
	j : File Separator

	methods :
	a : list
	b : create
	c : leave
	d : join
	e : permission message
	f : invite
	g : disband
	h : kick
	i : promote
	j : demote
	*/
}