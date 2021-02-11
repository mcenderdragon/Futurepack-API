package futurepack.api;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.function.BiConsumer;

import futurepack.api.capabilities.ILogisticInterface;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.Direction;
import net.minecraftforge.common.util.INBTSerializable;

public class LogisticStorage implements INBTSerializable<CompoundNBT>
{
	private EnumLogisticType[] supported;
	private EnumLogisticIO[] defaults;
	private ArrayList<EnumLogisticIO>[] blacklist; 
	/**
	 * [<logistic mode>][<block face>]
	 */
	private EnumLogisticIO[][] modes;
	
	private LogInf[] interfaces;
	
	public final BiConsumer<Direction, EnumLogisticType> onChange;
	
	public LogisticStorage(BiConsumer<Direction, EnumLogisticType> onChange, EnumLogisticType...supported)
	{
		this.onChange = onChange;
		Arrays.sort(supported);
		this.supported = supported;
		modes = new EnumLogisticIO[EnumLogisticType.values().length][FacingUtil.VALUES.length];
		blacklist = new ArrayList[EnumLogisticType.values().length];
		defaults = new EnumLogisticIO[EnumLogisticType.values().length];
		Arrays.fill(defaults, EnumLogisticIO.NONE);
		interfaces = new LogInf[FacingUtil.VALUES.length];
	}
	
	public void removeState(EnumLogisticIO logistic, EnumLogisticType mode)
	{
		if(blacklist[mode.ordinal()] == null)
		{
			blacklist[mode.ordinal()] = new ArrayList<EnumLogisticIO>(EnumLogisticIO.values().length);
		}
		if(!blacklist[mode.ordinal()].contains(logistic))
		{
			blacklist[mode.ordinal()].add(logistic);
		}
	}
	
	public void removeSupported(EnumLogisticType logistic)
	{
		for(int i=0;i<supported.length;i++)
		{
			if(supported[i] == logistic)
			{
				EnumLogisticType[] newarr = new EnumLogisticType[supported.length-1];
				if(i>0)
					System.arraycopy(supported, 0, newarr, 0, i);
				if(i<supported.length-1)
					System.arraycopy(supported, i+1, newarr, i, supported.length-1-i);
				
				supported = newarr;
			}
		}
	}
	
	/**
	 * @param log the default value
	 * @param mode the categore for the value 
	 */
	public void setDefaut(EnumLogisticIO log, EnumLogisticType mode)
	{
		defaults[mode.ordinal()] = log;
	}
	
	public boolean isValid(EnumLogisticType mode)
	{
		return 	Arrays.binarySearch(supported, mode) >= 0;
	}
	
	public boolean isValid(EnumLogisticIO log, EnumLogisticType mode)
	{
		if(blacklist[mode.ordinal()] == null)
		{
			blacklist[mode.ordinal()] = new ArrayList<EnumLogisticIO>(EnumLogisticIO.values().length);
			return true;
		}
		return  !blacklist[mode.ordinal()].contains(log);
	}
	
	public EnumLogisticIO getModeForFace(Direction face, EnumLogisticType mode)
	{
		if(mode==null)
			return null;
		
		if(face==null)
			return EnumLogisticIO.INOUT;	//Because RF-Tools, use it strange
			
		EnumLogisticIO log = modes[mode.ordinal()][face.ordinal()];
		if(log==null)
		{
			 modes[mode.ordinal()][face.ordinal()] = log = defaults[mode.ordinal()];
		}
		return log;
	}

	public boolean setModeForFace(Direction face, EnumLogisticIO log, EnumLogisticType mode)
	{
		if(isValid(mode) && isValid(log, mode))
		{
			modes[mode.ordinal()][face.ordinal()] = log;
			if(onChange!=null)
				onChange.accept(face, mode);
			return true;
		}
		return false;
	}

	public EnumLogisticType[] getLogisticModes()
	{	
		return supported;
	}

	public void write(CompoundNBT nbt)
	{
		int[] ints = new int[modes.length];
		for(int i=0;i<modes.length;i++)
		{
			ints[i] = toInt(modes[i], defaults[i]);
		}	
		nbt.putIntArray("logisticFaces", ints);
	}
	
	private int toInt(EnumLogisticIO[] arr, EnumLogisticIO defval)
	{
		if(arr.length!=6)
			throw new IllegalArgumentException("Arrays must have a length of 6 (a block have 6 sides)");
		
		int n = 0;
		int bits = 5;//  32/6=5.33 (block have 6 sides) -> so 5*6 = 30; 2^5=32
		for(int i=0;i<6;i++)
		{
			n |= (arr[i]==null?defval : arr[i]).ordinal() << (bits*i);
		}
		return n;
	}
	
	public void read(CompoundNBT nbt)
	{
		int[] ints = nbt.getIntArray("logisticFaces");
		for(int i=0;i<ints.length;i++)
		{
			modes[i] = fromInt(ints[i]);
		}
		for(EnumLogisticType types : EnumLogisticType.values())
		{
			for(int i=0;i<6;i++)
			{
				if(!isValid(modes[types.ordinal()][i], types))
				{
					modes[types.ordinal()][i] = defaults[types.ordinal()];
				}
			}
		}
	}
	
	private EnumLogisticIO[] fromInt(int n)
	{
		EnumLogisticIO[] arr = new EnumLogisticIO[6];
		
		int bits = 5;
		for(int i=0;i<6;i++)
		{
			arr[i] = EnumLogisticIO.values()[(n >> (bits*i)) & 31];
		}
		return arr;
	}
	
	private int size(int i)
	{
		return Integer.SIZE - Integer.numberOfLeadingZeros(i);
	}

	public boolean canInsert(Direction face, EnumLogisticType mode)
	{
		return getModeForFace(face, mode).canInsert();
	}
	
	public boolean canExtract(Direction face, EnumLogisticType mode)
	{
		return getModeForFace(face, mode).canExtract();
	}

	@Override
	public CompoundNBT serializeNBT()
	{
		CompoundNBT nbt = new CompoundNBT();
		write(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(CompoundNBT nbt)
	{
		read(nbt);
	}
	
	public ILogisticInterface getInterfaceforSide(Direction face)
	{
		int p = face.getIndex();
		if(interfaces[p]==null)
		{
			interfaces[p] = new LogInf(face);
		}
		return interfaces[p];
	}
	
	public class LogInf implements ILogisticInterface
	{
		public final Direction side;
		
		public LogInf(Direction side)
		{
			super();
			this.side = side;
		}

		@Override
		public EnumLogisticIO getMode(EnumLogisticType mode)
		{
			return getModeForFace(side, mode);
		}

		@Override
		public boolean setMode(EnumLogisticIO log, EnumLogisticType mode)
		{
			return setModeForFace(side, log, mode);
		}

		@Override
		public boolean isTypeSupported(EnumLogisticType type)
		{
			int e = Arrays.binarySearch(getLogisticModes(), type);
			return e >= 0;
		}
		
	}
}
