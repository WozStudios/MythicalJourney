package com.woz.mythicaljourney;

import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenAccessor;
import aurelienribon.tweenengine.TweenEquations;
import aurelienribon.tweenengine.TweenManager;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;

/*
 * User: Daniel
 * Date: 4/27/13
 * Time: 3:29 AM
 */
public class SplashScreen implements Screen, IDelayable {
	class SplashTween implements TweenAccessor<Sprite> {

		public static final int ALPHA = 1;

		@Override
		public int getValues(Sprite target, int tweenType, float[] returnValues) {
			switch(tweenType) {
				case ALPHA:
					returnValues[0] = target.getColor().a;
					return 1;
				default:
					return -1;
			}
		}

		@Override
		public void setValues(Sprite target, int tweenType, float[] newValues) {
			switch (tweenType) {
				case ALPHA:
					target.setColor(1.0f, 1.0f, 1.0f, newValues[0]);
					break;
				default:
					break;
			}
		}

	}

	private final MythicalJourneyGame game;

	private Sprite splashSprite;
	private Texture splashTexture;
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private Rectangle viewport;
	private TextureRegion textureRegion;
	private TweenManager tweenManager;
	private Delay pauser;

	public SplashScreen(MythicalJourneyGame game) {
		this.game = game;
	}

	@Override
	public void show() {
		splashTexture = new Texture(Gdx.files.internal("images/splashScreen.png"));
		splashTexture.setFilter(Texture.TextureFilter.Nearest, Texture.TextureFilter.Linear);

		//float screenAspect = (float)Gdx.graphics.getWidth() / (float)Gdx.graphics.getHeight();
		float textureAspect = (float)splashTexture.getWidth() / (float)splashTexture.getHeight();

		float width = splashTexture.getWidth();
		float height = splashTexture.getHeight();

		if (textureAspect > GameManager.VIRTUAL_ASPECT_RATIO) {
			width = height * GameManager.VIRTUAL_ASPECT_RATIO;
		}

		else {
			height = width * GameManager.VIRTUAL_ASPECT_RATIO;
		}

		 textureRegion = new TextureRegion(
									splashTexture,
									(int)((splashTexture.getWidth() - width) / 2),
									(int)((splashTexture.getHeight() - height) / 2),
									(int)width,
									(int)height);

		splashSprite = new Sprite(textureRegion);

		splashSprite.setPosition((int)((GameManager.VIRTUAL_WIDTH - splashSprite.getWidth()) / 2f),
				(int)((GameManager.VIRTUAL_HEIGHT - splashSprite.getHeight()) / 2f));
		float scale = Gdx.graphics.getWidth() / width;
		splashSprite.setScale(scale);

		splashSprite.setColor(1, 1, 1, 0);

		batch = new SpriteBatch();
		camera = new OrthographicCamera(1024, 512);
		camera.position.set((float)1024 / 2, (float)512 / 2, 0);

		tweenManager = new TweenManager();

		pauser = new Delay(this, 6f);
		pauser.start();

		Tween.registerAccessor(Sprite.class, new SplashTween());
		Tween.to(splashSprite, SplashTween.ALPHA, 3.0f).target(1.0f)
				.ease(TweenEquations.easeInQuad)
				.start(tweenManager);
		Tween.to(splashSprite, SplashTween.ALPHA, 2.0f).delay(4.5f).target(0.0f)
				.ease(TweenEquations.easeInQuad)
				.start(tweenManager);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.855f, 0.855f, 0.749f, 1);
		//Gdx.gl.glClearColor(1, 1, 1, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		tweenManager.update(delta);


		batch.begin();
		splashSprite.draw(batch);
		//batch.draw(textureRegion, 0, 0, GameManager.VIRTUAL_WIDTH, GameManager.VIRTUAL_HEIGHT);

		batch.end();
	}

	@Override
	public void resize(int width, int height) {
		float aspectRatio = (float)width/(float)height;
		float scale = 1f;
		Vector2 crop = new Vector2(0f, 0f);

		if(aspectRatio > GameManager.VIRTUAL_ASPECT_RATIO) {
			scale = (float)height / (float)GameManager.VIRTUAL_HEIGHT;
			crop.x = (width - GameManager.VIRTUAL_WIDTH * scale) / 2f;
		}
		else if(aspectRatio < GameManager.VIRTUAL_ASPECT_RATIO) {
			scale = (float)width / (float)GameManager.VIRTUAL_WIDTH;
			crop.y = (height - GameManager.VIRTUAL_HEIGHT * scale)/2f;
		}
		else {
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
	public void receiveDelayedEvent() {
		game.setScreen(new MenuScreen());
	}

	@Override
	public void dispose() {
		splashTexture.dispose();
		batch.dispose();
	}
}
