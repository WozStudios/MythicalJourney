package com.woz.mythicaljourney.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Net;
import com.badlogic.gdx.Net.HttpRequest;
import com.badlogic.gdx.Net.HttpResponse;
import com.badlogic.gdx.Net.HttpResponseListener;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.GL10;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.woz.mythicaljourney.MythicalJourneyGame;

import java.io.IOException;
import java.io.InputStream;

/*
 * User: Daniel
 * Date: 4/28/13
 * Time: 6:49 PM
 */
public class NetTestScreen implements Screen, HttpResponseListener {
	private String url;
	private Texture texture;
	private SpriteBatch batch;

	public NetTestScreen(MythicalJourneyGame mythicalJourneyGame) {
		url = "http://www.google.ca";

		batch = new SpriteBatch();
	}

	@Override
	public void show() {
		HttpRequest httpRequest = new HttpRequest(Net.HttpMethods.GET);
		httpRequest.setUrl("http://www.google.ca/images/srpr/logo4w.png");

		Gdx.net.sendHttpRequest(httpRequest, NetTestScreen.this);
	}

	@Override
	public void render(float delta) {
		Gdx.gl.glClearColor(0.2f, 0.2f, 0.2f, 1);
		Gdx.gl.glClear(GL10.GL_COLOR_BUFFER_BIT);

		if (texture != null) {
			batch.begin();
			batch.draw(texture, Gdx.graphics.getWidth() * 0.5f - texture.getWidth() * 0.5f, 100f);
			batch.end();
		}
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

	@Override
	public void dispose() {
		batch.dispose();
		if (texture != null) texture.dispose();
	}

	@Override
	public void handleHttpResponse(HttpResponse httpResponse) {
		int statusCode = httpResponse.getStatus().getStatusCode();
		Gdx.app.log("LOG", "HTTP Request status: " + statusCode);

		if (statusCode != 200) {
			Gdx.app.log("NetAPITest", "An error ocurred since statusCode is not OK");
			return;
		}

		final InputStream resultAsStream = httpResponse.getResultAsStream();
		try {
			Texture.setEnforcePotImages(false);
			texture = new Texture(new FileHandle("image.jpg") {
				@Override
				public InputStream read () {
					return resultAsStream;
				}
			});
			Texture.setEnforcePotImages(true);
		} finally {
			if (resultAsStream != null) try {
				resultAsStream.close();
			} catch (IOException e) {

			}
		}
	}

	@Override
	public void failed(Throwable t) {
		Gdx.app.log("LOG", "Failed to perform the HTTP Request: " + t.getMessage());
		t.printStackTrace();
	}
}
