package futurepack.api;

import java.util.HashSet;
import java.util.Set;

import futurepack.api.interfaces.tilentity.ITileNetwork;
import net.minecraft.util.math.BlockPos;

/**
 * This is the base packet sended in the Network. (Ingame)
 */
public class PacketBase
{
	private BlockPos src;
	private ITileNetwork sender;
	
	private Set<ITileNetwork> users = new HashSet<ITileNetwork>();
	
	public PacketBase(BlockPos src, ITileNetwork net)
	{
		this.src = src;
		this.sender = net;
		users.add(net);
	}
	
	/**
	 * This packet will be send to the <b>net</b> if it has not already received it.
	 * 
	 * @param net the receiver
	 */
	public final void post(ITileNetwork net)
	{
		if(!users.contains(net))
		{
			users.add(net);
			net.onFunkPacket(this);
		}
	}
	
	/**
	 * @return the Block Cordiants of the sender
	 */
	public BlockPos getSenderPosition()
	{
		return src;
	}
	
	/**
	 * @return the instance of the Sender
	 */
	public ITileNetwork getSender()
	{
		return sender;
	}
}
