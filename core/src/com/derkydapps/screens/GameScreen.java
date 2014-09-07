package com.derkydapps.screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.input.GestureDetector;
import com.derkydapps.gameworld.GameRenderer;
import com.derkydapps.gameworld.GameWorld;
import com.derkydapps.zchelpers.InputHandler;

public class GameScreen implements Screen {

	private GameWorld world;
	private GameRenderer renderer;
	private float runTime;

	// This is the constructor, not the class declaration
	public GameScreen() {

		float screenWidth = Gdx.graphics.getWidth();
		float screenHeight = Gdx.graphics.getHeight();
		float gameWidth = 136;
		float gameHeight = screenHeight / (screenWidth / gameWidth);

		int midPointY = (int) (gameHeight / 2);

		world = new GameWorld(gameHeight, screenWidth);
		renderer = new GameRenderer(world, (int) gameHeight, midPointY);

		//Gdx.input.setInputProcessor(new InputHandler(world.getBird()));
		Gdx.input.setInputProcessor(new GestureDetector(new InputHandler(world)));
		

	}

	@Override
	public void render(float delta) {
		runTime += delta;
		world.update(delta);
		renderer.render(delta,runTime);
	}

	@Override
	public void resize(int width, int height) {
		System.out.println("GameScreen - resizing");
	}

	@Override
	public void show() {
		System.out.println("GameScreen - show called");
	}

	@Override
	public void hide() {
		System.out.println("GameScreen - hide called");
	}

	@Override
	public void pause() {
		System.out.println("GameScreen - pause called");
	}

	@Override
	public void resume() {
		System.out.println("GameScreen - resume called");
	}

	@Override
	public void dispose() {
		// Leave blank
	}

}
