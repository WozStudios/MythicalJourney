package com.woz.mythicaljourney.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.woz.mythicaljourney.MythicalJourneyGame;
import com.woz.mythicaljourney.OrbRenderer;
import com.woz.mythicaljourney.input.InputManager;

/*
 * User: Daniel
 * Date: 4/28/13
 * Time: 4:34 AM
 */
public class OrbTestScreen implements Screen {
	private MythicalJourneyGame game;

	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont fps;

	private OrbRenderer orbRenderer;

	public OrbTestScreen(MythicalJourneyGame game) {
		this.game = game;

		fps = new BitmapFont();

		batch = new SpriteBatch();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);

		orbRenderer = new OrbRenderer(batch, camera);

		InputManager.setOrbRenderer(orbRenderer);
	}

	@Override
	public void show() {
		Gdx.app.log("LOG", "Orb Test Loaded!");
	}

	@Override
	public void render(float delta) {
		orbRenderer.render(delta);

		batch.begin();
		fps.draw(batch, "FPS: " + String.valueOf(Gdx.graphics.getFramesPerSecond()), 5, Gdx.graphics.getHeight()-5);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {
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

	public OrbRenderer getOrbRenderer() {
		return orbRenderer;
	}

	@Override
	public void dispose() {
		batch.dispose();
		fps.dispose();
		orbRenderer.dispose();
	}
}
