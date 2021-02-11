package futurepack.api.interfaces.tilentity;

import java.util.UUID;

import net.minecraft.entity.player.PlayerEntity;

/**
 * This is to store the owning player in the block
 */
public interface ITileWithOwner
{
	public void setOwner(PlayerEntity pl);
	
	public boolean isOwner(PlayerEntity pl);
	
	public boolean isOwner(UUID pl);
}
