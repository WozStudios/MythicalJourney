package com.woz.mythicaljourney.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.woz.mythicaljourney.GameManager;
import com.woz.mythicaljourney.OrbRenderer;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 6:31 PM
 */
public class InputManager {
	private static InputMultiplexer inputMultiplexer;
	private static UIInputProcessor uiInputProcessor;
	private static MusicInputProcessor musicInputProcessor;
	private static OrbInputProcessor orbInputProcessor;

	private static boolean wasTouched = false;
	private static boolean touchedUp = false;

	public static void setup() {
		uiInputProcessor = new UIInputProcessor();
		musicInputProcessor = new MusicInputProcessor();
		orbInputProcessor = new OrbInputProcessor();
		inputMultiplexer = new InputMultiplexer(uiInputProcessor,  musicInputProcessor, orbInputProcessor);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public static void setOrbRenderer(OrbRenderer orbRenderer) {
		orbInputProcessor.setOrbRenderer(orbRenderer);
	}
}
