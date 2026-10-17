package byrussx.vanillaoverrides.mixin;

import net.minecraft.world.entity.npc.villager.Villager;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(Villager.class)
public abstract class LeashVillagersNeckHeightMixin
{
    // Overloads original getLeashOffset() method from Leashable interface
    public Vec3 getLeashOffset(float partialTicks)
    {
        Villager villager = (Villager) (Object) this;

        // Returns a new position to leash the villager at neck height like the rest of mobs
        return new Vec3(
                0.0,
                villager.getEyeHeight() - 0.3,
                villager.getBbWidth() * 0.4F
        );
    }
}