package futurepack.api.interfaces.tilentity;

import net.minecraft.util.INameable;

/**
 * Currently used by waypoints
 */
public interface ITileRenameable extends INameable
{
	public void setName(String s);
}
