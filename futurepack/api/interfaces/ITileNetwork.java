package futurepack.api.interfaces;

import futurepack.api.FunkPacket;

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
	public void onFunkPacket(FunkPacket pkt);
}
