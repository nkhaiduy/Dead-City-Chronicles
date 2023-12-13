package com.group22;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;

import java.net.URL;

/**
 * The Sound class is responsible for loading and playing sound files.
 * It allows the playing, stopping, and looping of sounds as well as volume control.
 */
public class Sound {
    Clip clip; // Clip object that can load a sound file and play it
    URL soundURL[] = new URL[30]; // Array of URLs to store sound file locations
    FloatControl fc; // Float control to for volume
    int volumeScale = 3;
    float volume;


    /**
     * Constructor initializes all sound files by loading them into the soundURL array.
     */
    public Sound(){
        soundURL[0] = getClass().getResource("/sound/Theme.wav");
        soundURL[1] = getClass().getResource("/sound/pickup.wav");
        soundURL[2] = getClass().getResource("/sound/cure.wav");
        soundURL[3] = getClass().getResource("/sound/scream.wav");
        soundURL[4] = getClass().getResource("/sound/zombie.wav");
        soundURL[5] = getClass().getResource("/sound/select.wav");
        soundURL[6] = getClass().getResource("/sound/coral.wav");
    }

    /**
     * Sets the current sound file to be used by loading it into the clip.
     * @param i the index of the sound file in the soundURL array
     */
    public void setFile(int i){
        try{
            AudioInputStream ais = AudioSystem.getAudioInputStream(soundURL[i]);
            clip = AudioSystem.getClip();
            clip.open(ais);
            fc = (FloatControl)clip.getControl(FloatControl.Type.MASTER_GAIN);
            checkVolume();
        }catch(Exception e){
        }
    }

    /**
     * Plays the currently loaded sound file from the beginning.
     */
    public void play() {
        if (clip != null) {
            clip.start();
        }
    }

    /**
     * Loops the currently loaded sound file continuously.
     */
    public void loop() {
        if (clip != null) {
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }
    }

    public void stop() {
        if (clip != null) {
            clip.stop();
        }
    }

    /**
     * Checks and adjusts the volume based on the current volumeScale setting.
     */
    public void checkVolume(){
        switch(volumeScale){
            case 0: volume = -80f; break;
            case 1: volume= -20f; break;
            case 2: volume = -12f; break;
            case 3: volume = -5f; break;
            case 4: volume = 1f; break;
            case 5: volume = 6f; break;
        }
        fc.setValue(volume);
    }
}
