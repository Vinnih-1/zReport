package kazumy.plugin.zreport.spigot.report.viewer.entity;

import com.henryfabio.sqlprovider.executor.SQLExecutor;
import kazumy.plugin.zreport.spigot.configuration.MessageValue;
import kazumy.plugin.zreport.spigot.report.enums.Reputation;
import kazumy.plugin.zreport.spigot.report.viewer.impl.ViewerImpl;

public class Author extends ViewerImpl  {

    public Author(String name) {
        super(name);
    }

    public void rewardContributor(SQLExecutor executor) {
        if (getNewReputation() == null) return;

        if (this.getReputation() == null) {
            executor.updateQuery("INSERT INTO reputations(nickname, reputation, contributions) VALUES(?, ?, ?)", s -> {
                s.set(1, this.getName().toLowerCase());
                s.set(2, this.getReputation().toString());
                s.set(3, this.getContributions());
            });
        } else {
            executor.updateQuery("UPDATE reputations SET reputation = ?, contributions = ? WHERE nickname = ?", s -> {
                s.set(1, this.getReputation().toString());
                s.set(2, this.getContributions());
                s.set(3, this.getName().toLowerCase());
            });
        }
        this.toPlayer().sendMessage(MessageValue.get(MessageValue::approvedReport));
    }

    /**
     * Pega a quantidade de contribuições que o jogador fez,
     * e verifica se ele está apto para para upar de reputação
     *
     * @return Retorna a possível nova reputação do Author (Possívelmente null)
     */
    private Reputation getNewReputation() {
        if (this.getReputation() == Reputation.CONTRIBUTOR) return null;
        if (this.getContributions() >= Reputation.CONTRIBUTOR.getRequiredContributions()) return Reputation.CONTRIBUTOR;
        if (this.getContributions() >= Reputation.MEDIAN.getRequiredContributions()) return Reputation.MEDIAN;

        return Reputation.NONE;
    }
}
