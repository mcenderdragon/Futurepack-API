package futurepack.api.helper;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;

import com.google.common.base.Predicate;

import futurepack.common.FPConfig;
import futurepack.common.item.ItemMagnetArmor;

public class HelperMagnetism
{
	public static int magnet_range = 5;
	public static float magnet_power = 0.4F;
	
	public static void doMagnetism(World w, BlockPos pos, int range, float power)
	{
		final List<Entity> anti = new ArrayList();
		List<Entity> list = w.getEntitiesWithinAABB(Entity.class, AxisAlignedBB.fromBounds(-range, -range, -range, range, range, range).offset(pos.getX()+0.5, pos.getY()+0.5, pos.getZ()+0.5), new Predicate<Entity>() //getEntitiesWithinAABBExcludingEntity
		{	
			@Override
			public boolean apply(Entity var1) 
			{
				if(var1 instanceof EntityItem || var1 instanceof EntityMinecart || var1 instanceof EntityIronGolem)
				{
					return true;
				}
				if(var1 instanceof EntityLivingBase)
				{
					EntityLivingBase base = (EntityLivingBase) var1;
					boolean b1 = base.getEquipmentInSlot(1)!=null && base.getEquipmentInSlot(1).getItem() instanceof ItemMagnetArmor;
					boolean b2 = base.getEquipmentInSlot(2)!=null && base.getEquipmentInSlot(2).getItem() instanceof ItemMagnetArmor;
					boolean b3 = base.getEquipmentInSlot(3)!=null && base.getEquipmentInSlot(3).getItem() instanceof ItemMagnetArmor;
					boolean b4 = base.getEquipmentInSlot(4)!=null && base.getEquipmentInSlot(4).getItem() instanceof ItemMagnetArmor;
					if( b1 || b2 || b3 || b4)
					{
						anti.add(var1);
						return true;
					}
				}
				if(var1 instanceof EntityLivingBase)
				{
					EntityLivingBase base = (EntityLivingBase) var1;
					boolean b1 = base.getEquipmentInSlot(1)!=null && FPConfig.boots.contains(base.getEquipmentInSlot(1).getItem());
					boolean b2 = base.getEquipmentInSlot(2)!=null && FPConfig.leggings.contains(base.getEquipmentInSlot(2).getItem());
					boolean b3 = base.getEquipmentInSlot(3)!=null && FPConfig.chestplates.contains(base.getEquipmentInSlot(3).getItem());
					boolean b4 = base.getEquipmentInSlot(4)!=null && FPConfig.helmets.contains(base.getEquipmentInSlot(4).getItem());
					return b1 || b2 || b3 || b4;
				}
				
				return false;
			}
		});
//		int meta = getBlockMetadata();
//		int dir = meta / 2 + meta%2;
		for(Entity item : list)
		{
			double d3 = ((double)pos.getX() + 0.5D) - item.posX ;
			double d4 = ((double)pos.getY() + 0.5D) - item.posY ;
			double d5 = ((double)pos.getZ() + 0.5D) - item.posZ ;

			d3 = mincheck(d3);
			d4 = mincheck(d4);
			d5 = mincheck(d5);
			
			if(item instanceof EntityLivingBase)
			{
				if(item.getDistanceSqToCenter(pos)> 0.09 * range * range)
					continue;
			}
			
			double dis = Math.sqrt(item.getDistanceSqToCenter(pos));
			
			d3 = d3/dis * power;
			d4 = d4/dis * power;
			d5 = d5/dis * power;
			
			if(anti.contains(item))
			{
				d3*=-1;
				d4*=-1;
				d5*=-1;
			}
			if(dis < 1.5)
			{
				item.motionX = d3;
				item.motionY = d4;
				item.motionZ = d5;
			}
			else
			{
				if(Math.abs(item.motionX) < Math.abs(d3*3))
				{
					item.motionX += d3;
				}
				if(Math.abs(item.motionY) < Math.abs(d4*3))
				{
					item.motionY += d4;
				}
				if(Math.abs(item.motionZ) < Math.abs(d5*3))
				{
					item.motionZ += d5;
				}
			}
			
			item.fallDistance = 0F;
		}
	}
	
	public static void doMagnetism(World w, BlockPos pos)
	{
		doMagnetism(w, pos, magnet_range, magnet_power);
	}
	
	private static double mincheck(double d)
	{
		if(Math.abs(d)<0.5)
		{
			d = 0;
		}
		return d;
	}
}
