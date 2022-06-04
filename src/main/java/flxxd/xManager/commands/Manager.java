package flxxd.xManager.commands;

import com.google.common.collect.Lists;
import flxxd.xManager.Main;
import flxxd.xManager.helpers.AbstractCommand;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import java.util.List;

public class Manager extends AbstractCommand {
    public Manager () {
        super("manager");
    }

    RegisteredServiceProvider<LuckPerms> provider = Bukkit.getServicesManager().getRegistration(LuckPerms.class);

    @Override
    public void execute (CommandSender sender, String label, String[] args) {
        Player send3r = Bukkit.getPlayer(sender.getName());
        assert send3r != null;

        if (args.length == 0) {
            send3r.sendActionBar(Main.getValue("actionBars.global.noArguments"));
            return;
        }

        if (args[0].equalsIgnoreCase("get") && args[1].equalsIgnoreCase("uuid")) {
            if (send3r.hasPermission("xManager.get")) {
                if (args.length != 3) {
                    String info = Main.getValue("actionBars.get.playerNotSpecified");

                    send3r.sendActionBar(info);
                    return;
                }

                LuckPerms api = provider.getProvider();
                User user = api.getUserManager().getUser(args[2]);

                if (user == null) {
                    String info = Main.getValue("actionBars.get.playerNotFound")
                            .replace("%user%", args[2]);

                    send3r.sendActionBar(info);
                    return;
                }

                String info = Main.getValue("chat.get.uuid")
                        .replace("[", "").replace("]", "")
                        .replace("%user%", user.getUsername())
                        .replace("%uuid%", user.getUniqueId().toString())
                        .replace("%group%", user.getPrimaryGroup());

                send3r.sendMessage(info);
                return;
            }
        }
        if (args[0].equalsIgnoreCase("get") && args[1].equalsIgnoreCase("group")) {
            if (send3r.hasPermission("xManager.get")) {
                if (args.length != 3) {
                    String info = Main.getValue("actionBars.get.playerNotSpecified");

                    send3r.sendActionBar(info);
                    return;
                }

                LuckPerms api = provider.getProvider();
                User user = api.getUserManager().getUser(args[2]);

                if (user == null) {
                    String info = Main.getValue("actionBars.get.playerNotFound")
                            .replace("%user%", args[2]);

                    send3r.sendActionBar(info);
                    return;
                }

                String info = Main.getValue("chat.get.group")
                        .replace("[", "").replace("]", "")
                        .replace("%user%", user.getUsername())
                        .replace("%group%", user.getPrimaryGroup());

                send3r.sendMessage(info);
                return;
            }
        }
        if (args[0].equalsIgnoreCase("get")) {
            if (send3r.hasPermission("xManager.get")) {
                LuckPerms api = provider.getProvider();
                User user = api.getUserManager().getUser(args[1]);

                if (user == null) {
                    String info = Main.getValue("actionBars.get.playerNotFound")
                            .replace("%user%", args[1]);

                    send3r.sendActionBar(info);
                    return;
                }

                String info = Main.getValue("chat.get.userInfo")
                        .replace("[", "").replace("]", "")
                        .replace("%user%", user.getUsername())
                        .replace("%uuid%", user.getUniqueId().toString())
                        .replace("%group%", user.getPrimaryGroup());

                send3r.sendMessage(info);
                return;
            }
        }

        if (args[0].equalsIgnoreCase("set") && args[1].equalsIgnoreCase("group")) {
            if (send3r.hasPermission("xManager.set")) {
                if (args.length != 4) {
                    String info = Main.getValue("actionBars.set.playerNotSpecified");

                    send3r.sendActionBar(info);
                    return;
                }

                LuckPerms api = provider.getProvider();
                User user = api.getUserManager().getUser(args[2]);

                if (user == null) {
                    String info = Main.getValue("actionBars.set.playerNotFound")
                            .replace("%user%", args[2]);

                    send3r.sendActionBar(info);
                    return;
                }

                String info = Main.getValue("chat.set.group")
                        .replace("[", "").replace("]", "")
                        .replace("%user%", user.getUsername())
                        .replace("%group%", args[3]);

                user.setPrimaryGroup(args[3]);
                api.getUserManager().saveUser(user);

                send3r.sendMessage(info);
                return;
            }
        }

        send3r.sendActionBar(Main.getValue("actionBars.global.unknownCommand").replace("&", "§"));
    }

    public List<String> complete (CommandSender sender, String[] args) {
        if (args.length == 1) return Lists.newArrayList("get", "set");
        if (args.length == 2 && args[0].equalsIgnoreCase("get")) return Lists.newArrayList("uuid", "group");
        if (args.length == 2 && args[0].equalsIgnoreCase("set")) return Lists.newArrayList("group", "permission");

        return Lists.newArrayList();
    }
}
