package futurepack.api.interfaces;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.BlockPos;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;

/**
 * This interface is used to add custom information to the gui of the Escanner, after scanning something.
 * It is registered in {@link futurepack.common.research.ScanningManager} (API method will follow)
 */
public interface IScanPart
{
	/**
	 * Called if a Block is scanned. Return null if nothing should added or a chat component
	 * 
	 * @param w the World
	 * @param pos the Postiion
	 * @param inGUI if true its in the EScanner, false in the normal chat. (use it for different colors -> better readable)
	 * @return the ChatComponent
	 */
	public IChatComponent doBlock(World w, BlockPos pos, boolean inGUI);
	
	/**
	 * Called if an Entity is scanned. Return null if nothing should added or a chat component
	 * 
	 * @param w the World
	 * @param e the Entity
	 * @param inGUI if true its in the EScanner, false in the normal chat.(use it for different colors -> better readable)
	 * @return the ChatComponent
	 */
	public IChatComponent doEntity(World w, EntityLivingBase e, boolean inGUI);

}
