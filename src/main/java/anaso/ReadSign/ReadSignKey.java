package anaso.ReadSign;

import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.util.ChatComponentText;
import net.minecraft.world.World;

import java.util.HashMap;

public class ReadSignKey{
	KeyBinding keyBinding = null;

	HashMap<String, Boolean> Options = new HashMap<String, Boolean>();

	public ReadSignKey(KeyBinding keyBinding, HashMap Options){
		this.Options = Options;
		this.keyBinding = keyBinding;
	}

	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void inputKey(InputEvent.KeyInputEvent event){
		Minecraft MC = FMLClientHandler.instance().getClient();

		if(keyBinding.isPressed()){
			if(MC.objectMouseOver != null && MC.currentScreen == null){
				int BlockX = MC.objectMouseOver.blockX;
				int BlockY = MC.objectMouseOver.blockY;
				int BlockZ = MC.objectMouseOver.blockZ;

				// ターゲットしているブロックの確認

				if(Block.getBlockFromName("standing_sign").equals(MC.theWorld.getBlock(BlockX, BlockY, BlockZ)) || Block.getBlockFromName("wall_sign").equals(MC.theWorld.getBlock(BlockX, BlockY, BlockZ))){
					double[] worldPosition = {BlockX + 0.5, BlockY + 0.5, BlockZ + 0.5};

					TileEntitySign par1TileEntitySign = (TileEntitySign) MC.theWorld.getTileEntity(BlockX, BlockY, BlockZ);
					if(Options.get("Sneaking").booleanValue() == MC.thePlayer.isSneaking()){
						AddMessage(par1TileEntitySign, worldPosition);
					} else if(Options.get("NotSneaking").booleanValue() != MC.thePlayer.isSneaking()){
						AddMessage(par1TileEntitySign, worldPosition);
					}
				}
			}
		}
	}

	public boolean AddMessage(TileEntitySign SignText, double[] worldPosition){
		String text = "", temp = "";

		for(int a = 0; a < SignText.signText.length; a++){
			temp = SignText.signText[a];
			if(temp.equals(" ") || temp.equals("")){
			} else{
				if(Options.get("ModeNewLine").booleanValue()){
					FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText(temp));
				} else{
					text += temp + "  ";
				}
			}
		}

		if(!Options.get("ModeNewLine").booleanValue()){
			FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText(text));
		}

		if(Options.get("ConnectHukidashiChat").booleanValue()){
		}

		return true;
	}
}