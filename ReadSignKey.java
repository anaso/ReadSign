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
	//boolean Check = false;

	public static int bindKey;

	HashMap <String, Boolean> Options = new HashMap<String, Boolean>();

	public ReadSignKey(KeyBinding[] keyBindings, boolean[] repeatings, HashMap Options)
	{
		//the first value is an array of KeyBindings, the second is whether or not the call
		//keyDown should repeat as long as the key is down
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
			//loadOptions();
			//Check = false;
			//System.out.println("RS in");

			if(kb.keyCode == bindKey)
			{
				if(MC.objectMouseOver != null)
				{
					int BlockX = MC.objectMouseOver.blockX;
					int BlockY = MC.objectMouseOver.blockY;
					int BlockZ = MC.objectMouseOver.blockZ;

					if(Block.signPost.blockID == MC.theWorld.getBlockId(BlockX, BlockY, BlockZ) || Block.signWall.blockID == MC.theWorld.getBlockId(BlockX, BlockY, BlockZ))
					{
						TileEntitySign par1TileEntitySign = (TileEntitySign)MC.theWorld.getBlockTileEntity(BlockX, BlockY, BlockZ);
						if(Options.get("Sneaking").booleanValue() == MC.thePlayer.isSneaking())
						{
							AddMessege(par1TileEntitySign);
						}
						else if(Options.get("NotSneaking").booleanValue() != MC.thePlayer.isSneaking())
						{
							AddMessege(par1TileEntitySign);
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

	public boolean AddMessege(TileEntitySign SignText)
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

		return true;
	}
/*
	public void loadOptions()
	{
		Minecraft MC = ModLoader.getMinecraftInstance();
		File optionsFile = new File(MC.getMinecraftDir(), "options.txt");

		try
		{
			if (!optionsFile.exists())
			{
				return;
			}

			BufferedReader var1 = new BufferedReader(new FileReader(optionsFile));
			String var2 = "";

			while ((var2 = var1.readLine()) != null)
			{
				try
				{
					String[] var3 = var2.split(":");

					for (int var4 = 0; var4 < this.keyBindings.length; ++var4)
					{
						if (var3[0].equals("key_ReadSign"))
						{
							bindKey = Integer.parseInt(var3[1]);
						}
					}
				}
				catch (Exception e)
				{
					System.out.println(e);
				}
			}

			KeyBinding.resetKeyBindingArrayAndHash();
			var1.close();
		}
		catch (Exception e)
		{
			System.out.println(e);
		}
	}
*/
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