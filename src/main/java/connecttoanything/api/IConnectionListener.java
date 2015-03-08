package connecttoanything.api;

import java.net.Socket;

public interface IConnectionListener {
	/**
	 * Called when a socket connector successfully created a connection
	 * 
	 * @param s
	 *            - Established socket, should be checked for null
	 */
	public void onConnected(Socket s);

	/**
	 * Called when a socket connector disconnects from a connection
	 */
	public void onDisconnected();

	/**
	 * Called when a socket connector failed to establish a connection
	 * 
	 * @param e
	 *            - Exception that occured while trying to connect
	 */
	public void onFail(Exception e);
}