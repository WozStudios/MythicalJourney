package com.woz.mythicaljourney.gameobjects;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/*
 * User: Daniel
 * Date: 4/28/13
 * Time: 4:46 AM
 */
public class Orb {
	private Vector2 position;
	private float radius;
	private Color color;
	private float growthSpeed;
	private float maxRadius;
	private OrbListener orbListener;
	private int ID;

	public Orb(Vector2 position, float radius, Color color, OrbListener orbListener) {
		this.position = position;
		this.radius = radius;
		this.color = color;
		this.orbListener = orbListener;

		growthSpeed = 35f;
		maxRadius = 100f;
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
		this.color = color;
	}

	public void setColor(float r, float g, float b, float a) {
		color.r = r;
		color.g = g;
		color.b = b;
		color.a = a;
	}

	public void setAlpha(float alpha) {
		color.set(color.r, color.g, color.b, alpha);
	}

	public void update(float  delta) {
		radius += growthSpeed * delta;

		if (radius > maxRadius) {
			die();
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
