package connecttoanything.tileentity;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTUtil;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import connecttoanything.ConnectToAnything;
import connecttoanything.api.IConnectionListener;
import connecttoanything.api.IConnectionProvider;
import connecttoanything.api.IReader;
import connecttoanything.api.TileEntityConnectionProviderBase;
import connecttoanything.client.gui.inventory.GuiSocketConnector;
import connecttoanything.item.ItemConnectionCard;
import connecttoanything.ref.R;
import connecttoanything.util.Log;

public class TileEntitySocketConnector extends TileEntityConnectionProviderBase
		implements IConnectionListener, IInventory {

	// Socket connection constants
	private static final String HOST_UNDEFINED = "";
	private static final int PORT_UNDEFINED = -1;

	// NBT keys
	private static final String NBT_BASE = TileEntitySocketConnector.class
			.getSimpleName();
	private static final String NBT_HOST = NBT_BASE + ".host";
	private static final String NBT_PORT = NBT_BASE + ".port";
	private static final String NBT_CARD = NBT_BASE + ".card";
	private static final String NBT_LISTENERS = NBT_BASE + ".listeners";
	private static final String NBT_READERS = NBT_BASE + ".readers";
	private static final String NBT_POS_X = NBT_BASE + ".pos_x";
	private static final String NBT_POS_Y = NBT_BASE + ".pos_y";
	private static final String NBT_POS_Z = NBT_BASE + ".pos_z";

	// Tile members
	private Socket socket = null;
	private String host = HOST_UNDEFINED;
	private int port = PORT_UNDEFINED;
	private Map<BlockPos, IConnectionListener> listeners = null;
	private Map<BlockPos, IReader> readers = null;
	private ItemStack inventoryCard = null;

	// Keep track of sockets to close on server stop
	public static List<Socket> sockets;

	public void connect(String host, int port, boolean disconnect) {
		if (!listeners.containsKey(pos)) {
			listeners.put(pos, this);
		}

		if (disconnect) {
			if (socket != null) {
				try {
					sockets.remove(socket);
					socket.close();
				} catch (IOException e) {
					Log.severe(e.getMessage());
				} finally {
					socket = null;
					for (IConnectionListener listener : listeners.values()) {
						listener.onDisconnected();
					}
				}
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

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);

		host = compound.getString(NBT_HOST);
		port = compound.getInteger(NBT_PORT);
		NBTTagCompound compoundCard = compound.getCompoundTag(NBT_CARD);
		inventoryCard = ItemStack.loadItemStackFromNBT(compoundCard);

		if (compound.hasKey(NBT_LISTENERS)) {
			listeners = new HashMap<BlockPos, IConnectionListener>();
			NBTTagList listListeners = compound.getTagList(NBT_LISTENERS, 10);
			for (int i = 0; i < listListeners.tagCount(); i++) {
				NBTTagCompound compoundPosition = listListeners
						.getCompoundTagAt(i);
				BlockPos pos = new BlockPos(
						compoundPosition.getInteger(NBT_POS_X),
						compoundPosition.getInteger(NBT_POS_Y),
						compoundPosition.getInteger(NBT_POS_Z));
				listeners.put(pos, null);
			}
		}

		if (compound.hasKey(NBT_READERS)) {
			readers = new HashMap<BlockPos, IReader>();
			NBTTagList listReaders = compound.getTagList(NBT_READERS, 10);
			for (int i = 0; i < listReaders.tagCount(); i++) {
				NBTTagCompound compoundPosition = listReaders
						.getCompoundTagAt(i);
				BlockPos pos = new BlockPos(
						compoundPosition.getInteger(NBT_POS_X),
						compoundPosition.getInteger(NBT_POS_Y),
						compoundPosition.getInteger(NBT_POS_Z));
				readers.put(pos, null);
			}
		}
	}

	@Override
	public void writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);

		compound.setString(NBT_HOST, host);
		compound.setInteger(NBT_PORT, port);
		NBTTagCompound compoundCard = new NBTTagCompound();
		if (inventoryCard != null) {
			inventoryCard.writeToNBT(compoundCard);
		}
		compound.setTag(NBT_CARD, compoundCard);

		if (listeners != null) {
			NBTTagList listListeners = new NBTTagList();
			for (BlockPos pos : listeners.keySet()) {
				NBTTagCompound compoundPosition = new NBTTagCompound();
				compoundPosition.setInteger(NBT_POS_X, pos.getX());
				compoundPosition.setInteger(NBT_POS_Y, pos.getY());
				compoundPosition.setInteger(NBT_POS_Z, pos.getZ());
				listListeners.appendTag(compoundPosition);
			}
			compound.setTag(NBT_LISTENERS, listListeners);
		}

		if (readers != null) {
			NBTTagList listReaders = new NBTTagList();
			for (BlockPos pos : readers.keySet()) {
				NBTTagCompound compoundPosition = new NBTTagCompound();
				compoundPosition.setInteger(NBT_POS_X, pos.getX());
				compoundPosition.setInteger(NBT_POS_Y, pos.getY());
				compoundPosition.setInteger(NBT_POS_Z, pos.getZ());
				listReaders.appendTag(compoundPosition);
			}
			compound.setTag(NBT_READERS, listReaders);
		}
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

	@Override
	public void addReader(BlockPos pos, IReader reader) {
		if (readers == null) {
			readers = new HashMap<BlockPos, IReader>();
		}

		readers.put(pos, reader);
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
				// start reading till socket closed
				read();
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

		public void read() {
			if (isConnected()) {
				if (readers != null) {
					for (BlockPos pos : readers.keySet()) {
						if (readers.get(pos) == null) {
							readers.put(pos,
									(IReader) worldObj.getTileEntity(pos));
						}
					}
				}

				try (BufferedReader in = new BufferedReader(
						new InputStreamReader(socket.getInputStream()))) {
					String line;
					while ((line = in.readLine()) != null) {
						for (IReader reader : readers.values()) {
							reader.onRead(line);
						}
					}
				} catch (IOException e) {
					for (IConnectionListener listener : listeners.values()) {
						listener.onFail(e);
					}
				}

			}
		}
	}

	@Override
	public void onConnected(Socket s) {
		if (sockets == null) {
			sockets = new ArrayList<Socket>();
		}
		sockets.add(s);
	}

	@Override
	public void onDisconnected() {
		socket = null;
	}

	@Override
	public void onFail(Exception e) {
		socket = null;
		Log.severe(e.getMessage());
	}

	public String getHost() {
		return host;
	}

	public int getPort() {
		return port;
	}

	@Override
	public String getName() {
		return R.Block.SOCKET_CONNECTOR.getName();
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public IChatComponent getDisplayName() {
		return new ChatComponentText(getName());
	}

	@Override
	public int getSizeInventory() {
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventoryCard;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = inventoryCard;
		inventoryCard = null;
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return inventoryCard;
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (isItemValidForSlot(index, stack)) {
			inventoryCard = stack;
		}
		markDirty();
	}

	@Override
	public int getInventoryStackLimit() {
		return 1;
	}

	@Override
	public boolean isUseableByPlayer(EntityPlayer player) {
		return worldObj.getTileEntity(pos) == this
				&& player.getDistanceSq(pos.add(.5, .5, .5)) < 64;
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return stack != null && stack.getItem() instanceof ItemConnectionCard;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
	}

}
