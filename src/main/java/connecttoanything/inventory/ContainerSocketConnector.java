package connecttoanything.inventory;

import connecttoanything.util.Log;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerSocketConnector extends Container {

	private IInventory inventoryConnector;

	public ContainerSocketConnector(IInventory inventoryPlayer,
			IInventory inventoryConnector, EntityPlayer player) {
		this.inventoryConnector = inventoryConnector;
		inventoryConnector.openInventory(player);

		this.addSlotToContainer(new SlotConnectionCard(inventoryConnector, 36,
				36, 31));

		for (int j = 0; j < 3; ++j) {
			for (int k = 0; k < 9; ++k) {
				this.addSlotToContainer(new Slot(inventoryPlayer,
						k + j * 9 + 9, 8 + k * 18, 66 + j * 18 + 18));
			}
		}

		for (int j = 0; j < 9; ++j) {
			this.addSlotToContainer(new Slot(inventoryPlayer, j, 8 + j * 18,
					124 + 18));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return inventoryConnector.isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int index) {
		ItemStack itemstack = null;
		Slot slot = (Slot) this.inventorySlots.get(index);

		if (slot != null && slot.getHasStack()) {
			ItemStack itemstack1 = slot.getStack();
			itemstack = itemstack1.copy();

			if (index < 36) {
				if (!this.mergeItemStack(itemstack1, 36,
						this.inventorySlots.size(), true)) {
					return null;
				}
			} else if (!this.mergeItemStack(itemstack1, 0, 36, false)) {
				return null;
			}

			if (itemstack1.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}
		}

		return itemstack;
	}

	@Override
	public void putStackInSlot(int index, ItemStack stack) {
		if (inventoryConnector.isItemValidForSlot(index, stack)) {
			super.putStackInSlot(index, stack);
		}
	}

	public class SlotConnectionCard extends Slot {

		public SlotConnectionCard(IInventory inventoryIn, int index,
				int xPosition, int yPosition) {
			super(inventoryIn, index, xPosition, yPosition);
		}

		@Override
		public boolean isItemValid(ItemStack stack) {
			return inventoryConnector.isItemValidForSlot(36, stack);
		}

		@Override
		public int getSlotStackLimit() {
			return 1;
		}

	}

}
