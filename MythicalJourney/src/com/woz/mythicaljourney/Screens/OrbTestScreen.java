package com.woz.mythicaljourney.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;
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
	private SpriteBatch batch;
	private OrthographicCamera camera;
	private BitmapFont fps;

	private ShaderProgram blurShader;
	private static final int FBO_SIZE = 1024;
	private static final float MAX_BLUR = 8f;
	private final TextureRegion fboRegion;
	private float brightnessScale;
	private final Color orbColor;
	private float orbRadius;
	private Array<Orb> orbs;

	public OrbTestScreen(MythicalJourneyGame game) {
		this.game = game;

		orbs = new Array<Orb>();

		orbColor = new Color(0.3f, 0.7f, 0.8f, 1.0f);
		orbRadius = 100f;

		orb = new Orb(new Vector2(300, 400), orbRadius, orbColor);

		orbs.add(orb);

		shapeRenderer = new ShapeRenderer();

		initializeShaders();

		blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		fboRegion = new TextureRegion(blurTargetA.getColorBufferTexture());
		fboRegion.flip(false, true);

		batch = new SpriteBatch();

		brightnessScale = 2f;

		fps = new BitmapFont();

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
		blurShader.setUniformf("radius", MAX_BLUR);
		blurShader.end();
	}

	@Override
	public void show() {
		Gdx.app.log("LOG", "Orb Test Loaded!");
	}

	@Override
	public void render(float delta) {
		updateOrbRadius(delta);

		Gdx.gl.glEnable(GL20.GL_BLEND);

		//Start rendering to an offscreen color buffer
		blurTargetA.begin();

		//Clear the offscreen buffer with an opaque background
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		//before rendering, ensure we are using the default shader
		batch.setShader(null);

		//resize the batch projection matrix before drawing with it
		resizeBatch(FBO_SIZE, FBO_SIZE);

		renderOrb();

		//finish rendering to the offscreen buffer
		blurTargetA.end();

		//our first blur pass goes to target B
		blurTargetB.begin();

		//now let's start blurring the offscreen image
		batch.setShader(blurShader);

		batch.begin();

		//ensure the direction is along the X-axis only
		blurShader.setUniformf("dir", 1f, 0f);

		//we want to render FBO target A into target B
		fboRegion.setTexture(blurTargetA.getColorBufferTexture());

		//draw the scene to target B with a horizontal blur effect

		batch.enableBlending();
		batch.draw(fboRegion, 0, 0);

		//flush the batch before ending the FBO
		batch.flush();

		//finish rendering target B
		blurTargetB.end();

		//now we can render to the screen using the vertical blur shader

		Gdx.gl.glClearColor(0.8f, 0.8f, 0.6f, 0f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);


		//update our projection matrix with the screen size
		resizeBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch.end();


		orb.setRadius(300);
		//renderOrb();

		orb.setRadius(100);
		orb.setColor(orbColor);
		shapeRenderer.setColor(orbColor);

		//renderOrb();

		batch.begin();

		//update the blur only along Y-axis
		blurShader.setUniformf("dir", 0f, 1f);

		//draw target B to the screen with a vertical blur effect
		fboRegion.setTexture(blurTargetB.getColorBufferTexture());

		batch.setBlendFunction(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		batch.enableBlending();
		batch.draw(fboRegion, 0, 0);
		batch.disableBlending();

		//reset to default shader without blurs
		batch.setShader(null);

		//draw FPS
		fps.draw(batch, String.valueOf(Gdx.graphics.getFramesPerSecond()), 5, Gdx.graphics.getHeight()-5);

		//finally, end the batch since we have reached the end of the frame
		batch.end();
	}

	private void updateOrbRadius(float delta) {
		orbRadius -= 10f * delta;

		if (orbRadius < 10) {
			if (orbs.size > 0) {
				Gdx.app.log("LOG", "Orb killed");
				orbs.removeIndex(0);
			}
		}

		for (Orb orb : orbs) {
			orb.setRadius(orbRadius);
		}
	}

	@Override
	public void resize(int width, int height) {
	}

	private void resizeBatch(int width, int height) {
		camera.setToOrtho(false, width, height);
		batch.setProjectionMatrix(camera.combined);
	}


	private void renderOrb() {
		shapeRenderer.setColor(orb.getColor());
		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		for (Orb orb : orbs) {
			shapeRenderer.circle(orb.getPosition().x, orb.getPosition().y, orb.getRadius());
		}
		shapeRenderer.end();
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
		batch.dispose();
		blurShader.dispose();
	}
}
