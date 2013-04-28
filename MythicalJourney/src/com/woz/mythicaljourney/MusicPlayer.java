package com.woz.mythicaljourney;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.AudioDevice;
import com.badlogic.gdx.utils.Disposable;
import com.musicg.wave.Wave;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 6:06 PM
 */
public class MusicPlayer extends Thread {
	private final AudioDevice device = Gdx.app.getAudio().newAudioDevice(44100, false);
	private String filename;
	private boolean stop = true;

	public MusicPlayer(String filename) {
		this.filename = filename;
	}

	public void run() {
		String path = "audio/" + filename;

		// create a wave object
		Wave wave;
		wave = new Wave(path);

		// print the wave header and info
		System.out.println(wave);

		short samples[] = wave.getSampleAmplitudes();
		//float samples[] = new float[1024];

		while (!stop) {

			device.writeSamples(samples, 0, samples.length);
		}

	}

	public void stopPlaying() {
		stop = true;
	}

	public void startPlaying() {
		stop = false;
	}

	public boolean isPlaying() {
		return !stop;
	}

	public void dispose() {
		device.dispose();
	}
}
