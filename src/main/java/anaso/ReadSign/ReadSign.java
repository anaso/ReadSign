package anaso.ReadSign;

import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.settings.KeyBinding;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.common.config.Property;
import org.lwjgl.input.Keyboard;

import java.util.HashMap;
import java.util.Objects;

@Mod
(
	modid = "ReadSign",
	version = "1.0"
)

public class ReadSign
{
	public static boolean Sneaking = false;
	public static boolean NotSneaking = true;
	public static boolean ModeNewLine = false;
	public static boolean connectHukidashiChat = true;

	@SideOnly(Side.CLIENT)
	public static final KeyBinding readSign = new KeyBinding("key.readSign.name", Keyboard.KEY_R, "readSign.inputEvent.name");

	HashMap <String, Object> Options = new HashMap<String, Object>();

	@EventHandler
	public void preInit(FMLPreInitializationEvent event)
	{
		Configuration cfg = new Configuration(event.getSuggestedConfigurationFile());
		try
		{
			cfg.load();
			Property PropSneaking = cfg.get(cfg.CATEGORY_GENERAL, "Sneaking", false);
			Property PropNotSneaking  = cfg.get(cfg.CATEGORY_GENERAL, "NotSneaking", true);
			Property PropModeNewLine = cfg.get(cfg.CATEGORY_GENERAL, "ModeNewLine", false);
			Property PropConnectHukidashiChat = cfg.get(cfg.CATEGORY_GENERAL, "Connect HukidashiChat", true);
			Property PropHeader = cfg.get(cfg.CATEGORY_GENERAL, "Header", "-- Read Sign --");

			PropSneaking.comment  = "True => Sneaking Read";
			PropNotSneaking.comment   = "True => NotSneaking Read";
			PropModeNewLine.comment   = "True=>MultiLine  False=>SingleLine";

			Sneaking = PropSneaking.getBoolean(false);
			NotSneaking = PropNotSneaking.getBoolean(true);
			ModeNewLine = PropModeNewLine.getBoolean(false);
			connectHukidashiChat = PropConnectHukidashiChat.getBoolean(true);

			Options.put("Sneaking", Boolean.valueOf(Sneaking));
			Options.put("NotSneaking", Boolean.valueOf(NotSneaking));
			Options.put("ModeNewLine", Boolean.valueOf(ModeNewLine));
			Options.put("ConnectHukidashiChat", Boolean.valueOf(connectHukidashiChat));
			Options.put("Header", PropHeader.getString());

		}
		catch (Exception e)
		{
			System.err.println(e);
		}
		finally
		{
			cfg.save();
		}
	}

	@Mod.EventHandler
	public void Init(FMLInitializationEvent event)
	{
		if(event.getSide() == Side.CLIENT)
		{
			ClientRegistry.registerKeyBinding(readSign);
		}

		FMLCommonHandler.instance().bus().register(new ReadSignKey(readSign, Options));
	}
}