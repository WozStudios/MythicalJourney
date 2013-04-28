package com.woz.mythicaljourney.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 6:31 PM
 */
public class InputManager {
	private static InputMultiplexer inputMultiplexer;
	private static UIInputProcessor uiInputProcessor;
	private static MusicInputProcessor musicInputProcessor;

	private static boolean wasTouched = false;
	private static boolean touchedUp = false;

	public static void setup() {
		uiInputProcessor = new UIInputProcessor();
		musicInputProcessor = new MusicInputProcessor();
		inputMultiplexer = new InputMultiplexer(uiInputProcessor,  musicInputProcessor);

		Gdx.input.setInputProcessor(inputMultiplexer);
	}

	public static void setTouched(boolean wasTouched) {
		InputManager.wasTouched = wasTouched;
	}

	public static void setTouchedUp(boolean touchedUp) {
		InputManager.touchedUp = touchedUp;
	}

	public static boolean wasTouched() {
		return wasTouched;
	}

	public static boolean wasTouchedUp() {
		return touchedUp;
	}
}
