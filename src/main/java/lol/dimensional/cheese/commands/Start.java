package lol.dimensional.cheese.commands;

import lol.dimensional.cheese.Cheese;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;

public class Start implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (!(sender instanceof Player)) {
            return false;
        }

        Player player;
        try {
            player = Cheese.getInstance().getServer().getPlayer(args[0]);
        } catch (IndexOutOfBoundsException ignored) {
            player = Cheese.getInstance().getCheeseBoy();
        }

        if (player == null) {
            sender.sendMessage("No one to send the cheese title to :(");
            return false;
        }

        boolean started = Cheese
                .getInstance()
                .startTheCheese(player);

        sender.sendMessage(Cheese.formatColors("Started sending cheese to " + (started ? "&6" + player.getName() : "no one because it was already started..")));
        return true;
    }
}
