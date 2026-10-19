package byrussx.vanillaoverrides.mixin;

import byrussx.vanillaoverrides.config.ModConfig;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Leashable;
import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.level.gamerules.GameRules;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(Leashable.class)
public interface UnleashVillagersMixin
{
    @Inject(method = "tickLeash", at = @At("HEAD"), cancellable = true)
    private static <E extends Entity & Leashable> void vanillaOverrides$onTickLeash(ServerLevel level, E entity, CallbackInfo ci)
    {
        // Check if the entity is not a villager
        if (!(entity instanceof Villager)) return;

        // If the override is off and the villager is leashed, the leash
        if (!ModConfig.VILLAGER_LEADS.get() && entity.isLeashed())
        {
            // Same as vanilla, drop the leash if ENTITY_DROPS is enabled
            if (level.getGameRules().get(GameRules.ENTITY_DROPS))
            {
                entity.dropLeash();
            }
            else
            {
                entity.removeLeash();
            }

            ci.cancel();
        }
    }
}