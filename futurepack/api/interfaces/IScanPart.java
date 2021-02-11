package futurepack.api.interfaces;

import java.util.Collection;
import java.util.Collections;
import java.util.concurrent.Callable;

import javax.annotation.Nonnull;

import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.World;

/**
 * This interface is used to add custom information to the gui of the Escanner, after scanning something.
 * It is registered in {@link futurepack.common.research.ScanningManager} (API method will follow)
 * Register in {@link futurepack.api.FPApiMain#registerScanPart(futurepack.api.EnumScanPosition, IScanPart)}
 */
public interface IScanPart
{
	/**
	 * Called if a Block is scanned. Return null if nothing should added or a chat component
	 * 
	 * @param w the World
	 * @param pos the Postiion
	 * @param inGUI if true its in the EScanner, false in the normal chat. (use it for different colors -> better readable)
	 * @param res 
	 * @return the ChatComponent
	 */
	@Deprecated
	public ITextComponent doBlock(World w, BlockPos pos, boolean inGUI, BlockRayTraceResult res);
	
	/**
	 * Called if an Entity is scanned. Return null if nothing should added or a chat component
	 * 
	 * @param w the World
	 * @param e the Entity
	 * @param inGUI if true its in the EScanner, false in the normal chat.(use it for different colors -> better readable)
	 * @return the ChatComponent
	 */
	@Deprecated
	public ITextComponent doEntity(World w, LivingEntity e, boolean inGUI);

	/**
	 * Called if a Block is scanned. Return null if nothing should added or a chat component
	 * 
	 * @param w the World
	 * @param pos the Postiion
	 * @param inGUI if true its in the EScanner, false in the normal chat. (use it for different colors -> better readable)
	 * @param res 
	 * @return the ChatComponents
	 */
	@Nonnull
	public default Callable<Collection<ITextComponent>> doBlockMulti(World w, BlockPos pos, boolean inGUI, BlockRayTraceResult res)
	{
		return () -> Collections.singletonList(doBlock(w, pos, inGUI, res));
	}
	
	/**
	 * Called if an Entity is scanned. Return null if nothing should added or a chat component
	 * 
	 * @param w the World
	 * @param e the Entity
	 * @param inGUI if true its in the EScanner, false in the normal chat.(use it for different colors -> better readable)
	 * @return the ChatComponents
	 */
	@Nonnull
	public default Callable<Collection<ITextComponent>> doEntityMulti(World w, LivingEntity e, boolean inGUI)
	{
		return () -> Collections.singletonList(doEntity(w, e, inGUI));
	}
	
}
