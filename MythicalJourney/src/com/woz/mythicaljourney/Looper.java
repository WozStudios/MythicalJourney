package com.woz.mythicaljourney;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 6:25 PM
 */
public class Looper implements IDelayable {
	private MusicPlayer musicPlayer1;
	private MusicPlayer musicPlayer2;
	private Delay secondPlayerDelay;
	private String filename;

	public Looper(String filename) {
		musicPlayer1 = new MusicPlayer(filename);
		musicPlayer2 = new MusicPlayer(filename);

		musicPlayer1.start();





	}

	public void start() {
		musicPlayer1.startPlaying();

		secondPlayerDelay = new Delay(this, 2.0f);

		secondPlayerDelay.start();

	}

	@Override
	public void receiveDelayedEvent() {
		if (!musicPlayer2.isPlaying())
			musicPlayer2.start();
	}

	public void stop() {
		musicPlayer1.stopPlaying();
		musicPlayer2.stopPlaying();
	}

	public void dispose() {
		musicPlayer1.dispose();
		musicPlayer2.dispose();
	}
}
