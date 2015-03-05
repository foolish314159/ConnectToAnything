package connecttoanything.network;

import connecttoanything.item.ItemConnectionCard;
import io.netty.buffer.ByteBuf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.fml.common.network.ByteBufUtils;

public class MessageConnectionCardChange extends
		MessageBase<MessageConnectionCardChange> {

	private String host;
	private int port;

	public MessageConnectionCardChange() {
	}

	public MessageConnectionCardChange(String host, int port) {
		this.host = host;
		this.port = port;
	}

	@Override
	public void fromBytes(ByteBuf buf) {
		host = ByteBufUtils.readUTF8String(buf);
		port = buf.readInt();
	}

	@Override
	public void toBytes(ByteBuf buf) {
		ByteBufUtils.writeUTF8String(buf, host);
		buf.writeInt(port);
	}

	@Override
	public void handleClientSide(MessageConnectionCardChange message,
			EntityPlayer player) {

	}

	@Override
	public void handleServerSide(MessageConnectionCardChange message,
			EntityPlayer player) {
		NBTTagCompound compound = player.inventory.getCurrentItem()
				.getTagCompound();
		compound.setString(ItemConnectionCard.NBT_HOST, message.host);
		compound.setInteger(ItemConnectionCard.NBT_PORT, message.port);

		player.addChatMessage(new ChatComponentText("Host set to "
				+ message.host));
		player.addChatMessage(new ChatComponentText("Port set to "
				+ message.port));
	}
}
