package futurepack.api.interfaces;

import net.minecraft.world.IWorldNameable;

/**
 * Currently used by waypoints
 */
public interface ITileRenameable extends IWorldNameable
{
	public void setName(String s);
}
