package com.woz.mythicaljourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.woz.mythicaljourney.MythicalJourneyGame;

/*
 * User: Daniel
 * Date: 4/29/13
 * Time: 2:52 PM
 */
public class ShapeRendererAlphaTest implements Screen {
	private ShapeRenderer renderer;

	public ShapeRendererAlphaTest(MythicalJourneyGame mythicalJourneyGame) {
		//To change body of created methods use File | Settings | File Templates.
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(1, 0, 0, 0.5f);
		renderer.rect(0, 0, 100, 200);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(0, 1, 0, 0.5f);
		renderer.rect(200, 0, 100, 100);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Line);
		renderer.setColor(0, 1, 0, 0.5f);
		renderer.circle(400, 50, 50);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(1, 0, 1, 0.5f);
		renderer.circle(500, 50, 50);
		renderer.end();

		renderer.begin(ShapeRenderer.ShapeType.Filled);
		renderer.setColor(0, 1, 1, 0.5f);
		renderer.circle(550, 50, 50);
		renderer.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	@Override
	public void show() {
		renderer = new ShapeRenderer();
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
		renderer.dispose();
	}
}
