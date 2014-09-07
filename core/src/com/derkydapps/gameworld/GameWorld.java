package com.derkydapps.gameworld;

import com.badlogic.gdx.math.Rectangle;
import com.derkydapps.gameobjects.Cat;
import com.derkydapps.gameobjects.ScrollHandler;
import com.derkydapps.gameobjects.Water;
import com.derkydapps.zchelpers.AssetLoader;

public class GameWorld {

	private Cat cat;
	private ScrollHandler scroller;
	private Water water;
	private int score = 0;
	private int subScore = 0;
	private GameState currentState;
	float gameheight;
	private float runTime = 0;
	
	public GameWorld(float gameheight, float screenwidth) {
		this.gameheight = gameheight;
		currentState = GameState.MENU;
		scroller = new ScrollHandler(this, gameheight);
		cat = new Cat(-17, (gameheight/5)*2-14, 20, 15, screenwidth, gameheight, scroller);
		water = new Water(0, gameheight, 136,cat,scroller,this);
		
		
		//start();
	}

	 public void update(float delta) {

		 runTime += delta;

	        switch (currentState) {
	        case READY:
	        	updateRunning(delta);
	        	break;
	        case MENU:
	            updateReady(delta);
	            break;

	        case RUNNING:
	            updateRunning(delta);
	            break;
	        default:
	            break;
	        }

	    }

	    private void updateReady(float delta) {
	    	
	    }
	public void updateRunning(float delta) {
		// Add a delta cap so that if our game takes too long
		// to update, we will not break our collision detection.

		if (delta > .15f) {
			delta = .15f;
		}

		cat.update(delta);
		scroller.update(delta);
		water.update(delta);
		if (scroller.collides(cat) && cat.isAlive() || water.collides() && cat.isAlive()) {
			scroller.stop();
			cat.die();
			AssetLoader.dead.play();
			AssetLoader.setGameCount(AssetLoader.getGameCount() +1);
			AssetLoader.setHopCount(AssetLoader.getHopCount() + subScore);
			if(score > AssetLoader.getHighScore()){
				AssetLoader.setHighScore(score);
				AssetLoader.getGame().submitScoreGPGS("CgkIsuizifIDEAIQAQ", score);
				if(AssetLoader.getDonateState()){
					AssetLoader.getGame().submitScoreGPGS("CgkIsuizifIDEAIQAg", score);
				}
			}
		}


	}

	public Cat getCat() {
		return cat;

	}
	public Water getWater(){
		return water;
	}
	
	public ScrollHandler getScroller() {
		return scroller;
	}
	
	public int getScore() {
		return score;
	}
	public int getSubScore(){
		return subScore;
	}
	public void addScore(int increment) {
		score += increment;
	}
	public void addSubScore(){
		subScore++;
	}
	public enum GameState {

	    MENU,READY, RUNNING, GAMEOVER, HIGHSCORE, DONATE, SKIN

	}
	public boolean isReady() {
        return currentState == GameState.READY;
    }
	public boolean isSkin() {
        return currentState == GameState.SKIN;
    }

    public void start() {
        currentState = GameState.READY;
    }

    public void restart() {
        currentState = GameState.READY;
        score = 0;
        subScore = 0;
        cat.onRestart();
        scroller.onRestart();
        water.onRestart();
       
    }

    public boolean isGameOver() {
        return currentState == GameState.GAMEOVER;
    }
    public boolean isHighScore() {
        return currentState == GameState.HIGHSCORE;
    }
    public void ready() {
        currentState = GameState.READY;
    }
	public float getMidPointY() {
		return gameheight/2;
	}
	 public boolean isMenu() {
	        return currentState == GameState.MENU;
	    }

	    public boolean isRunning() {
	        return currentState == GameState.RUNNING;
	    }
	    public boolean isDonate() {
	        return currentState == GameState.DONATE;
	    }
	  
    public void setCurrentStateReady(){
    	currentState = GameState.READY;
    }
    public void setCurrentStateDonate(){
    	currentState = GameState.DONATE;
    }
    public void setCurrentStateRunning(){
    	currentState = GameState.RUNNING;
    }
    public void setCurrentStateGameover(){
    	currentState = GameState.GAMEOVER;
    }
    public void setCurrentStateMenu(){
    	currentState = GameState.MENU;
    }
    public void setCurrentStateSkin(){
    	currentState = GameState.SKIN;
    }
}
