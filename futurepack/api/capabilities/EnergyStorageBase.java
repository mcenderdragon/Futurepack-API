package futurepack.api.capabilities;

public abstract class EnergyStorageBase implements IEnergyStorageBase
{
	protected int energy = 0;
	private final int maxenergy;
	private final EnumEnergyMode mode;
	
	public EnergyStorageBase(int maxpower, EnumEnergyMode type)
	{
		this.maxenergy = maxpower;
		this.energy = 0;
		this.mode = type;
	}

	@Override
	public int get()
	{
		return energy;
	}

	@Override
	public int getMax()
	{
		return maxenergy;
	}

	@Override
	public int use(int used)
	{
		if(energy >= used)
		{
			energy -= used;
			return used;
		}
		else
		{
			int p = energy;
			energy = 0;
			return p;
		}
	}

	@Override
	public int add(int added)
	{
		if(energy + added <= maxenergy)
		{
			energy += added;
			return added; 
		}
		else
		{
			int p = maxenergy - energy;
			energy = maxenergy;
			return p;
		}
	}
	
	@Override
	public EnumEnergyMode getType()
	{
		return mode;
	}
	
	public void set(int e)
	{
		energy = e;
	}
}
