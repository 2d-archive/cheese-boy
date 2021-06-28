package lol.dimensional.cheese;

import lol.dimensional.cheese.listeners.PlayerListener;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.title.Title;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;
import java.util.UUID;

public final class Cheese extends JavaPlugin implements Listener {

    private static final String DEFAULT_CHEESE_BOY = "873c0367-7ba9-4a9a-96ae-fa312ae756cb";
    private static Cheese INSTANCE;

    @Nullable
    private Integer taskId;

    @Nullable
    private UUID cheeseBoy;

    Cheese() {
        Cheese.INSTANCE = this;
    }

    @Nullable
    public UUID getCheeseBoy() {
        return this.cheeseBoy;
    }

    @Override
    public void onEnable() {
        this.getLogger().info("Lol fucking cheese boy");

        /* get and set the cheese boy value */
        String rawCheeseBoy = getConfig().getString("cheese-boy", Cheese.DEFAULT_CHEESE_BOY);
        this.cheeseBoy = UUID.fromString(rawCheeseBoy);

        /* events */
        PluginManager pluginManager = this.getServer().getPluginManager();
        pluginManager.registerEvents(new PlayerListener(), this);

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
                .getPlayer(Objects.requireNonNull(cheeseBoy));

        if (player != null) {
            this.startTheCheese(player);
        }
    }

    /**
     * Starts the cheese title on
     *
     * @param player The player to continuously send the cheese to.
     */
    public void startTheCheese(Player player) {
        if (this.taskId == null) {
            this
                    .getLogger()
                    .info("Starting the cheese for %s".formatted(player.getName()));

            this.taskId = Bukkit
                    .getScheduler()
                    .scheduleSyncRepeatingTask(this, () -> sendCheeseTitle(player), 100L, 100L);
        }
    }

    /**
     * Stops sending the cheese title.
     */
    public void stopTheCheese() {
        if (this.taskId != null) {
            Bukkit.getScheduler().cancelTask(this.taskId);
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
        return player.getUniqueId() != Cheese.getInstance().getCheeseBoy();
    }
}
