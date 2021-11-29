package kazumy.plugin.zreport.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import kazumy.plugin.zreport.inventories.base.BaseInventory;
import kazumy.plugin.zreport.inventories.utils.ItemBuilder;
import kazumy.plugin.zreport.report.user.User;
import lombok.Data;
import lombok.val;

@Data(staticConstructor = "of")
public class RepointInventory implements BaseInventory {
	
	private final Player player;
	private final User user;

	@Override
	public void onClick(InventoryClickEvent event) {	
	}

	@Override
	public Inventory buildInventory() {
		val inventory = Bukkit.createInventory(null, 9 * 5, "§8Menu: Pontos de Report");
		inventory.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, (short)3)
				.name(String.format("§aPerfil de %s", user.getName()))
				.owner(user.getName())
				.lore("  §fRanking: §a?", "",
						"§eClique para informações"));
		inventory.setItem(29, new ItemBuilder(Material.BOOK)
				.name("§aHistórico de contribuições")
				.lore("§7Aqui ficam todas as denúncias",
						"§7efetuados e concretizados por",
						"§7algum staff feita por ele", "", 
						"§eClique para informações"));
		inventory.setItem(30, new ItemBuilder(Material.SIGN)
				.name("§aPonto de contribuição")
				.lore("§7Quando você reporta alguém",
						"§7e ele é punido, você recebe",
						"§71 ponto de contribuição", "",
						String.format("§fTotal: §e%s", user.getAmount())));
		inventory.setItem(32, new ItemBuilder(Material.TRIPWIRE_HOOK)
				.name("§aMelhores contribuidores")
				.lore("§7Aqui ficam registrados os",
						"§7top contribuidores que contribuem",
						"§7com a staff por um servidor",
						"§7mais limpo e justo", "",
						"§eClique para informações"));
		inventory.setItem(33, new ItemBuilder(Material.NETHER_STAR)
				.name("§aRemover repoints")
				.lore("§7Use isto caso o jogador",
						"§7esteja abusando do comando",
						"§7denunciando gente inocente", "",
						player.hasPermission("zreport.manager") ? "§aClique para alterar" : "§cVocê não tem permissão"));
		
		return inventory;
	}
}
