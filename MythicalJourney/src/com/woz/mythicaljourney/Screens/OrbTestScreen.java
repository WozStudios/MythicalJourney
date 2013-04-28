package com.woz.mythicaljourney.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.woz.mythicaljourney.MythicalJourneyGame;
import com.woz.mythicaljourney.gameobjects.Orb;

/*
 * User: Daniel
 * Date: 4/28/13
 * Time: 4:34 AM
 */
public class OrbTestScreen implements Screen {
	private MythicalJourneyGame game;

	private ShapeRenderer shapeRenderer;
	private Orb orb;

	private FrameBuffer blurTargetA;
	private FrameBuffer blurTargetB;
	private SpriteBatch spriteBatch;
	private OrthographicCamera camera;

	private ShaderProgram blurShader;
	private final int FBO_SIZE = 1024;
	private final float MAX_BLUR = 2f;
	private final TextureRegion fboRegion;

	public OrbTestScreen(MythicalJourneyGame game) {
		this.game = game;

		orb = new Orb(new Vector2(300, 400), 100, new Color(0.3f, 0.7f, 0.8f, 1.0f));

		shapeRenderer = new ShapeRenderer();
		shapeRenderer.setColor(0.3f, 0.7f, 0.8f, 1.0f);

		blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		fboRegion = new TextureRegion(blurTargetA.getColorBufferTexture());
		fboRegion.flip(false, true);

		spriteBatch = new SpriteBatch();

		initializeShaders();

		camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
		camera.setToOrtho(false);
	}

	private void initializeShaders() {
		//String verticalVertexShader = "shaders/verticalVertex.glsl";
		//String horizontalVertexShader = "shaders/horizontalVertex.glsl";
		String vertexShader = "shaders/blur.vert";
		String fragmentShader = "shaders/blur.frag";

		ShaderProgram.pedantic = false;

		blurShader = new ShaderProgram(Gdx.files.internal(vertexShader), Gdx.files.internal(fragmentShader));
		if (!blurShader.isCompiled()) {
			System.err.println(blurShader.getLog());
			System.exit(0);
		}
		if (blurShader.getLog().length()!=0)
			System.out.println(blurShader.getLog());

		//setup uniforms for our shader
		blurShader.begin();
		blurShader.setUniformf("dir", 0f, 0f);
		blurShader.setUniformf("resolution", FBO_SIZE);
		blurShader.setUniformf("radius", 1f);
		blurShader.end();
	}

	@Override
	public void show() {
		Gdx.app.log("LOG", "Orb Test Loaded!");
	}

	@Override
	public void render(float delta) {
		//Start rendering to an offscreen color buffer
		blurTargetA.begin();

		//Clear the offscreen buffer with an opaque background
		Gdx.gl.glClearColor(0.855f, 0.855f, 0.749f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		//before rendering, ensure we are using the default shader
		spriteBatch.setShader(null);

		//resize the batch projection matrix before drawing with it
		resizeBatch(FBO_SIZE, FBO_SIZE);

		//now we can start drawing...

		//draw our scene here
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
		shapeRenderer.circle(orb.getPosition().x, orb.getPosition().y, orb.getRadius());
		shapeRenderer.end();

		//finish rendering to the offscreen buffer
		blurTargetA.end();

		//now let's start blurring the offscreen image
		spriteBatch.setShader(blurShader);

		spriteBatch.begin();

		//ensure the direction is along the X-axis only
		blurShader.setUniformf("dir", 1f, 0f);

		//update blur amount based on touch input
		float mouseXAmt = Gdx.input.getX() / (float)Gdx.graphics.getWidth();
		blurShader.setUniformf("radius", mouseXAmt * MAX_BLUR);

		//our first blur pass goes to target B
		blurTargetB.begin();

		//we want to render FBO target A into target B
		fboRegion.setTexture(blurTargetA.getColorBufferTexture());

		//draw the scene to target B with a horizontal blur effect
		spriteBatch.draw(fboRegion, 0, 0);

		//flush the batch before ending the FBO
		spriteBatch.flush();

		//finish rendering target B
		blurTargetB.end();

		//now we can render to the screen using the vertical blur shader

		//update our projection matrix with the screen size
		resizeBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		//update the blur only along Y-axis
		blurShader.setUniformf("dir", 0f, 1f);

		//update the Y-axis blur radius
		float mouseYAmt = Gdx.input.getY() / (float)Gdx.graphics.getHeight();
		blurShader.setUniformf("radius", mouseYAmt * MAX_BLUR);

		//draw target B to the screen with a vertical blur effect
		fboRegion.setTexture(blurTargetB.getColorBufferTexture());
		spriteBatch.draw(fboRegion, 0, 0);

		//reset to default shader without blurs
		spriteBatch.setShader(null);

		//draw FPS
		//fps.draw(spriteBatch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 5, Gdx.graphics.getHeight()-5);

		//finally, end the batch since we have reached the end of the frame
		spriteBatch.end();
	}

	@Override
	public void resize(int width, int height) {
	}

	private void resizeBatch(int width, int height) {
		camera.setToOrtho(false, width, height);
		spriteBatch.setProjectionMatrix(camera.combined);
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
		shapeRenderer.dispose();
		blurTargetA.dispose();
		blurTargetB.dispose();
		spriteBatch.dispose();
		blurShader.dispose();
	}
}
