package futurepack.api;

import net.minecraft.util.math.BlockPos;

/**
 * This is a simple extension of the vanilla BlockPos.
 */
public class ParentCoords extends BlockPos
{
	private final ParentCoords parent;
	private final int depth;
	
	public ParentCoords(BlockPos coords, ParentCoords parent)
    {
        super(coords);
        this.parent = parent;
        this.depth = parent == null ? 0 : parent.getDepth()+1;
    }
	
	public ParentCoords getParent()
	{
		return parent;
	}
	
	public int getDepth()
	{
		return depth;
	}
}
