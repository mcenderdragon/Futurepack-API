package futurepack.api;

import net.minecraft.util.BlockPos;

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