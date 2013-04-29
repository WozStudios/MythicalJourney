package com.woz.mythicaljourney.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

/*
 * User: Daniel
 * Date: 4/28/13
 * Time: 4:46 AM
 */
public class Orb {
	public final Color LIGHT_COLOR;
	public final Color DARK_COLOR;
	private final float brightnessScale;

	public enum OrbPart {
		Light,
		Dark
	}

	private Vector2 position;
	private float radius;
	private Color color;
	private float growthSpeed;
	private float maxRadius;
	private OrbListener orbListener;
	private int ID;
	private boolean isDying;
	private float alpha;
	private float fadeSpeed;

	public Orb(Vector2 position, float radius, Color color, OrbListener orbListener) {
		this.position = position;
		this.radius = radius;
		this.color = color;
		this.orbListener = orbListener;

		growthSpeed = 50f;
		maxRadius = 100f;
		isDying = false;
		alpha = 1.0f;
		fadeSpeed = 6f;

		brightnessScale = 0.666666f;

		LIGHT_COLOR = generateRandomColor(0.4f, 1.0f);
		DARK_COLOR = new Color(LIGHT_COLOR.r * brightnessScale, LIGHT_COLOR.g * brightnessScale, LIGHT_COLOR.b * brightnessScale, 1.0f);
	}

	private Color generateRandomColor(float min, float max) {
		float r = MathUtils.random(min, max);
		float g = MathUtils.random(min, max);
		float b = MathUtils.random(min, max);

		return new Color(r, g, b, 1.0f);
	}

	public Vector2 getPosition() {
		return position;
	}

	public void setPosition(Vector2 position) {
		this.position = position;
	}

	public float getRadius() {
		return radius;
	}

	public void setRadius(float radius) {
		this.radius = radius;
	}

	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color.r = color.r;
		this.color.g = color.g;
		this.color.b = color.b;
	}

	public void setColor(float r, float g, float b, float a) {
		color.r = r;
		color.g = g;
		color.b = b;
		color.a = a;
	}

	public void setAlpha(float alpha) {
		color.a = alpha;
	}

	public void update(float  delta) {
		if (!isDying) {
			radius += growthSpeed * delta;

			if (radius > maxRadius) {
				isDying = true;
			}
		}
		else {
			alpha -= fadeSpeed * delta;
			setAlpha(alpha);

			if (alpha < 0f) {
				die();
			}
		}
	}

	private void die() {
		orbListener.notifyDead(this);

		//TODO add fadeout to dying orbs
	}

	public int getID() {
		return ID;
	}

	public void setID(int ID) {
		this.ID = ID;
	}
}
