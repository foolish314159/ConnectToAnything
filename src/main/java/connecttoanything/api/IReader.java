package connecttoanything.api;

public interface IReader {

	/**
	 * Reads the next line from specified provider
	 * 
	 * @param provider
	 *            - any {@link IConnectionProvider} with access to a socket
	 *            connector
	 * @return the next line from a connector (blocking call) or null immediatly
	 *         if {@link IConnectionProvider #isConnected()} returns false
	 */
	public String readLine(IConnectionProvider provider);

}
