package cn.mcimy.binditem.command;

import cn.mcimy.binditem.BindItem;
import cn.mcimy.binditem.util.ConfigUtil;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class BindCommand implements TabExecutor {

    private BindItem plugin;

    public BindCommand(BindItem plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length == 2) {
            Player player = Bukkit.getPlayerExact(args[1]);
            if (player == null) {
                sender.sendMessage("玩家不在线");
                return true;
            }
            switch (args[0]) {
                case "give": {
                    player.getInventory().addItem(plugin.item);
                    return true;
                }
                case "bind": {
                    if (player.getItemInHand() != null) {
                        ItemStack handItem = player.getItemInHand();
                        ItemMeta meta = handItem.getItemMeta();
                        List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
                        plugin.addBindLore(handItem, lore, meta);
                    } else {
                        sender.sendMessage("请让 "+player.getName()+" 手持物品");
                    }
                    return true;
                }
            }
            return false;
        } else if (args.length == 1 && args[0].equalsIgnoreCase("reload")){
            plugin.reloadConfig();
            ConfigUtil.load();
            sender.sendMessage("重载完成");
            return true;
        }
        return false;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length == 1) {
            return Collections.singletonList("give, bind, reload");
        }
        return null;
    }
}
