package com.derkydapps.gameobjects;


public class LastUsed{

    private static int lastused = 0;

	public static int getLastused() {
		return lastused;
	}

	public static void setLastused(int lastused) {
		LastUsed.lastused = lastused;
	}

    
}