package kazumy.plugin.zreport.report;

public enum ReportPriority {
	
	BAIXA("§7§lBAIXA"), MEDIA("§e§lMÉDIA"), ALTA("§c§lALTA");
	
	private String type;
	
	ReportPriority(String type) {
		this.type = type;
	}
	
	public String getType() {
		return type;
	}
}
