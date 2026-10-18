package byrussx.vanillaoverrides.mixin;

import byrussx.vanillaoverrides.config.ModConfig;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public class LeashVillagersMixin
{
	// Allows leashing villagers
	@Inject(at = @At("HEAD"), method = "canBeLeashed", cancellable = true)
	private void oncanBeLeashed(CallbackInfoReturnable<Boolean> cir)
	{
		// If the override is enabled return true to make the villager leashable
		if (ModConfig.VILLAGER_LEADS.get())
		{
			cir.setReturnValue(true);
		}
		else
		{
			cir.setReturnValue(false);
		}

	}
}