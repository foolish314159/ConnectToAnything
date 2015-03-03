package connecttoanything.tileentity;

import java.io.IOException;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.inventory.IInventory;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import connecttoanything.api.IConnectionListener;
import connecttoanything.api.IConnectionProvider;
import connecttoanything.util.Log;

public class TileEntitySocketConnector extends TileEntityConnectionProviderBase
		implements IConnectionListener {

	// Socket connection constants
	private static final String HOST_UNDEFINED = "";
	private static final int PORT_UNDEFINED = -1;

	// NBT keys
	private static final String NBT_BASE = TileEntitySocketConnector.class
			.getSimpleName();
	private static final String NBT_HOST = NBT_BASE + ".host";
	private static final String NBT_PORT = NBT_BASE + ".port";
	private static final String NBT_LISTENERS = NBT_BASE + ".listeners";
	private static final String NBT_LISTENER_POS_X = NBT_BASE + ".pos_x";
	private static final String NBT_LISTENER_POS_Y = NBT_BASE + ".pos_y";
	private static final String NBT_LISTENER_POS_Z = NBT_BASE + ".pos_z";

	// Tile members
	private Socket socket = null;
	private String host = HOST_UNDEFINED;
	private int port = PORT_UNDEFINED;
	private Map<BlockPos, IConnectionListener> listeners = null;

	public void connect(String host, int port, boolean disconnect) {
		if (disconnect && socket != null) {
			try {
				socket.close();
			} catch (IOException e) {
				Log.severe(e.getMessage());
			} finally {
				socket = null;
			}
		} else if (!host.equals(HOST_UNDEFINED) && port != PORT_UNDEFINED) {
			this.host = host;
			this.port = port;

			if (listeners != null) {
				for (BlockPos pos : listeners.keySet()) {
					if (listeners.get(pos) == null) {
						listeners.put(pos, (IConnectionListener) worldObj
								.getTileEntity(pos));
					}
				}
			}

			Thread connectThread = new Thread(new ConnectionWorker());
			connectThread.start();
		}
	}

	public void writeLine(String msg) {
		if (socket != null) {
			try {
				socket.getOutputStream().write((msg + "\n").getBytes());
			} catch (IOException e) {
				Log.severe(e.getMessage());
			}
		}
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		host = compound.getString(NBT_HOST);
		port = compound.getInteger(NBT_PORT);

		if (compound.hasKey(NBT_LISTENERS)) {
			listeners = new HashMap<BlockPos, IConnectionListener>();
			NBTTagList listListeners = compound.getTagList(NBT_LISTENERS, 10);
			for (int i = 0; i < listListeners.tagCount(); i++) {
				NBTTagCompound compoundPosition = listListeners
						.getCompoundTagAt(i);
				BlockPos pos = new BlockPos(
						compoundPosition.getInteger(NBT_LISTENER_POS_X),
						compoundPosition.getInteger(NBT_LISTENER_POS_Y),
						compoundPosition.getInteger(NBT_LISTENER_POS_Z));
				listeners.put(pos, null);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setString(NBT_HOST, host);
		compound.setInteger(NBT_PORT, port);

		if (listeners != null) {
			NBTTagList listListeners = new NBTTagList();
			for (BlockPos pos : listeners.keySet()) {
				NBTTagCompound compoundPosition = new NBTTagCompound();
				compoundPosition.setInteger(NBT_LISTENER_POS_X, pos.getX());
				compoundPosition.setInteger(NBT_LISTENER_POS_Y, pos.getY());
				compoundPosition.setInteger(NBT_LISTENER_POS_Z, pos.getZ());
				listListeners.appendTag(compoundPosition);
			}
			compound.setTag(NBT_LISTENERS, listListeners);
		}

		connect(HOST_UNDEFINED, PORT_UNDEFINED, true);
	}

	@Override
	public boolean isMaster() {
		return true;
	}

	@Override
	public IConnectionProvider getMaster(List<IConnectionProvider> checked) {
		return this;
	}

	@Override
	public boolean isConnected() {
		return socket != null && !socket.isClosed();
	}

	@Override
	public void addConnectionListener(BlockPos pos, IConnectionListener listener) {
		if (listeners == null) {
			listeners = new HashMap<BlockPos, IConnectionListener>();
		}

		listeners.put(pos, listener);
	}

	private class ConnectionWorker implements Runnable {
		@Override
		public void run() {
			try {
				socket = new Socket(host, port);
				if (listeners != null) {
					for (BlockPos key : listeners.keySet())
						listeners.get(key).onConnected(socket);
				}
			} catch (UnknownHostException e) {
				if (listeners != null) {
					for (BlockPos key : listeners.keySet())
						listeners.get(key).onFail(e);
				}
			} catch (IOException e) {
				if (listeners != null) {
					for (BlockPos key : listeners.keySet())
						listeners.get(key).onFail(e);
				}
			}
		}
	}

	@Override
	public void onConnected(Socket s) {
	}

	@Override
	public void onFail(Exception e) {
		socket = null;
		Log.severe(e.getMessage());
	}

}
