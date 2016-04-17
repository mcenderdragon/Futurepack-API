package futurepack.api;

import java.util.ArrayList;
import java.util.Arrays;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.INBTSerializable;
import futurepack.api.interfaces.ITileLogisticSided;

public class LogisticStorage implements ITileLogisticSided, INBTSerializable<NBTTagCompound>
{
	private EnumLogisticMode[] supported;
	private EnumLogistic[] defaults;
	private ArrayList<EnumLogistic>[] blacklist; 
	/**
	 * [<logistic mode>][<block face>]
	 */
	private EnumLogistic[][] modes;
	
	public LogisticStorage(Object o)
	{
		this(EnumLogisticMode.getAllSupported(o));
	}
	
	public LogisticStorage(EnumLogisticMode...supported)
	{
		Arrays.sort(supported);
		this.supported = supported;
		modes = new EnumLogistic[EnumLogisticMode.values().length][EnumFacing.values().length];
		blacklist = new ArrayList[EnumLogisticMode.values().length];
		defaults = new EnumLogistic[EnumLogisticMode.values().length];
		Arrays.fill(defaults, EnumLogistic.NONE);
	}
	
	public void removeState(EnumLogistic logistic, EnumLogisticMode mode)
	{
//		for(int i=0;i<states.length;i++)
//		{
//			if(states[i] == logistic)
//			{
//				EnumLogistic[] newarr = new EnumLogistic[states.length-1];
//				if(i>0)
//					System.arraycopy(states, 0, newarr, 0, i);
//				if(i<states.length-1)
//					System.arraycopy(states, i+1, newarr, i, states.length-1-i);
//				
//				states = newarr;
//			}
//		}
		if(blacklist[mode.ordinal()] == null)
		{
			blacklist[mode.ordinal()] = new ArrayList(EnumLogistic.values().length);
		}
		if(!blacklist[mode.ordinal()].contains(logistic))
		{
			blacklist[mode.ordinal()].add(logistic);
		}
	}
	
	public void removeSupported(EnumLogisticMode logistic)
	{
		for(int i=0;i<supported.length;i++)
		{
			if(supported[i] == logistic)
			{
				EnumLogisticMode[] newarr = new EnumLogisticMode[supported.length-1];
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
	public void setDefaut(EnumLogistic log, EnumLogisticMode mode)
	{
		defaults[mode.ordinal()] = log;
	}
	
	public boolean isValid(EnumLogisticMode mode)
	{
		return 	Arrays.binarySearch(supported, mode) >= 0;
	}
	
	public boolean isValid(EnumLogistic log, EnumLogisticMode mode)
	{
		if(blacklist[mode.ordinal()] == null)
		{
			blacklist[mode.ordinal()] = new ArrayList(EnumLogistic.values().length);
			return true;
		}
		return  !blacklist[mode.ordinal()].contains(log);
	}
	
	@Override
	public EnumLogistic getModeForFace(EnumFacing face, EnumLogisticMode mode)
	{
		EnumLogistic log = modes[mode.ordinal()][face.ordinal()];
		if(log==null)
		{
			 modes[mode.ordinal()][face.ordinal()] = log = defaults[mode.ordinal()];
		}
		return log;
	}

	@Override
	public boolean setModeForFace(EnumFacing face, EnumLogistic log, EnumLogisticMode mode)
	{
		if(isValid(mode) && isValid(log, mode))
		{
			modes[mode.ordinal()][face.ordinal()] = log;
			return true;
		}
		return false;
	}

	@Override
	public EnumLogisticMode[] getLogisticModes()
	{	
		return supported;
	}

	public void writeToNBT(NBTTagCompound nbt)
	{
		int[] ints = new int[modes.length];
		for(int i=0;i<modes.length;i++)
		{
			ints[i] = toInt(modes[i], defaults[i]);
		}	
		nbt.setIntArray("logisticFaces", ints);
	}
	
	private int toInt(EnumLogistic[] arr, EnumLogistic defval)
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
	
	public void readFromNBT(NBTTagCompound nbt)
	{
		int[] ints = nbt.getIntArray("logisticFaces");
		for(int i=0;i<ints.length;i++)
		{
			modes[i] = fromInt(ints[i]);
		}
	}
	
	private EnumLogistic[] fromInt(int n)
	{
		EnumLogistic[] arr = new EnumLogistic[6];
		
		int bits = 5;
		for(int i=0;i<6;i++)
		{
			arr[i] = EnumLogistic.values()[(n >> (bits*i)) & 31];
		}
		return arr;
	}
	
	private int size(int i)
	{
		return Integer.SIZE - Integer.numberOfLeadingZeros(i);
	}

	public boolean canInsert(EnumFacing face, EnumLogisticMode mode)
	{
		return getModeForFace(face, mode).canInsert();
	}
	
	public boolean canExtract(EnumFacing face, EnumLogisticMode mode)
	{
		return getModeForFace(face, mode).canExtract();
	}

	@Override
	public NBTTagCompound serializeNBT()
	{
		NBTTagCompound nbt = new NBTTagCompound();
		writeToNBT(nbt);
		return nbt;
	}

	@Override
	public void deserializeNBT(NBTTagCompound nbt)
	{
		readFromNBT(nbt);
	}
}
