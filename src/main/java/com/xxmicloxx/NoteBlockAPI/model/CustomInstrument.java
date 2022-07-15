package com.xxmicloxx.NoteBlockAPI.model;

/**
 * Create custom instruments from a sound file
 * @deprecated Use {@link cz.koca2000.nbs4j.CustomInstrument}
 */
@Deprecated
public class CustomInstrument {

	private final cz.koca2000.nbs4j.CustomInstrument customInstrument;
	private byte index;

	/**
	 * Creates a CustomInstrument
	 * @param index
	 * @param name
	 * @param soundFileName
	 * @deprecated Use {@link cz.koca2000.nbs4j.CustomInstrument}
	 */
	@Deprecated
	public CustomInstrument(byte index, String name, String soundFileName) {
		this.index = index;
		customInstrument = new cz.koca2000.nbs4j.CustomInstrument()
				.setName(name)
				.setFileName(soundFileName);
	}

	public CustomInstrument(cz.koca2000.nbs4j.CustomInstrument customInstrument){
		this.customInstrument = customInstrument;
	}

	/**
	 * Gets index of CustomInstrument
	 * @return index
	 */
	@Deprecated
	public byte getIndex() {
		return index;
	}

	/**
	 * Gets name of CustomInstrument
	 * @return name
	 */
	@Deprecated
	public String getName() {
		return customInstrument.getName();
	}

	/**
	 * Gets file name of the sound
	 * @return file name
	 */
	@Deprecated
	public String getSoundFileName() {
		return customInstrument.getFileName();
	}

	/**
	 * Gets the org.bukkit.Sound enum for this CustomInstrument
	 * @return org.bukkit.Sound enum
	 */
	@Deprecated
	public org.bukkit.Sound getSound() {
		return null;
	}
}
