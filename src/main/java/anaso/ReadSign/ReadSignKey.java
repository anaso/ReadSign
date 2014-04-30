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

import java.util.ArrayList;
import java.util.HashMap;

public class ReadSignKey{
	KeyBinding keyBinding = null;

	HashMap<String, Object> Options = new HashMap<String, Object>();

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
					if((Boolean) Options.get("Sneaking") == MC.thePlayer.isSneaking()){
						AddMessage(par1TileEntitySign, worldPosition);
					} else if((Boolean) Options.get("NotSneaking") != MC.thePlayer.isSneaking()){
						AddMessage(par1TileEntitySign, worldPosition);
					}
				}
			}
		}
	}

	public boolean AddMessage(TileEntitySign SignText, double[] worldPosition){
		String text = "", temp = "";
		ArrayList<String> showText = new ArrayList<String>();
		int minLine = 0;

		if(!Options.get("Header").equals("")){
			showText.add((String) Options.get("Header"));
			minLine = 1;
		}

		for(int a = 0; a < SignText.signText.length; a++){
			temp = SignText.signText[a];
			if(temp.equals(" ") || temp.equals("")){
			} else{
				if((Boolean) Options.get("ModeNewLine")){
					showText.add(temp);
				} else{
					text += temp + "  ";
				}
			}
		}

		if(!(Boolean) Options.get("ModeNewLine")){
			if(!text.replaceAll(" ", "").equals("")){
				showText.add(text);
			}
		}

		// 表示部分
		if(showText.size() > minLine)
		{
			for(String showString:showText)
			{
				FMLClientHandler.instance().getClient().thePlayer.addChatMessage(new ChatComponentText(showString));
			}
		}

		if((Boolean) Options.get("ConnectHukidashiChat")){
		}

		return true;
	}
}