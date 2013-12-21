package anaso.ReadSign;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.EnumSet;
import java.util.HashMap;
import org.lwjgl.input.Keyboard;
import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.world.World;

public class ReadSignKey extends KeyHandler
{
	public static int bindKey;

	HashMap <String, Boolean> Options = new HashMap<String, Boolean>();

	public ReadSignKey(KeyBinding[] keyBindings, boolean[] repeatings, HashMap Options)
	{
		super(keyBindings, repeatings);

		this.Options = Options;
	}

	@Override
	public String getLabel()
	{
		return "ReadSign Keybindings";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd, boolean isRepeat)
	{
		if(tickEnd)
		{
			Minecraft MC = ModLoader.getMinecraftInstance();

			for(int i = 0; MC.gameSettings.keyBindings.length > i; i++)
			{
				if(MC.gameSettings.keyBindings[i].keyDescription.equals("ReadSign"))
				{
					this.bindKey = MC.gameSettings.keyBindings[i].keyCode;
				}
			}

			if(kb.keyCode == bindKey)
			{
				if(MC.objectMouseOver != null && MC.currentScreen == null)
				{
					int BlockX = MC.objectMouseOver.blockX;
					int BlockY = MC.objectMouseOver.blockY;
					int BlockZ = MC.objectMouseOver.blockZ;

					// ターゲットしているブロックの確認

					if(Block.signPost.blockID == MC.theWorld.getBlockId(BlockX, BlockY, BlockZ) || Block.signWall.blockID == MC.theWorld.getBlockId(BlockX, BlockY, BlockZ))
					{
						double[] worldPosition = {BlockX+0.5, BlockY+0.5, BlockZ+0.5};

						TileEntitySign par1TileEntitySign = (TileEntitySign)MC.theWorld.getBlockTileEntity(BlockX, BlockY, BlockZ);
						if(Options.get("Sneaking").booleanValue() == MC.thePlayer.isSneaking())
						{
							AddMessege(par1TileEntitySign, worldPosition);
						}
						else if(Options.get("NotSneaking").booleanValue() != MC.thePlayer.isSneaking())
						{
							AddMessege(par1TileEntitySign, worldPosition);
						}
					}
				}
			}
		}
	}

private int getMaxCurrentStrength(World par1World, int par2, int par3, int par4, int par5)
{
	if (par1World.getBlockId(par2, par3, par4) != Block.redstoneWire.blockID)
	{
		return par5;
	}
	else
	{
		int var6 = par1World.getBlockMetadata(par2, par3, par4);
		return var6 > par5 ? var6 : par5;
	}
}

	public boolean AddMessege(TileEntitySign SignText, double[] worldPosition)
	{
		String text = "", temp = "";

		for (int a = 0; a < SignText.signText.length; a++)
		{
			temp = SignText.signText[a];
			if (temp.equals(" ") || temp.equals(""))
			{
			}
			else
			{
				if(Options.get("ModeNewLine").booleanValue())
				{
					ModLoader.getMinecraftInstance().thePlayer.addChatMessage(temp);
				}
				else
				{
					text += temp + "  ";
				}
			}
		}

		if(!Options.get("ModeNewLine").booleanValue())
		{
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage(text);
		}

		if(Options.get("ConnectHukidashiChat").booleanValue())
		{
			try
			{
				// 座標を指定する
				anaso.HukidashiChat.HukidashiAPI.setHukidashi("Sign", SignText.signText, worldPosition[0], worldPosition[1], worldPosition[2]);
			}
			catch (NoClassDefFoundError e)
			{
				System.out.println(e);
			}
		}

		return true;
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd)
	{
		//do whatever
	}

	@Override
	public EnumSet<TickType> ticks()
	{
		return EnumSet.of(TickType.CLIENT);
		//I am unsure if any different TickTypes have any different effects.
	}
}