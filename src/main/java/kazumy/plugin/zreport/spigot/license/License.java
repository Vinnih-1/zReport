package kazumy.plugin.zreport.spigot.license;

import org.bukkit.Bukkit;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import lombok.Getter;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.util.Base64;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Getter
public class License {

    protected final String key;
    protected final String url = "https://239787r98jsdui9jsdjin234jhibn438u.mercado-minecraft.com.br/checkLicense/";

    private String name;
    private String author;
    private String version;

    /**
     * Constructs a new License.
     *
     * @param key The license key from config.
     */
    public License(String key) {
        this.key = key;
    }

    /**
     * Checks the license.
     *
     * @throws NoSuchElementException When the key is null or empty.
     * @return If the license is valid.
     */
    public boolean check() {

        if (this.key == null || this.key.equals("")) {
            Bukkit.getConsoleSender().sendMessage("§c[zPunish] §fNenhuma key foi encontrada!");
            ;
            return false;
        }

        try {
            URL url = new URL(this.url + this.key);
            URLConnection connection = url.openConnection();

            connection.setRequestProperty("content-type", "application/json; charset=UTF-8");
            connection.setRequestProperty("user-agent",
                    "Mozilla/5.0 (X11; Linux x86_64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/81.0.4044.92 Safari/537.36'");
            connection.setRequestProperty("DevShop", "pota72dquecssdpoaur7as");
            connection.setDoOutput(true);

            OutputStream outputStream = connection.getOutputStream();
            outputStream.write(("{\"token\":" + new String(Base64.getDecoder().decode(
                    "IlnOl86F5rCVWc2AReu6lM6FW+uKtjfojbTNq+Kpv1TjjIHrrpZj4bap6Jq0241+Z9qi1qjnlLrEjNad7JOY5butWc2AKX7kmYYo26XbjeuKtuKpv+WRrOy/tOG2qdul54yH572Z4Z+yccSMIg=="))
                    + "}").getBytes());
            outputStream.flush();
            outputStream.close();

            try (BufferedReader bufferedReader = new BufferedReader(
                    new InputStreamReader(connection.getInputStream()))) {
                String value = bufferedReader.lines().collect(Collectors.joining("\n"));
                JSONObject jsonObject = (JSONObject) new JSONParser().parse(value);

                if (jsonObject.get("success").toString().equals("true")) {
                    this.name = jsonObject.get("name").toString();
                    this.author = jsonObject.get("author").toString();
                    this.version = jsonObject.get("latestVersion").toString();
                    return true;
                } else {
                    Bukkit.getConsoleSender().sendMessage("§e[zPunish] §f" + jsonObject.get("message").toString());
                    Bukkit.shutdown();
                    return false;
                }
            }
        } catch (Exception ignored) {
            Bukkit.getConsoleSender().sendMessage("§c[zPunish] §fOcorreu um erro enquanto verificávamos sua key.");
            Bukkit.shutdown();
            return false;
        }
    }
}
