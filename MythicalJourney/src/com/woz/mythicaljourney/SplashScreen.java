package com.woz.mythicaljourney;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 3:29 AM
 */
public class SplashScreen implements Screen {
	private Sprite splashSprite;
	private Texture splashTexture;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle viewport;


	@Override
	public void show() {
		splashTexture = new Texture(Gdx.files.internal("images/splashScreen.png"));
		splashTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);
		splashSprite = new Sprite(splashTexture);
		splashSprite.setColor(1.0f, 1.0f, 1.0f, 1.0f);
		batch = new SpriteBatch();
		camera = new OrthographicCamera(1024, 512);
		camera.position.set((float)1024 / 2, (float)512 / 2, 0);
	}

	@Override
	public void render(float delta) {
		batch.begin();
		//splashSprite.draw(batch);
		batch.draw(splashTexture, 0, 0, GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT, 0, 0, 1024, 512, false, false);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float)width/(float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if(aspectRatio > GameManager.VIRTUAL_ASPECT_RATIO)
		{
			scale = (float)height / (float)GameManager.VIRTUAL_HEIGHT;
			crop.x = (width - GameManager.VIRTUAL_WIDTH * scale) / 2f;
		}
		else if(aspectRatio < GameManager.VIRTUAL_ASPECT_RATIO)
		{
			scale = (float)width / (float)GameManager.VIRTUAL_WIDTH;
			crop.y = (height - GameManager.VIRTUAL_HEIGHT * scale)/2f;
		}
		else
		{
			scale = (float)width / (float)GameManager.VIRTUAL_WIDTH;
		}

		float w = (float)GameManager.VIRTUAL_WIDTH * scale;
		float h = (float)GameManager.VIRTUAL_HEIGHT * scale;
		viewport = new Rectangle(crop.x, crop.y, w, h);

		Gdx.gl.glViewport((int)viewport.x, (int)viewport.y, (int)viewport.width, (int)viewport.height);
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
		splashTexture.dispose();
		batch.dispose();
	}
}
