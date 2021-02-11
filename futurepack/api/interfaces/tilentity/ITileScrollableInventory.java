package futurepack.api.interfaces.tilentity;

import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Direction;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;

public interface ITileScrollableInventory
{
	public default IItemHandlerModifiable getScrollableInventory()
	{
		return (IItemHandlerModifiable) ((TileEntity)this).getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, Direction.DOWN).orElseThrow(NullPointerException::new);
	}
}
