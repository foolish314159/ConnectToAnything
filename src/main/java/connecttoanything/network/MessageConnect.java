package connecttoanything.network;

import connecttoanything.item.ItemConnectionCard;
import connecttoanything.tileentity.TileEntitySocketConnector;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockPos;

public class MessageConnect extends MessageBase<MessageConnect> {

	private boolean disconnect;
	private BlockPos pos;

	public MessageConnect() {
	}

	public MessageConnect(boolean disconnect, BlockPos pos) {
		this.disconnect = disconnect;
		this.pos = pos;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		disconnect = buf.readBoolean();
		pos = new BlockPos(buf.readInt(), buf.readInt(), buf.readInt());
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeBoolean(disconnect);
		buf.writeInt(pos.getX());
		buf.writeInt(pos.getY());
		buf.writeInt(pos.getZ());
	}

	@Override
	public void handleClientSide(MessageConnect message, EntityPlayer player) {
	}

	@Override
	public void handleServerSide(MessageConnect message, EntityPlayer player) {
		TileEntitySocketConnector connector = (TileEntitySocketConnector) player.worldObj
				.getTileEntity(message.pos);
		ItemStack stack = connector.getStackInSlot(36);
		if (stack != null && connector != null) {
			String host = stack.getTagCompound().getString(
					ItemConnectionCard.NBT_HOST);
			int port = stack.getTagCompound().getInteger(
					ItemConnectionCard.NBT_PORT);
			connector.connect(host, port, message.disconnect);
		}
	}

}
