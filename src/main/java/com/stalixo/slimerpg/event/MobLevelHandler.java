package com.stalixo.slimerpg.event;

import com.stalixo.slimerpg.zones.ZoneManager;
import net.minecraft.network.chat.Component;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.monster.Monster;
import net.minecraftforge.event.entity.EntityJoinLevelEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber
public class MobLevelHandler {

    static ZoneManager zoneManager = ZoneManager.getInstance();

    @SubscribeEvent
    public static void onMobSpawn(EntityJoinLevelEvent event) {
        Entity entity = event.getEntity();

        // Verifica se a entidade é um monstro
        if (entity instanceof Monster) {
            int x = entity.blockPosition().getX();
            int y = entity.blockPosition().getY();
            int z = entity.blockPosition().getZ();

            // Calcula o nível baseado na zona
            int mobLevel = zoneManager.getMobLevel(x, y, z);

            // Ajusta os atributos do mob baseado no nível
            Monster mob = (Monster) entity;
            mob.getAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0 + mobLevel * 5);
            mob.setHealth(mob.getMaxHealth());

            mob.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(3.0 + mobLevel * 2);

            mob.setCustomName(Component.literal( ""+mobLevel));
        }
    }
}