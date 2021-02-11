package futurepack.api.interfaces.tilentity;

import futurepack.api.PacketBase;

/**
 * Implement this in your {@link net.minecraft.tileentity.TileEntity} and you will receive packets from the network. This is used for the research modules.
 */
public interface ITileNetwork
{
	/**
	 * @return if this is a Network machine (true) or just have this Interface implemented (false)
	 */
	public boolean isNetworkAble();
	
	/**
	 * @return if this is a wire or a Machine. (Machines are ends of the network)
	 */
	public boolean isWire();
	
	/**
	 * Wires Dont recive packets
	 */
	public void onFunkPacket(PacketBase pkt);
}
