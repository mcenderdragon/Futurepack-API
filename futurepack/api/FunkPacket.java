package futurepack.api;

import java.util.ArrayList;

import net.minecraft.util.BlockPos;
import futurepack.api.interfaces.ITileNetwork;

public class FunkPacket
{
	BlockPos src;
	ITileNetwork sender;
	
	private ArrayList<ITileNetwork> users = new ArrayList<ITileNetwork>();
	
	public FunkPacket(BlockPos src, ITileNetwork net)
	{
		this.src = src;
		this.sender = net;
		users.add(net);
	}
	
	public void post(ITileNetwork net)
	{
		if(!users.contains(net))
		{
			users.add(net);
			net.onFunkPacket(this);
		}
	}
}
