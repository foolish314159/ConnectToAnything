package connecttoanything.api;

import java.util.List;

import net.minecraft.util.BlockPos;

public interface IConnectionProvider {

	/**
	 * Returns a list of all {@link IConnectionProvider} directly connected to
	 * this one
	 */
	public List<IConnectionProvider> getConnectedProviders();

	/**
	 * Returns true if this provider is a SocketConnector
	 */
	public boolean isMaster();

	/**
	 * Returns the {@link IConnectionProvider} connected to this via other
	 * providers for which {@link IConnectionProvider #isMaster()} returns true
	 * or null if none exists
	 */
	public IConnectionProvider getMaster(List<IConnectionProvider> checked);

	/**
	 * Returns true if a master with established socket is connected
	 */
	public boolean isConnected();

	/**
	 * Adds an {@link IConnectionListener} to the current network of providers.<br>
	 * All listeners will be stored by the master and notified during connection
	 * process.
	 * 
	 * @param pos
	 *            - BlockPos of the listener
	 */
	public void addConnectionListener(BlockPos pos, IConnectionListener listener);

	/**
	 * Adds an {@link IReader} to the current network of providers.<br>
	 * All listeners will be stored by the master and notified when a line is
	 * read from the socket.
	 * 
	 * @param pos
	 *            - BlockPos of the listener
	 */
	public void addReader(BlockPos pos, IReader reader);
}
