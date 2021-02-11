package futurepack.api.interfaces;

import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.capability.IFluidHandler;

public interface IFluidTankInfo 
{
	public FluidStack getFluidStack();
	
	public int getCapacity();
	
	default boolean isEmpty()
	{
		return getFluidStack().isEmpty();
	}

	public class FluidTankInfo implements IFluidTankInfo
	{
		private final IFluidHandler handler;
		private final int tank;
		
		public FluidTankInfo(IFluidHandler handler, int tank) 
		{
			super();
			this.handler = handler;
			this.tank = tank;
		}

		@Override
		public FluidStack getFluidStack() 
		{
			return handler.getFluidInTank(tank);
		}

		@Override
		public int getCapacity() 
		{
			return handler.getTankCapacity(tank);
		}
	}
}
