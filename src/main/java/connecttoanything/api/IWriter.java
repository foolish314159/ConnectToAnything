package connecttoanything.api;

public interface IWriter {

	/**
	 * Writes a message to specified provider
	 * 
	 * @param provider
	 *            - any {@link IConnectionProvider} with access to a socket
	 *            connector
	 * @param msg
	 *            - message to write
	 * @return true if message has been written, false if
	 *         {@link IConnectionProvider #isConnected()} returns false
	 */
	public boolean writeLine(IConnectionProvider provider, String msg);

}
