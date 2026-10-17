package byrussx.vanillaoverrides.mixin;

import byrussx.vanillaoverrides.config.ModConfig;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.entity.npc.villager.AbstractVillager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(AbstractVillager.class)
public class LeashVillagersMixin
{
	@Inject(at = @At("HEAD"), method = "canBeLeashed", cancellable = true)
	private void oncanBeLeashed(CallbackInfoReturnable<Boolean> cir)
	{
		// Returns true to make the villager leashable
		if (ModConfig.INSTANCE.villagerLeads)
		{
			cir.setReturnValue(true);
		}
		else
		{
			cir.setReturnValue(false);
		}

	}
}