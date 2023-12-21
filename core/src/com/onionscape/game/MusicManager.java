package com.onionscape.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.audio.Sound;

import java.util.ArrayList;
import java.util.List;

public class MusicManager {
    private static MusicManager instance = new MusicManager();
    private List<Music> backgroundMusicTracks, fightMusicTracks;
    private List<Sound> soundEffects;
    private int currentTrackIndex;
    private Music currentMusic;

    private MusicManager() {
        // private constructor to enforce singleton pattern
        backgroundMusicTracks = new ArrayList<>();
        fightMusicTracks = new ArrayList<>();
        soundEffects = new ArrayList<>();
        currentTrackIndex = 0;
    }

    public static MusicManager getInstance() {
        return instance;
    }

    public void initialize() {
        // Add your music tracks here
        Music track1 = Gdx.audio.newMusic(Gdx.files.internal("sounds/ShadowsAndDust.mp3"));
        Music track2 = Gdx.audio.newMusic(Gdx.files.internal("sounds/InDreams.mp3"));
        Music track3 = Gdx.audio.newMusic(Gdx.files.internal("sounds/PhaseShift.mp3"));
        Music track4 = Gdx.audio.newMusic(Gdx.files.internal("sounds/Chase.mp3"));
        Music track5 = Gdx.audio.newMusic(Gdx.files.internal("sounds/SuperEpic.mp3"));

        backgroundMusicTracks.add(track1);
        backgroundMusicTracks.add(track2);
        backgroundMusicTracks.add(track3);

        fightMusicTracks.add(track4);
        fightMusicTracks.add(track5);

        for (Music music : backgroundMusicTracks) {
            music.setLooping(false);
            music.setVolume(0.2f);
        }
        for (Music music : fightMusicTracks) {
            music.setLooping(false);
            music.setVolume(0.1f);
        }

        // Sound effects
        Sound attackSound = Gdx.audio.newSound(Gdx.files.internal("sounds/AxeAttack.wav"));
        Sound blockSound = Gdx.audio.newSound(Gdx.files.internal("sounds/EntityHit.wav"));

        soundEffects.add(attackSound);
        soundEffects.add(blockSound);
        
        playBackgroundMusic();
    }

    public void playBackgroundMusic() {
        stopCurrentTrack();

        currentTrackIndex = (currentTrackIndex + 1) % backgroundMusicTracks.size();
        currentMusic = backgroundMusicTracks.get(currentTrackIndex);
        currentMusic.play();
    }

    public void playFightMusic() {
        stopCurrentTrack();

        currentTrackIndex = (currentTrackIndex + 1) % fightMusicTracks.size();
        currentMusic = fightMusicTracks.get(currentTrackIndex);
        currentMusic.play();
    }

    public void playSoundEffect(int index) {
        if (index >= 0 && index < soundEffects.size()) {
            soundEffects.get(index).play(0.45f);
        }
    }

    public void stopCurrentTrack() {
        if (currentMusic != null && currentMusic.isPlaying()) {
            currentMusic.stop();
        }
    }

    public void dispose() {
        for (Music music : backgroundMusicTracks)
            music.dispose();
        for (Music music : fightMusicTracks)
            music.dispose();
        for (Sound sound : soundEffects)
            sound.dispose();
    }
}
