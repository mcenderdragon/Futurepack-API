package futurepack.api;

import net.minecraft.util.math.BlockPos;

/**
 * This is a simple extension of the vanilla BlockPos.
 */
public class ParentCoords extends BlockPos
{
	private final ParentCoords parent;
	
	public ParentCoords(BlockPos coords, ParentCoords parent)
    {
        super(coords);
        this.parent = parent;
    }
	
	public ParentCoords getParent()
	{
		return parent;
	}
}