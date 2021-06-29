package lol.dimensional.cheese.commands;

import lol.dimensional.cheese.Cheese;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Stop implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        boolean stopped = Cheese.getInstance().stopTheCheese();
        sender.sendMessage(stopped ? "Stopped sending the cheese" : "I did nothing lol");

        return true;
    }
}
