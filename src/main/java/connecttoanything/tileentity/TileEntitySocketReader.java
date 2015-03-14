package connecttoanything.tileentity;

import java.net.Socket;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.IChatComponent;
import connecttoanything.api.IConnectionListener;
import connecttoanything.api.IReader;
import connecttoanything.api.TileEntityConnectionProviderBase;
import connecttoanything.ref.R;
import connecttoanything.util.Log;

public class TileEntitySocketReader extends TileEntityConnectionProviderBase
		implements IConnectionListener, IReader, IInventory {

	private ItemStack inventoryItemInput;
	private ItemStack inventoryItemOutput;

	@Override
	public void onConnected(Socket s) {
		Log.info("Reader notified: Connected");
	}

	@Override
	public void onFail(Exception e) {
		Log.severe("Reader notified: " + e.getMessage());
	}

	@Override
	public void onDisconnected() {
		Log.info("Reader notified: Disconnected");
	}

	@Override
	public void onRead(String line) {
		Log.info("Reader read line: " + line);
	}

	@Override
	public String getName() {
		return R.Block.SOCKET_READER.getName();
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
		return 2;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return index == 0 ? inventoryItemInput : inventoryItemOutput;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		ItemStack stack = getStackInSlot(index);
		stack.stackSize -= count;
		if (stack.stackSize <= 0) {
			setInventorySlotContents(index, null);
		}
		return stack;
	}

	@Override
	public ItemStack getStackInSlotOnClosing(int index) {
		return getStackInSlot(index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		if (index == 0) {
			inventoryItemInput = stack;
		} else {
			inventoryItemOutput = stack;
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
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
		return stack != null;
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
