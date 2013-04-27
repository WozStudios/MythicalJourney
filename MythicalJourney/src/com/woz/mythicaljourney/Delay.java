package com.woz.mythicaljourney;

import com.badlogic.gdx.Gdx;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 4:00 PM
 */
public class Delay extends Thread {
	//private DelayedEvent delayedEvent;
	private long pauseTime;
	private IDelayable delayable;

	public Delay(IDelayable delayable, float pauseTime) {
		//delayedEvent = new DelayedEvent();
		this.pauseTime = (long)(pauseTime * 1000);
		this.delayable = delayable;
	}

	@Override
	public void run() {
		try {
			sleep(pauseTime);
			Gdx.app.log("LOG", "Finished pausing for " + pauseTime + "s.");

			Gdx.app.postRunnable(new Runnable() {
				@Override
				public void run() {
					delayable.receiveDelayedEvent();
				}
			});

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
