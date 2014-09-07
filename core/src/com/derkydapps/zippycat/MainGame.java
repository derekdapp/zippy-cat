package com.derkydapps.zippycat;

import com.badlogic.gdx.Game;
import com.derkydapps.gameworld.GameRenderer;
import com.derkydapps.screens.SplashScreen;
import com.derkydapps.zchelpers.AssetLoader;

public class MainGame extends Game {

	private final ActionResolver game;
	boolean donated;
	public MainGame(ActionResolver game, boolean donated){
		this.game = game;
		this.donated = donated;
		//platformInterface.Login();
		}


	@Override
    public void create() {
        AssetLoader.load(game, donated);
        setScreen(new SplashScreen(this));
    }

    @Override
    public void dispose() {
        super.dispose();
        AssetLoader.dispose();
        GameRenderer.dispose();
    }
    public ActionResolver getGame(){
    	return game;
    }
    

}