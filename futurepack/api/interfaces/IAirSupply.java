package futurepack.api.interfaces;

/**
 * Capability added to LivingEntity
 */
public interface IAirSupply
{		
	public int getAir();
	
	public void addAir(int amount);
	
	public void reduceAir(int amount);

	public int getMaxAir();
}
