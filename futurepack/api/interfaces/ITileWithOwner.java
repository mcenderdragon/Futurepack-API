package futurepack.api.interfaces;

import net.minecraft.entity.player.EntityPlayer;

public interface ITileWithOwner
{
	public void setOwner(EntityPlayer pl);
	
	public boolean isOwner(EntityPlayer pl);
}
