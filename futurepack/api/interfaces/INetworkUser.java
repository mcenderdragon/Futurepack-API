package futurepack.api.interfaces;

import futurepack.api.PacketBase;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * This is needed for everything that sends a {@link futurepack.api.PacketBase}
 * See {@link futurepack.common.network.NetworkManager}
 */
public interface INetworkUser
{
	/**
	 * The World of the sender.
	 */
	public World getWorldObj();
	
	/**
	 * the Position of the sender.
	 */
	public BlockPos getBlockPos();
	
	/**
	 * This is called after the Packet is sent.
	 * @param pkt the Packet after everyone has received it.
	 */
	public void postPacketSend(PacketBase pkt);
}
