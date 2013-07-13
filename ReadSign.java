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
	version = "1.6"
)
@NetworkMod
(
	clientSideRequired = true
)

public class ReadSign
{
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
}