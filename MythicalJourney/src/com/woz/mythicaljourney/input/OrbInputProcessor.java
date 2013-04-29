package com.woz.mythicaljourney.input;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.woz.mythicaljourney.OrbRenderer;

/*
 * User: Daniel
 * Date: 4/29/13
 * Time: 2:59 AM
 */
public class OrbInputProcessor implements InputProcessor {
	private OrbRenderer orbRenderer;

	public OrbInputProcessor() {
	}

	@Override
	public boolean keyDown(int keycode) {
		return false;
	}

	@Override
	public boolean keyUp(int keycode) {
		return false;
	}

	@Override
	public boolean keyTyped(char character) {
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		if (orbRenderer != null)
			orbRenderer.spawnOrb(Gdx.input.getX(), Gdx.input.getY());

		Gdx.app.log("Log", "Touched!");
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		return false;
	}

	public void setOrbRenderer(OrbRenderer orbRenderer) {
		this.orbRenderer = orbRenderer;
	}
}
