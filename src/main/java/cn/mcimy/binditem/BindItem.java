package cn.mcimy.binditem;

import cn.mcimy.binditem.command.BindCommand;
import cn.mcimy.binditem.listener.PlayerListener;
import cn.mcimy.binditem.util.ConfigUtil;
import org.bukkit.event.Listener;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.List;

public final class BindItem extends JavaPlugin implements Listener {

    public ItemStack item;
    public String message;
    public String message2;
    public String message3;
    public String message4;
    public String bindLore;
    public List<Integer> blackList;

    @Override
    public void onEnable() {
        saveDefaultConfig();
        ConfigUtil.load();
        getServer().getPluginManager().registerEvents(new PlayerListener(this), this);
        getCommand("binditem").setExecutor(new BindCommand(this));
    }

    public void addBindLore(ItemStack clickItem, List<String> lore, ItemMeta meta) {
        lore.add(bindLore);
        meta.setLore(lore);
        clickItem.setItemMeta(meta);
    }
}
