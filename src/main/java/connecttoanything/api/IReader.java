package connecttoanything.api;

public interface IReader {

	/**
	 * Called when a line has been read from a master provider
	 * 
	 * @param line
	 *            - line read from socket
	 */
	public void onRead(String line);

}
