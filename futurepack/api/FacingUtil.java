package futurepack.api;

import net.minecraft.util.Direction;
import net.minecraft.util.math.BlockPos;

public class FacingUtil 
{
	public static final Direction[] HORIZONTAL = new Direction[] {Direction.byHorizontalIndex(0), Direction.byHorizontalIndex(1), Direction.byHorizontalIndex(2), Direction.byHorizontalIndex(3)};
	public static final Direction[] VALUES = Direction.values();
	/**
	 * 
	 * @param x1 x of Block1
	 * @param y1 y of Block1
	 * @param z1 z of Block1
	 * @param x2 x of Block2
	 * @param y2 y of Block2
	 * @param z2 z of Block2
	 * @return the Side from Block1 to Block2
	 */
	public static Direction getSide(int x1, int y1, int z1, int x2, int y2, int z2)
	{
		int j = x2 - x1;
		int k = y2 - y1;
		int l = z2 - z1;
		
		for(Direction dir : VALUES)
		{
			if(dir.getXOffset() == j && dir.getYOffset()==k && dir.getZOffset()==l)
			{
				return dir;
			}
		}
		return Direction.DOWN;
	}
	/**
	 * @return the Side from Block1 to Block2
	 */
	public static Direction getSide(BlockPos pos1, BlockPos pos2)
	{
		return getSide(pos1.getX(), pos1.getY(), pos1.getZ(), pos2.getX(), pos2.getY(), pos2.getZ());
	}
}
