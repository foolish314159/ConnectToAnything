package connecttoanything.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;

public class ContainerSocketConnector extends Container {

	private IInventory inventoryConnector;

	public ContainerSocketConnector(IInventory inventoryPlayer,
			IInventory inventoryConnector, EntityPlayer player) {
		this.inventoryConnector = inventoryConnector;
		inventoryConnector.openInventory(player);

		this.addSlotToContainer(new Slot(inventoryConnector, 36, 81, 31));

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
	
	

}
