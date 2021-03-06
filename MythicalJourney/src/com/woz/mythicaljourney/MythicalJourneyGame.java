package com.woz.mythicaljourney;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.woz.mythicaljourney.screens.OrbTestScreen;
import com.woz.mythicaljourney.input.InputManager;
import com.woz.mythicaljourney.screens.ShapeRendererAlphaTest;
import com.woz.mythicaljourney.utils.Time;

public class MythicalJourneyGame implements ApplicationListener {
	private Screen screen;
	
	@Override
	public void create() {
		InputManager.setup();

		screen = new OrbTestScreen(this);

		setScreen(screen);
	}

	@Override
	public void render() {
		float delta = Gdx.graphics.getDeltaTime();
		Time.update(delta);

		if (screen != null)
			screen.render(delta);
	}

	@Override
	public void resize(int width, int height) {
		if (screen != null)
			screen.resize(width, height);
	}

	@Override
	public void pause() {
		if (screen != null)
			screen.pause();
	}

	@Override
	public void resume() {
		if (screen != null)
			screen.resume();
	}

	/** Sets the current screen. {@link Screen#hide()} is called on any old screen, and {@link Screen#show()} is called on the new
	 * screen, if any.
	 * @param screen may be {@code null}
	 */
	public void setScreen(Screen screen) {
		if (this.screen != null) this.screen.hide();
		this.screen = screen;
		if (this.screen != null) {
			this.screen.show();
			this.screen.resize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		}
	}

	public Screen getScreen(Screen screen) {
		return screen;
	}

	@Override
	public void dispose() {
		if (screen != null)
			screen.dispose();
	}
}
