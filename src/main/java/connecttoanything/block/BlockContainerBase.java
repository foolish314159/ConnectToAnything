package connecttoanything.block;

import connecttoanything.ref.R.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;

public abstract class BlockContainerBase extends BlockContainer {

	public BlockContainerBase(Class<? extends TileEntity> tileEntityClass,
			Block block) {
		super(Material.rock);

		setUnlocalizedName(block.getUnlocalizedName());
		GameRegistry.registerBlock(this, block.getName());
		GameRegistry.registerTileEntity(tileEntityClass,
				block.getUnlocalizedName());

		setCreativeTab(CreativeTabs.tabBlock);
	}

	@Override
	public int getRenderType() {
		return 3;
	}

}
