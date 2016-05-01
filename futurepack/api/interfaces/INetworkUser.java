package futurepack.api.interfaces;

import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import futurepack.api.PacketBase;

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
