package htt.commands;

import org.bukkit.*;
import org.bukkit.command.*;
import org.bukkit.entity.*;
import org.bukkit.inventory.EquipmentSlot;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.potion.*;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class DoppelgangerCommand implements CommandExecutor {

    private final JavaPlugin plugin;
    private final HashMap<UUID, ArmorStand> doppelgangers = new HashMap<>();

    public DoppelgangerCommand(JavaPlugin plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player player)) return false;

        if (args.length > 0 && args[0].equalsIgnoreCase("start")) {

            for (Player p : Bukkit.getOnlinePlayers()) {
                p.hidePlayer(plugin, player);
            }

            player.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, Integer.MAX_VALUE, 1, false, false));

            ArmorStand stand = player.getWorld().spawn(player.getLocation(), ArmorStand.class, s -> {
                s.setInvisible(true);
                s.setInvulnerable(true);
                s.setGravity(false);
                s.setMarker(true);
                s.setBasePlate(false);
                s.setCustomNameVisible(false);


                ItemStack modelItem = new ItemStack(Material.CARVED_PUMPKIN);
                s.setItem(EquipmentSlot.HEAD, modelItem);
            });

            doppelgangers.put(player.getUniqueId(), stand);

            new BukkitRunnable() {
                @Override
                public void run() {
                    if (!player.isOnline() || !doppelgangers.containsKey(player.getUniqueId())) {
                        cancel();
                        return;
                    }

                    ArmorStand currentStand = doppelgangers.get(player.getUniqueId());
                    currentStand.teleport(player.getLocation().add(0, -1.7, 0));
                }
            }.runTaskTimer(plugin, 0L, 1L);

            player.sendMessage(ChatColor.GREEN + "started");
            return true;
        }

        return false;
    }
}
