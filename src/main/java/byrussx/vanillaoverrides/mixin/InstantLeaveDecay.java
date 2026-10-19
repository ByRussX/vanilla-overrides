package byrussx.vanillaoverrides.mixin;

import byrussx.vanillaoverrides.config.ModConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.tags.BlockTags;
import net.minecraft.world.level.block.LeavesBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockStateProperties;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerLevel.class)
public class InstantLeaveDecay
{
    // Instant leave decay
    @Inject(method = "sendBlockUpdated", at = @At("HEAD"))
    public void sendBlockUpdated(BlockPos pos, BlockState old, BlockState current, int updateFlags, CallbackInfo ci)
    {
        // Verify the current block are leaves
        if (!(current.getBlock() instanceof LeavesBlock)) return;

        // Verify the override is enabled
        if (!ModConfig.INSTANT_LEAF_DECAY.get()) return;

        // Get the current ServerLevel to perform the random tick
        ServerLevel world = (ServerLevel) (Object) this;

        // Check if the block has the leaves properties (for safety)
        if (old.is(BlockTags.LEAVES)
                && current.hasProperty(BlockStateProperties.PERSISTENT)
                && current.hasProperty(BlockStateProperties.DISTANCE))
        {

            // Get its persistent and distance flags
            boolean persistent = current.getValue(BlockStateProperties.PERSISTENT);
            int distance = current.getValue(BlockStateProperties.DISTANCE);

            // If the leaves block is not persistent and has a distance higher or equal to 7
            if (!persistent && distance >= 7)
            {
                // Execute a random tick to break the block using vanilla methods
                current.randomTick(world, pos, world.getRandom());
            }
        }
    }
}