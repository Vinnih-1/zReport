package kazumy.plugin.zreport.spigot.configuration;

import com.henryfabio.minecraft.configinjector.common.annotations.ConfigField;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigFile;
import com.henryfabio.minecraft.configinjector.common.annotations.ConfigSection;
import com.henryfabio.minecraft.configinjector.common.annotations.TranslateColors;
import com.henryfabio.minecraft.configinjector.common.injector.ConfigurationInjectable;
import lombok.Getter;
import lombok.experimental.Accessors;

import java.util.List;
import java.util.function.Function;

@TranslateColors
@Getter @Accessors(fluent = true)
@ConfigSection("mensagem-server")
@ConfigFile("config.yml")
public class MessageValue implements ConfigurationInjectable {

    @Getter private static final MessageValue instance = new MessageValue();

    @ConfigField("mensagens.player-offline") private String offlinePlayer;
    @ConfigField("mensagens.sem-permissao") private String withoutPermission;
    @ConfigField("mensagens.sem-provas") private String withoutEvidence;
    @ConfigField("mensagens.comando-bloqueado") private String blockedCommand;
    @ConfigField("mensagens.denunucia-enviada") private String sendedReport;
    @ConfigField("mensagens.denunucia-aprovada") private String approvedReport;
    @ConfigField("denuncia-prova-mensagem") private List<String> evidenceMessage;
    @ConfigField("alerta-staff-mensagem") private List<String> reportAlertMessage;

    public static <T> T get(Function<MessageValue, T> function) {
        return function.apply(instance);
    }
}
