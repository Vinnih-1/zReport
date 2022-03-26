package kazumy.plugin.zreport.spigot.report.menu.itembuilder;

import lombok.val;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import java.util.Arrays;

public class ItemBuilder extends ItemStack {

    public ItemBuilder(Material material) {
        super(material);
    }

    public ItemBuilder(Material material, int amount) {
        super(material, amount);
    }

    public ItemBuilder(Material material, int amount, short damage) {
        super(material, amount, damage);
    }

    public ItemBuilder name(String name) {
        val meta = this.getItemMeta();
        meta.setDisplayName(name);
        this.setItemMeta(meta);
        return this;
    }

    public ItemBuilder lore(String... lore) {
        val meta = this.getItemMeta();
        meta.setLore(Arrays.asList(lore));
        this.setItemMeta(meta);
        return this;
    }
}
