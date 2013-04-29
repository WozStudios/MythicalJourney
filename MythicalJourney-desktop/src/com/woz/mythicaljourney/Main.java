package com.woz.mythicaljourney;

import com.badlogic.gdx.backends.lwjgl.LwjglApplication;
import com.badlogic.gdx.backends.lwjgl.LwjglApplicationConfiguration;

public class Main {
	public static void main(String[] args) {
		LwjglApplicationConfiguration cfg = new LwjglApplicationConfiguration();
		cfg.title = "MythicalJourney";
		cfg.useGL20 = true;
		cfg.width = 1270;
		cfg.height = 720;
		
		new LwjglApplication(new MythicalJourneyGame(), cfg);
	}
}
