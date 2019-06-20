package cn.mcimy.binditem.util;

import cn.mcimy.binditem.BindItem;

public class ConfigUtil {

    public static void load() {
        BindItem plugin = BindItem.getPlugin(BindItem.class);
        plugin.item = plugin.getConfig().getItemStack("item");
        plugin.message = plugin.getConfig().getString("message");
        plugin.message2 = plugin.getConfig().getString("message2");
        plugin.message3 = plugin.getConfig().getString("message3");
        plugin.message4 = plugin.getConfig().getString("message4");
        plugin.bindLore = plugin.getConfig().getString("bindLore");
        plugin.blackList = plugin.getConfig().getIntegerList("blackList");
    }
}
