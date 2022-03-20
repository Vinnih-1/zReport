package kazumy.plugin.zreport.spigot.additional.dependencies.manager;

import kazumy.plugin.zreport.spigot.MainReport;
import kazumy.plugin.zreport.spigot.additional.dependencies.JDA4Server;
import kazumy.plugin.zreport.spigot.additional.dependencies.base.Dependencies;
import lombok.Data;
import lombok.SneakyThrows;
import lombok.val;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.FileOutputStream;
import java.net.URL;
import java.nio.channels.Channels;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

@Data(staticConstructor = "of")
public class DependenciesManager {

    private final MainReport plugin;
    private final File file;

    public Optional<? extends Dependencies> missingAny() {
        return Stream.of(new JDA4Server()).filter(dep -> Arrays.asList(file.list()).contains(dep)).findFirst();
    }

    @SneakyThrows
    public void downloadTo(String name, String url) {
        val download = new URL(url);
        val rbc = Channels.newChannel(download.openStream());
        val fos = new FileOutputStream(file + "/" + name + ".jar");
        fos.getChannel().transferFrom(rbc, 0L, Long.MAX_VALUE);
        rbc.close();
        fos.close();

        Thread.sleep(2000);
        Bukkit.getConsoleSender().sendMessage(String.format("§a[zReport] §fDependência §e%s §fbaixada com êxito. Carregando-a...", name));
        Bukkit.getPluginManager().enablePlugin(Bukkit.getPluginManager().loadPlugin(new File("./" + name + ".jar")));
    }
}
