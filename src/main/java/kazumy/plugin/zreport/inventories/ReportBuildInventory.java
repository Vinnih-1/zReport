package kazumy.plugin.zreport.inventories;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.Inventory;

import kazumy.plugin.zreport.inventories.base.BaseInventory;
import kazumy.plugin.zreport.inventories.utils.ItemBuilder;
import kazumy.plugin.zreport.report.Report;
import lombok.Data;
import lombok.val;

@Data(staticConstructor = "of")
public class ReportBuildInventory implements BaseInventory {
	
	private final Report report;

	@Override
	public void onClick(InventoryClickEvent event) {		
	}

	@Override
	public Inventory buildInventory() {
		val inventory = Bukkit.createInventory(null, 9 * 5, "§8Menu: Denunciar");
		inventory.setItem(13, new ItemBuilder(Material.SKULL_ITEM, 1, (short)3)
				.name(String.format("§aPerfil de %s", report.getName()))
				.owner(report.getName())
				.lore("", "§eClique para informações"));
		inventory.setItem(30, new ItemBuilder(Material.BEACON)
				.name("§aSelecionar o motivo")
				.lore("§7Clique para ver a lista",
						"§7de motivos para reportar", "",
						"§fSelecionado: §e" + (report.getViewer().getReason() == null ? "Nenhum" : report.getViewer().getReason())));
//		inventory.setItem(30, new ItemBuilder(Material.PAPER)
//				.name("§aAdicionar as provas")
//				.lore("§7Caso tenha alguma prova",
//						"§7que irá facilitar na punição",
//						"§7do jogador, coloque-a aqui", "",
//						"§fProvas: §e" + report.getViewer().getEvidence()));
		inventory.setItem(31, new ItemBuilder(Material.BOOK)
				.name("§aSelecionar a prioridade")
				.lore("§7Caso queira uma prioridade",
						"§7mais alta neste report", "",
						"§fPrioridade: " + report.getViewer().getPriority().getType()));
		inventory.setItem(32, new ItemBuilder(Material.ANVIL)
				.name("§aEnviar denúncia")
				.lore("§7Ao clicar aqui, você enviará",
						"§7um alerta desta denúncia",
						"§7a todos os staffs presentes", "",
						"§eClique para enviar"));
		
		return inventory;
	}
}
