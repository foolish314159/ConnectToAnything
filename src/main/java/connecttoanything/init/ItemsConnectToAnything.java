package connecttoanything.init;

import net.minecraft.item.Item;
import connecttoanything.item.ItemConnectionCard;

public class ItemsConnectToAnything {

	public static Item itemConnectionCard;

	public static void init() {
		itemConnectionCard = new ItemConnectionCard();
	}

}
