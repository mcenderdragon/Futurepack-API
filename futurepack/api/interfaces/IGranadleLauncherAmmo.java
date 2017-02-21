package futurepack.api.interfaces;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Implement this in an "Item", to make it Ammo for the Grenade Launcher
 *
 */
public interface IGranadleLauncherAmmo 
{
	/**
	 * 
	 * @param it ItemStack to check
	 * @return if it is a vaild ammo for GranadeLauncher
	 */
	public boolean isGranade(ItemStack it);
	
	/**
	 * 
	 * @param w World
	 * @param it ItemStack of the grenade, will auto decrement after shoot
	 * @param pl Thrower
	 * @param strength uniform power for shoot [0.0,1.0]
	 * @return EntityThrowable representing the spawned projectile  or  NULL
	 * 
	 * Note: you have to run setHeadingFromThrower(...) for the enity, or set it up other way
	 */
	public Entity createGrenade(World w, ItemStack it, EntityPlayer pl, float strength);
}
