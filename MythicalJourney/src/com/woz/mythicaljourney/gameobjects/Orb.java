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

	public Orb(Vector2 position, float radius, Color color) {
		this.position = position;
		this.radius = radius;
		this.color = color;
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

}
