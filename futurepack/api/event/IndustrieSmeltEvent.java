package futurepack.api.event;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.player.PlayerEvent;

/**
 * fired at the {@link net.minecraftforge.common.MinecraftForge#EVENT_BUS} if a player click at a output-slot in the industrial-furnace-gui.
 */
public class IndustrieSmeltEvent extends PlayerEvent
{
	public ItemStack smelt;
	public IndustrieSmeltEvent(PlayerEntity player, ItemStack it)
	{
		super(player);
		smelt = it;
	}

}
