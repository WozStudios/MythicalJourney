package com.woz.mythicaljourney;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.Texture.TextureFilter;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import org.apache.http.protocol.HttpDateGenerator;

public class MythicalJourneyGame implements ApplicationListener {
	private Screen screen;
	
	@Override
	public void create() {
		Gdx.input.setInputProcessor(new UIInputProcessor());

		screen = new SplashScreen(this);

		setScreen(screen);
	}

	@Override
	public void render() {

		if (screen != null)
			screen.render(Gdx.graphics.getDeltaTime());
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

	@Override
	public void dispose() {
		if (screen != null)
			screen.dispose();
	}
}
