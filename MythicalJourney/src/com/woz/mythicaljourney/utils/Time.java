package com.woz.mythicaljourney.utils;

/*
 * User: Daniel
 * Date: 4/29/13
 * Time: 4:19 PM
 */
public class Time {
	private static float elapsedTime = 0f;

	public static void update(float delta) {
		elapsedTime += delta;
	}

	public static float getTime() {
		return elapsedTime;
	}
}
