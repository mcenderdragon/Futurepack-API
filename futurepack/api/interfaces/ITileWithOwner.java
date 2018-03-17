package futurepack.api.interfaces;

import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;

/**
 * This is to store the owning player in the block
 */
public interface ITileWithOwner
{
	public void setOwner(EntityPlayer pl);
	
	public boolean isOwner(EntityPlayer pl);
	
	public boolean isOwner(UUID pl);
}
