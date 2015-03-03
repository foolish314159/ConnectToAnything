package connecttoanything.item;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.ContainerRepair;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBook;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.IInteractionObject;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import connecttoanything.init.ItemsConnectToAnything;
import connecttoanything.ref.R;

public class ItemConnectionCard extends Item {

	// NBT keys
	private static final String NBT_BASE = ItemConnectionCard.class
			.getSimpleName();
	private static final String NBT_HOST = NBT_BASE + ".host";
	private static final String NBT_PORT = NBT_BASE + ".port";

	public ItemConnectionCard() {
		setUnlocalizedName(R.Item.CONNECTION_CARD.getUnlocalizedName());
		GameRegistry.registerItem(this, R.Item.CONNECTION_CARD.getName());

		GameRegistry.addShapelessRecipe(new ItemStack(this), new ItemStack(
				Items.iron_ingot), new ItemStack(Items.emerald));

		setMaxStackSize(1);
		setCreativeTab(CreativeTabs.tabMaterials);
		setMaxDamage(0);
	}

	@Override
	public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
		NBTTagCompound compound = new NBTTagCompound();
		compound.setString(NBT_HOST, "localhost");
		compound.setInteger(NBT_PORT, 8888);

		stack.setTagCompound(compound);
	}

	@Override
	public void addInformation(ItemStack stack, EntityPlayer playerIn,
			List tooltip, boolean advanced) {

		NBTTagCompound compound = stack.getTagCompound();
		if (compound != null) {
			String host = compound.getString(NBT_HOST);
			int port = compound.getInteger(NBT_PORT);
			tooltip.add(EnumChatFormatting.GREEN + "Host: " + host);
			tooltip.add(EnumChatFormatting.RED + "Port: " + port);
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack itemStackIn, World worldIn,
			EntityPlayer playerIn) {
		// playerIn.displayGui(new ConnectionCard(worldIn,
		// playerIn.getPosition()));
		playerIn.displayGUIBook(new ItemStack(Items.written_book));

		return itemStackIn;
	}

	public static class ConnectionCard implements IInteractionObject {

		private final World world;
		private final BlockPos position;

		public ConnectionCard(World worldIn, BlockPos pos) {
			this.world = worldIn;
			this.position = pos;
		}

		@Override
		public String getName() {
			return R.Item.CONNECTION_CARD.getName();
		}

		@Override
		public boolean hasCustomName() {
			return false;
		}

		@Override
		public IChatComponent getDisplayName() {
			return new ChatComponentTranslation(
					ItemsConnectToAnything.itemConnectionCard.getUnlocalizedName()
							+ ".name", new Object[0]);
		}

		@Override
		public Container createContainer(InventoryPlayer playerInventory,
				EntityPlayer playerIn) {
			return new ContainerRepair(playerInventory, world, position,
					playerIn);
		}

		@Override
		public String getGuiID() {
			return "minecraft:anvil";
		}

	}

}
