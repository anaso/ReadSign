package anaso.ReadSign;

import java.util.HashMap;
import java.util.logging.Level;

import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.Configuration;
import net.minecraftforge.common.Property;

import org.lwjgl.input.Keyboard;

import cpw.mods.fml.client.registry.KeyBindingRegistry;
import cpw.mods.fml.common.FMLLog;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.network.NetworkMod;

@Mod
(
	modid = "ReadSign",
	name = "Read Sign",
	version = "1.6"
)
@NetworkMod
(
		clientSideRequired = false,
		serverSideRequired = false
)

public class ReadSign
{
	public static int KeyRead = Keyboard.KEY_R;
	public static boolean Sneaking = false;
	public static boolean NotSneaking = true;
	public static boolean ModeNewLine = false;
	public static boolean connectHukidashiChat = true;

	HashMap <String, Boolean> Options = new HashMap<String, Boolean>();

	@EventHandler
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
			Property PropConnectHukidashiChat = cfg.get(cfg.CATEGORY_GENERAL, "Connect HukidashiChat", true);

			PropSneaking.comment  = "True => Sneaking Read";
			PropNotSneaking.comment   = "True => NotSneaking Read";
			PropModeNewLine.comment   = "True=>MultiLine  False=>SingleLine";

			KeyRead = PropKeyRead.getInt();
			Sneaking = PropSneaking.getBoolean(false);
			NotSneaking = PropNotSneaking.getBoolean(true);
			ModeNewLine = PropModeNewLine.getBoolean(false);
			connectHukidashiChat = PropConnectHukidashiChat.getBoolean(true);

			Options.put("Sneaking", Boolean.valueOf(Sneaking));
			Options.put("NotSneaking", Boolean.valueOf(NotSneaking));
			Options.put("ModeNewLine", Boolean.valueOf(ModeNewLine));
			Options.put("ConnectHukidashiChat", Boolean.valueOf(connectHukidashiChat));

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

	@EventHandler
	public void PostInit(FMLPostInitializationEvent event)
	{
		KeyBinding[] myBinding = {new KeyBinding("ReadSign", KeyRead)};

		boolean[] myBindingRepeat = {false};

		ReadSignKey myKeyHandler = new ReadSignKey(myBinding, myBindingRepeat, Options);

		KeyBindingRegistry.registerKeyBinding(myKeyHandler);
	}
}