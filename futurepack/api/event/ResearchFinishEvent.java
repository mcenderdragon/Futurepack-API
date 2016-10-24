package futurepack.api.event;

import java.util.UUID;

import futurepack.api.interfaces.IResearch;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraftforge.fml.common.eventhandler.Event;

public class ResearchFinishEvent extends Event
{
	private IResearch research;
	private UUID player;
	
	private ResearchFinishEvent(UUID pl, IResearch res)
	{
		player = pl;
		research = res;
	}
	
	public UUID getPlayer()
	{
		return player;
	}
	
	public IResearch getResearch()
	{
		return research;
	}
	
	public static class Server extends ResearchFinishEvent
	{
		public Server(UUID pl, IResearch res)
		{
			super(pl, res);
		}	
	}
	
	public static class Client extends ResearchFinishEvent
	{
		private EntityPlayerSP sp;
		
		public Client(EntityPlayerSP pl, IResearch res)
		{
			super(pl.getGameProfile().getId(), res);
		}	
		
		public EntityPlayerSP getSinglePlayer()
		{
			return sp;
		}
	}
}
