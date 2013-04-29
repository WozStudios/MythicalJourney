package com.woz.mythicaljourney;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.woz.mythicaljourney.input.InputManager;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 5:37 PM
 */
public class MusicGTest implements Screen {
	private Thread thread;
	private boolean stop = false;
	private Looper baseLooper;
	private Looper secondLooper;
	private boolean musicPlaying;

	public MusicGTest(MythicalJourneyGame game) {
		baseLooper = new Looper("Intro.wav");
		secondLooper = new Looper("IntroPt2.wav");

		musicPlaying = false;
	}

	@Override
	public void render(float delta) {
		//if (InputManager.wasTouched()  && !musicPlaying) {
		//	baseLooper.start();
		//	secondLooper.start();
//
		//	musicPlaying = true;
		//	InputManager.setTouched(false);
		//}
//
		//if (InputManager.wasTouchedUp()) {
		//	baseLooper.stop();
		//	secondLooper.stop();
//
		//	musicPlaying = false;
//
		//	InputManager.setTouchedUp(false);
		//}
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {



		Gdx.app.log("LOG", "Musicg Test Loaded!");
	}

	@Override
	public void hide() {
	}

	@Override
	public void pause() {
	}

	@Override
	public void resume() {
	}

	@Override
	public void dispose() {
		baseLooper.stop();
		secondLooper.stop();

		baseLooper.dispose();
		secondLooper.dispose();
	}
}
