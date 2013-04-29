package com.woz.mythicaljourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 5:07 PM
 */
public class MenuScreen implements Screen {
	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.3f, 0.3f, 0.3f, 1.0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		Gdx.app.log("LOG", "Menu Screen Loaded!");
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
	}
}
