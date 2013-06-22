package anaso.ReadSign;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.*;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;
import net.minecraft.src.*;
import net.minecraft.tileentity.TileEntitySign;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;
import java.util.HashMap;
import java.util.logging.*;
import org.lwjgl.input.Keyboard;

@Mod
(
	modid = "ReadSign",
	name = "Read Sign",
	version = "1.5.2"
)
@NetworkMod
(
	clientSideRequired = true
)

public class ReadSign
{
	//@MLProp(min = 1.0D, max = 255.0D)
	//public static int KeyRead = 19;

	//public KeyBinding keyRead = new KeyBinding("ReadSign", KeyRead);

	/*
	@MLProp(name = "Sneaking", info = "True => Sneaking Read")
	public static boolean Sneaking = false;

	@MLProp(name = "NotSneaking", info = "True => NotSneaking Read")
	public static boolean NotSneaking = true;

	@MLProp(name = "ModeNewLine", info = "True=>MultiLine  False=>SingleLine")
	public static boolean ModeNewLine = false;
	*/

	public static int KeyRead = Keyboard.KEY_R;
	public static boolean Sneaking = false;
	public static boolean NotSneaking = true;
	public static boolean ModeNewLine = false;

	HashMap <String, Boolean> Options = new HashMap<String, Boolean>();

	@Mod.PreInit
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			Property PropKeyRead = cfg.get(cfg.CATEGORY_GENERAL, "KeyRead", 19);
			Property PropSneaking = cfg.get(cfg.CATEGORY_GENERAL, "Sneaking", false);
			Property PropNotSneaking  = cfg.get(cfg.CATEGORY_GENERAL, "NotSneaking", true);
			Property PropModeNewLine = cfg.get(cfg.CATEGORY_GENERAL, "ModeNewLine", false);
			PropSneaking.comment  = "True => Sneaking Read";
			PropNotSneaking.comment   = "True => NotSneaking Read";
			PropModeNewLine.comment   = "True=>MultiLine  False=>SingleLine";
			KeyRead = PropKeyRead.getInt();
			Sneaking = PropSneaking.getBoolean(false);
			NotSneaking = PropNotSneaking.getBoolean(true);
			ModeNewLine = PropModeNewLine.getBoolean(false);

			Options.put("Sneaking", Boolean.valueOf(Sneaking));
			Options.put("NotSneaking", Boolean.valueOf(NotSneaking));
			Options.put("ModeNewLine", Boolean.valueOf(ModeNewLine));

		}
		catch (Exception e)
		{
			FMLLog.log(Level.SEVERE, e, "Error Message");
		}
		finally
		{
			cfg.save();
		}
	}

	@Mod.PostInit
	public void PostInit(FMLPostInitializationEvent event)
	{
		KeyBinding[] myBinding = {new KeyBinding("ReadSign", KeyRead)};

		boolean[] myBindingRepeat = {false};

		ReadSignKey myKeyHandler = new ReadSignKey(myBinding, myBindingRepeat, Options);

		KeyBindingRegistry.registerKeyBinding(myKeyHandler);
	}

	/*
	public mod_ReadSign()
	{
		ModLoader.registerKey(this, new KeyBinding("ReadSign", KeyRead), false);
		ModLoader.registerPacketChannel(this, "ReadSign");
	}
	*/


	/*
	public static boolean AddMessege(TileEntitySign SignText, ReadSign RS)
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
				if(RS.ModeNewLine)
				{
					ModLoader.getMinecraftInstance().thePlayer.addChatMessage(temp);
				}
				else
				{
					text += temp + "  ";
				}
			}
		}

		if(!RS.ModeNewLine)
		{
			ModLoader.getMinecraftInstance().thePlayer.addChatMessage(text);
		}

		return true;
	}
	*/

	/*
	public void handleKeyboardEvent(KeyBinding keybinding)
	{
		Minecraft var = ModLoader.getMinecraftInstance();

		if (keybinding.keyDescription.equals("ReadSign"))
		{
			if(var.objectMouseOver != null)
			{
				int BlockX = var.objectMouseOver.blockX;
				int BlockY = var.objectMouseOver.blockY;
				int BlockZ = var.objectMouseOver.blockZ;

				if(Block.signPost.blockID == var.theWorld.getBlockId(BlockX, BlockY, BlockZ) || Block.signWall.blockID == var.theWorld.getBlockId(BlockX, BlockY, BlockZ))
				{
					TileEntitySign par1TileEntitySign = (TileEntitySign)var.theWorld.getBlockTileEntity(BlockX, BlockY, BlockZ);
					if(this.Sneaking == var.thePlayer.isSneaking())
					{
						ReadSign.AddMessege(par1TileEntitySign, this);
					}
					else if(this.NotSneaking != var.thePlayer.isSneaking())
					{
						ReadSign.AddMessege(par1TileEntitySign, this);
					}
				}
			}
		}
	}
	*/
}