package futurepack.api.main;

import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityInject;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import futurepack.api.interfaces.ITileHologramAble;
import futurepack.api.interfaces.ITileLogisticSided;
import futurepack.api.interfaces.ITileNeonEngine;
import futurepack.api.interfaces.ITileNetwork;
import futurepack.api.interfaces.ITileSupportAble;
import futurepack.api.interfaces.ITileXpStorage;

@Mod(modid=ApiMain.modID,name="Futurepack API", version="1.0.1")
public class ApiMain
{
	public static final String modID = "fp.api";
	
	
	@Instance(ApiMain.modID)
	public static ApiMain instance;
	
	@CapabilityInject(ITileNeonEngine.class)
	private static final Capability<ITileNeonEngine> cap_NEON = null;
	@CapabilityInject(ITileSupportAble.class)
	private static final Capability<ITileSupportAble> cap_SUPPORT = null;
	@CapabilityInject(ITileNetwork.class)
	private static final Capability<ITileNetwork> cap_NETWORK = null;
	@CapabilityInject(ITileXpStorage.class)
	private static final Capability<ITileXpStorage> cap_EXP = null;
	@CapabilityInject(ITileLogisticSided.class)
	private static final Capability<ITileLogisticSided> cap_LOGISTIC = null;
	@CapabilityInject(ITileHologramAble.class)
	private static final Capability<ITileHologramAble> cap_HOLOGRAM = null;
	
	
	@Mod.EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		CapabilityManager.INSTANCE.register(ITileNeonEngine.class, new CapabilityNeon.Storage(), new CapabilityNeon.Factory());
		CapabilityManager.INSTANCE.register(ITileSupportAble.class, new CapabilitySupport.Storage(), new CapabilitySupport.Factory());
		CapabilityManager.INSTANCE.register(ITileNetwork.class, new CapabilityNetwork.Storage(), new CapabilityNetwork.Factory());
		CapabilityManager.INSTANCE.register(ITileXpStorage.class, new CapabilityXp.Storage(), new CapabilityXp.Factory());
		CapabilityManager.INSTANCE.register(ITileLogisticSided.class, new CapabilityLogistic.Storage(), new CapabilityLogistic.Factory());
		CapabilityManager.INSTANCE.register(ITileHologramAble.class, new CapabilityHologram.Storage(), new CapabilityHologram.Factory());
	}
	
	@Mod.EventHandler
	public void init(FMLInitializationEvent event)
	{
		
	}
	
	@Mod.EventHandler
	public void postInit(FMLPostInitializationEvent event)
	{
		
	}
	
	@CapabilityInject(ITileNeonEngine.class)
	private static void onRegisteredNeon(Capability<ITileNeonEngine> neon)
	{
		
	}
	
	@CapabilityInject(ITileSupportAble.class)
	private static void onRegisteredSupport(Capability<ITileSupportAble> support)
	{
		
	}
	
	@CapabilityInject(ITileNetwork.class)
	private static void onRegisteredNetwork(Capability<ITileNetwork> net)
	{
		
	}
	
	@CapabilityInject(ITileXpStorage.class)
	private static void onRegisteredXP(Capability<ITileXpStorage> xp)
	{
		
	}
	
	@CapabilityInject(ITileLogisticSided.class)
	private static void onRegisteredLogisitic(Capability<ITileLogisticSided> logistic)
	{
		
	}
	
	@CapabilityInject(ITileHologramAble.class)
	private static void onRegisteredHologram(Capability<ITileHologramAble> holo)
	{
		
	}
}
