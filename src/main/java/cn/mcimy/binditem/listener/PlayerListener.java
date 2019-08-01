package cn.mcimy.binditem.listener;

import cn.mcimy.binditem.BindItem;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.ArrayList;
import java.util.List;

public class PlayerListener implements Listener {

    private BindItem plugin;

    public PlayerListener(BindItem plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event) {
        if (event.getClickedInventory().getType() == InventoryType.PLAYER) {
            if (event.getClick() == ClickType.LEFT && event.getSlotType() != InventoryType.SlotType.OUTSIDE) {
                Player player = (Player) event.getWhoClicked();
                ItemStack clickItem = player.getInventory().getItem(event.getSlot());
                if (event.getCursor().isSimilar(plugin.item) && clickItem != null) {
                    bind(event, player, clickItem);
                }
            }
        }
    }

    private void bind(InventoryClickEvent event, Player player, ItemStack clickItem) {
        event.setCancelled(true);
        ItemMeta meta = clickItem.getItemMeta();
        List<String> lore = meta.getLore() == null ? new ArrayList<>() : meta.getLore();
        if (event.getCursor().getAmount() != 1) {
            player.sendMessage(plugin.message2);
            return;
        } else if (lore.contains(plugin.bindLore)) {
            player.sendMessage(plugin.message3);
            return;
        } else if (plugin.blackList.contains(clickItem.getTypeId())) {
            player.sendMessage(plugin.message4);
            return;
        }
        plugin.addBindLore(clickItem, lore, meta);
        event.setCursor(null);
        player.sendMessage(plugin.message);
    }



    @EventHandler
    public void onPlayerDeath(PlayerDeathEvent event) {

        boolean hasBindItem = false;
        //严重BUG: 不使用迭代器移除的话会少遍历对象 - a39
        for (int i = 0; i < event.getDrops().size(); i++) {
            ItemStack item = event.getDrops().get(i);
            if (item.hasItemMeta() && item.getItemMeta().hasLore()) {
                if (item.getItemMeta().getLore().contains(plugin.bindLore)) {
                    hasBindItem = true;
                    event.getDrops().remove(item);
                }
            }
        }
        if (hasBindItem) {
            event.setKeepInventory(true);
            for (int i = 0; i < event.getDrops().size(); i++) {
                ItemStack item = event.getDrops().get(i);
                // 考虑其他插件对掉落的处理/增加权限使部分用户不掉落
                // 强制掉落不好 —— 754503921
                event.getEntity().getWorld().dropItem(event.getEntity().getLocation(), item);
                event.getEntity().getInventory().removeItem(item);
            }
        }
    }
}
