package lol.dimensional.cheese;

import lol.dimensional.cheese.commands.Start;
import lol.dimensional.cheese.commands.Stop;
import lol.dimensional.cheese.listeners.PlayerListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.PluginCommand;
import org.bukkit.entity.Player;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public final class Cheese extends JavaPlugin {

    private static final String DEFAULT_CHEESE_BOY = "873c0367-7ba9-4a9a-96ae-fa312ae756cb";
    private static Cheese INSTANCE;

    @Nullable
    private Integer taskId;

    private UUID cheeseBoyId;

    Cheese() {
        Cheese.INSTANCE = this;
    }

    public UUID getCheeseBoyId() {
        return this.cheeseBoyId;
    }

    @Nullable
    public Player getCheeseBoy() {
        return this.getServer().getPlayer(getCheeseBoyId());
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Lol fucking cheese boy");

        /* get and set the cheese boy value */
        String rawCheeseBoy = this
                .getConfig()
                .getString("cheese-boy", Cheese.DEFAULT_CHEESE_BOY);

        this.cheeseBoyId = UUID.fromString(rawCheeseBoy);

        /* events */
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(), this);

        /* commands */
        this.useExecutor("stop", new Stop());
        this.useExecutor("start", new Start());

        /* detect if the cheese boy is in this server. */
        this.detectCheeseBoy();
    }

    @Override
    public void onDisable() {
        this.stopTheCheese();
    }

    /**
     * Detects if the cheese boy is in this server, if so start sending them the cheese title.
     */
    public void detectCheeseBoy() {
        Player player = this
                .getServer()
                .getPlayer(Objects.requireNonNull(cheeseBoyId));

        if (player != null) {
            this.startTheCheese(player);
        }
    }

    /**
     * Starts the cheese title on
     *
     * @param player The player to continuously send the cheese to.
     */
    public boolean startTheCheese(Player player) {
        if (this.taskId == null) {
            this
                    .getLogger()
                    .info("Starting the cheese for " + player.getName());

            this.taskId = Bukkit
                    .getScheduler()
                    .scheduleSyncRepeatingTask(this, () -> sendCheeseTitle(player), 100L, 100L);

            return true;
        }

        return false;
    }

    /**
     * Stops sending the cheese title.
     */
    public boolean stopTheCheese() {
        if (this.taskId != null) {
            Bukkit.getScheduler().cancelTask(this.taskId);
            return true;
        }

        return false;
    }

    private void useExecutor(String command, CommandExecutor executor) {
        PluginCommand cmd = this.getCommand(command);
        if (cmd != null) {
            cmd.setExecutor(executor);
        }
    }

    /**
     * A instance of the {@link Cheese cheese plugin}
     *
     * @return Cheese instance
     */
    public static Cheese getInstance() {
        return Cheese.INSTANCE;
    }

    /**
     * Sends the cheese title to the supplied player.
     *
     * @param player The player to send the cheese title to.
     */
    public static void sendCheeseTitle(Player player) {
        /* make the title. */
        Component mainTitle = Component.text("Cheese Boy", NamedTextColor.GOLD);
        Component subtitle = Component.text("You stinky asf", NamedTextColor.GRAY);

        Title title = Title.title(mainTitle, subtitle);

        /* send it */
//        player.sendTitle(translateColors("&6Cheese Boy"), "You fucking stink", 10, 20, 20);
        player.showTitle(title);
    }

    /**
     * Whether a player is the one and only cheese boy.
     *
     * @param player The player to check
     * @return true, if the player is the cheese boy.
     */
    public static boolean isCheeseBoy(Player player) {
        return player.getUniqueId() != Cheese
                .getInstance()
                .getCheeseBoyId();
    }

    /**
     * Replaces the & character to add color.
     *
     * @param str String to add color to.
     * @return The translated string.
     */
    public static String formatColors(String str) {
        return ChatColor.translateAlternateColorCodes('&', str);
    }
}
