package com.woz.mythicaljourney;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.*;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.FrameBuffer;
import com.badlogic.gdx.graphics.glutils.ShaderProgram;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.utils.Array;
import com.woz.mythicaljourney.gameobjects.Orb;
import com.woz.mythicaljourney.gameobjects.Orb.OrbPart;
import com.woz.mythicaljourney.gameobjects.OrbListener;

/*
 * User: Daniel
 * Date: 4/29/13
 * Time: 1:54 AM
 */
public class OrbRenderer implements OrbListener {
	private static final int FBO_SIZE = 1024;

	private FrameBuffer blurTargetA;
	private FrameBuffer blurTargetB;

	private ShapeRenderer shapeRenderer;
	private ShaderProgram blurShader;

	private TextureRegion fboRegion;
	private float blurAmount;

	private Array<Orb> orbs;
	private Color orbColor;

	private SpriteBatch batch;
	private OrthographicCamera camera;

	public OrbRenderer(SpriteBatch batch, OrthographicCamera camera) {
		//this.batch = batch;
		//this.camera = camera;

		this.batch = new SpriteBatch();
		this.camera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		orbColor = new Color(0.3f, 0.8f, 0.9f, 1.0f);
		orbs = new Array<Orb>();

		shapeRenderer = new ShapeRenderer();

		blurTargetA = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		blurTargetB = new FrameBuffer(Pixmap.Format.RGBA8888, FBO_SIZE, FBO_SIZE, false);
		fboRegion = new TextureRegion(blurTargetA.getColorBufferTexture());
		fboRegion.flip(false, true);
		blurAmount = 8f;

		initializeShaders();
	}

	public void render(float delta) {
		updateOrbs(delta);

		blurTargetA.begin();
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		batch.setShader(null);
		resizeBatch(FBO_SIZE, FBO_SIZE);
		renderOrbs(OrbPart.Light);
		blurTargetA.end();

		blurTargetB.begin();
		Gdx.gl.glClearColor(0.0f, 0.0f, 0.0f, 1f);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);
		batch.setShader(blurShader);
		batch.begin();
		blurShader.setUniformf("dir", 1f, 0f);
		fboRegion.setTexture(blurTargetA.getColorBufferTexture());
		batch.draw(fboRegion, 0, 0);
		batch.flush();
		blurTargetB.end();

		Gdx.gl.glClearColor(0.8f, 0.8f, 0.6f, 0f);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		resizeBatch(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

		batch.end();

		renderOrbs(OrbPart.Dark);

		batch.begin();
		blurShader.setUniformf("dir", 0f, 1f);
		fboRegion.setTexture(blurTargetB.getColorBufferTexture());
		batch.draw(fboRegion, 0, 0);
		batch.setShader(null);
		batch.end();
	}

	private void renderOrbs(OrbPart orbPart) {
		Gdx.gl.glEnable(GL20.GL_BLEND);
		Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);

		shapeRenderer.setProjectionMatrix(camera.combined);
		shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);

		float orbRadius = 0f;

		for (Orb orb : orbs) {
			if (orbPart == OrbPart.Light) {
				orb.setColor(orb.LIGHT_COLOR);
				orbRadius = orb.getRadius() * 1.2f;
			}
			else if (orbPart == OrbPart.Dark) {
				orb.setColor(orb.DARK_COLOR);
				orbRadius = orb.getRadius();
			}
			shapeRenderer.setColor(orb.getColor());
			shapeRenderer.circle(orb.getPosition().x, orb.getPosition().y, orbRadius);
		}
		shapeRenderer.end();
	}

	private void resizeBatch(int width, int height) {
		camera.setToOrtho(false, width, height);
		batch.setProjectionMatrix(camera.combined);
	}

	private void updateOrbs(float delta) {
		for (Orb orb : orbs) {
			orb.update(delta);
		}
	}

	private void initializeShaders() {
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

		blurShader.begin();
		blurShader.setUniformf("dir", 0f, 0f);
		blurShader.setUniformf("resolution", FBO_SIZE);
		blurShader.setUniformf("radius", blurAmount);
		blurShader.end();
	}

	public void dispose() {
		blurTargetA.dispose();
		blurTargetB.dispose();
		blurShader.dispose();

		batch.dispose();

		shapeRenderer.dispose();
	}

	public void spawnOrb(int x, int y) {
		Vector3 touchPos = new Vector3();
		touchPos.set(x, y, 0);
		camera.unproject(touchPos);

		Orb orb = new Orb(new Vector2(touchPos.x, touchPos.y), 10f, new Color(orbColor), this);
		orbs.add(orb);

		Gdx.app.log("LOG", "Orb Spawned at: (" + touchPos.x + ", " + touchPos.y + ")!");
	}

	@Override
	public void notifyDead(Orb orb) {
		if (orbs.removeValue(orb, true)) {
			Gdx.app.log("LOG", "Orb killed");
		}
	}
}
