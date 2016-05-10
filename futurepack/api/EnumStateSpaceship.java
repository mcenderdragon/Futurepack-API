package futurepack.api;

import net.minecraft.util.ChatComponentTranslation;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;

/**
 * This enum used to check if a spaceship can jump.  
 */
public enum EnumStateSpaceship
{
	/**
	 * The BoardComputer have not enough neon energy
	 */
	lowEnergie,
	/**
	 * The spaceship is not airtight
	 */
	open,
	/**
	 * The spaceship has not enough trusters for its size. 
	 */
	missingThruster,
	/**
	 * The spaceship is missing something that can produce neon energie
	 */
	missingEngine,
	/**
	 * The spaceship is missing a teleporter
	 */
	missingBeam,
	/**
	 * The spaceship has not enough fuel to power a trusters
	 */
	missingFuel,
	/**
	 * Everything is fine
	 */
	GO;
	
	private EnumStateSpaceship()
	{
		
	}
	
	/**
	 * This returns the unformated (colors, ...) translated chat message
	 */
	public ChatComponentTranslation getRawChat(Object ...args)
	{
		return new ChatComponentTranslation("chat.spaceship." + this.name(),args);
	}
	
	/**
	 * Return the translated chat message with colors.
	 */
	public ChatComponentTranslation getChatFormated(Object ...args)
	{
		ChatComponentTranslation trans = getRawChat(args);
		trans.setChatStyle(new ChatStyle().setColor(EnumChatFormatting.RED));
		return trans;
	}
}
