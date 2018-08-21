package org.ppcis.EndOfFlight;

import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Copyright © Brian Ronald
 * 21/08/18
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 */
public class EndOfFlight extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        getServer().getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        PlayerInteractEvent.getHandlerList().unregister((Listener) this);
        EntityDamageEvent.getHandlerList().unregister((Listener) this);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        // Borrow & reuse the Essentials /fly permission
        if (event.getPlayer().hasPermission("essentials.fly")) return;
        getLogger().info(event.toString());
        if (event.getPlayer().isGliding() && event.hasItem()) {
            if (event.getItem().getType() == Material.FIREWORK_ROCKET && event.getPlayer().getWorld().getName().endsWith("_the_end")) {
                event.setCancelled(true);
            }
        }
    }

    @EventHandler
    public void onEntityDamage(EntityDamageEvent event) {
        // Borrow & reuse the Essentials /fly permission
        if (!event.getEntityType().equals(EntityType.PLAYER)) return;
        getLogger().info(event.toString());
        Player player = (Player) event.getEntity();
        if (player.hasPermission("essentials.fly")) return;
        if (player.isGliding() && player.getWorld().getName().endsWith("_the_end")) {
            player.setGliding(false);
        }
    }
}
