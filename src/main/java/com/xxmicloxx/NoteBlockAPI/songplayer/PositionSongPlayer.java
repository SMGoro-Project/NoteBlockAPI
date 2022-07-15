package com.xxmicloxx.NoteBlockAPI.songplayer;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import com.xxmicloxx.NoteBlockAPI.NoteBlockAPI;
import com.xxmicloxx.NoteBlockAPI.event.PlayerRangeStateChangeEvent;
import com.xxmicloxx.NoteBlockAPI.model.Layer;
import com.xxmicloxx.NoteBlockAPI.model.Note;
import com.xxmicloxx.NoteBlockAPI.model.Playlist;
import com.xxmicloxx.NoteBlockAPI.model.Song;
import com.xxmicloxx.NoteBlockAPI.model.SoundCategory;

/**
 * SongPlayer created at a specified Location
 *
 */
public class PositionSongPlayer extends RangeSongPlayer {

	private Location targetLocation;

	public PositionSongPlayer(Song song) {
		super(song);
	}

	public PositionSongPlayer(Song song, SoundCategory soundCategory) {
		super(song, soundCategory);
	}
	
	public PositionSongPlayer(Playlist playlist, SoundCategory soundCategory) {
		super(playlist, soundCategory);
	}

	public PositionSongPlayer(Playlist playlist) {
		super(playlist);
	}

	/**
	 * Gets location on which is the PositionSongPlayer playing
	 * @return {@link Location}
	 */
	public Location getTargetLocation() {
		return targetLocation;
	}

	/**
	 * Sets location on which is the PositionSongPlayer playing
	 */
	public void setTargetLocation(Location targetLocation) {
		this.targetLocation = targetLocation;
	}

	@Override
	public void playTick(Player player, int tick) {
		if (!player.getWorld().getName().equals(targetLocation.getWorld().getName())) {
			return; // not in same world
		}

		byte playerVolume = NoteBlockAPI.getPlayerVolume(player);

		for (int i = 0; i < playingSong.getLayersCount(); i++) {
			cz.koca2000.nbs4j.Layer layer = playingSong.getLayer(i);
			cz.koca2000.nbs4j.Note note = layer.getNote(tick);
			if (note == null) continue;

			float volume = ((layer.getVolume() * (int) this.volume * (int) playerVolume * note.getVolume()) / 100_00_00_00F)
					* ((1F / 16F) * getDistance());

			channelMode.play(player, targetLocation, playingSong, layer, note, soundCategory, volume, !enable10Octave);

			if (isInRange(player)) {
				if (!playerList.get(player.getUniqueId())) {
					playerList.put(player.getUniqueId(), true);
					plugin.doSync(() -> Bukkit.getPluginManager().callEvent(new PlayerRangeStateChangeEvent(this, player, true)));
				}
			} else {
				if (playerList.get(player.getUniqueId())) {
					playerList.put(player.getUniqueId(), false);
					plugin.doSync(() -> Bukkit.getPluginManager().callEvent(new PlayerRangeStateChangeEvent(this, player, false)));
				}
			}
		}
	}
	
	/**
	 * Returns true if the Player is able to hear the current PositionSongPlayer 
	 * @param player in range
	 * @return ability to hear the current PositionSongPlayer
	 */
	@Override
	public boolean isInRange(Player player) {
		return player.getLocation().distance(targetLocation) <= getDistance();
	}
}
