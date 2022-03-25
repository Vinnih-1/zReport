package kazumy.plugin.zreport.spigot.command.base;

import me.saiintbrisson.minecraft.command.command.Context;
import org.bukkit.command.CommandSender;

public abstract class BaseCommand {

    public abstract void execute(Context<CommandSender> context);
}
