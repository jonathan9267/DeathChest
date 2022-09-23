package jonathan9267.simpledeathchest;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Chest;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import java.util.UUID;

public class DeathChest implements Listener {

    Main plugin;

    public DeathChest(Main plugin){
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerDeath(EntityDamageEvent event) {

        /*
        Getting dead player.
        */

        if (event.getEntityType().equals(EntityType.PLAYER)) {
            UUID id = event.getEntity().getUniqueId();
            Player player = event.getEntity().getServer().getPlayer(id);
            if (player.getHealth() <= event.getDamage()) {

                /*
                Creating death chest
                 */
                
                Location DeathLocation = player.getLocation();

                /*
                Making sure there is space for a chest to be created.
                 */

                while (DeathLocation.getBlock().getType() != Material.AIR) {
                    DeathLocation.setY(DeathLocation.getY() + 1);
                }

                DeathLocation.getBlock().setType(Material.CHEST);
                Chest DeathChest = (Chest) DeathLocation.getBlock().getState();
                Inventory DeathChestInventory = DeathChest.getInventory();

                /*
                Adding items to death chest.
                Removing items from playerinventory.
                 */

                for (ItemStack i : player.getInventory().getContents()) {
                    if (i == null) {
                        continue;
                    }
                    DeathChestInventory.addItem(i);
                    player.getInventory().remove(i);
                }

                DeathChest.update();

                /*
                Time until chest de-spawns.
                 */

                int SecondsUntilDespawn = 120;
                Long Delay = 20L * SecondsUntilDespawn;

                Bukkit.getScheduler().scheduleSyncDelayedTask(plugin, new Runnable() {
                    @Override
                    public void run() {
                        for (ItemStack i : DeathChestInventory)
                            DeathChestInventory.remove(i);

                        DeathLocation.getBlock().setType(Material.AIR);

                    }
                }, Delay);

            }
        }
    }
}






