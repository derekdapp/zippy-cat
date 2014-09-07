package com.derkydapps.gameobjects;

import java.util.Random;

import com.derkydapps.gameworld.GameWorld;
import com.derkydapps.zchelpers.AssetLoader;

public class ScrollHandler {

	private Branch pipe1, pipe2, pipe3, pipe4, pipe5;
	Cat cat;
	public static final int SCROLL_SPEED = 0;
	//public static final int PIPE_GAP = 1;
	float gameHeight;
	int count =0;
	
	int mingap = 10;
	float gameheightdividedbyfive;
	
	private GameWorld gameWorld;

	public ScrollHandler(GameWorld gameWorld, float gameheight) {
		this.gameWorld = gameWorld;
		//frontGrass = new Grass(0, yPos, 143, 11, SCROLL_SPEED);
		//backGrass = new Grass(frontGrass.getTailX(), yPos, 143, 11,
				//SCROLL_SPEED);
		gameHeight = gameheight;
		gameheightdividedbyfive=gameHeight/5;
		Random r = new Random();
		int temp = r.nextInt(90) - 136;
		LastUsed.setLastused(-120);
		pipe1 = new Branch(-120, 0, 136, 16, SCROLL_SPEED, gameheight);
		//LastUsed.setLastused(-60);
		pipe2 = new Branch(-60, gameheightdividedbyfive, 136, 16, SCROLL_SPEED, gameheight);
		temp = r.nextInt(90) - 136;
		while(temp >= LastUsed.getLastused() && temp <= LastUsed.getLastused()+mingap){
			temp = r.nextInt(90) - 136;
		}
		//LastUsed.setLastused(temp);
		pipe3 = new Branch(0, gameheightdividedbyfive*2+1, 136, 16, SCROLL_SPEED, gameheight);
		temp = r.nextInt(90) - 136;
		pipe4 = new Branch(temp, gameheightdividedbyfive*3, 136, 16, SCROLL_SPEED, gameheight);
		temp = r.nextInt(90) - 136;
		while(temp >= LastUsed.getLastused() && temp <= LastUsed.getLastused()+mingap){
			temp = r.nextInt(90) - 136;
		}
		//LastUsed.setLastused(temp);
		pipe5 = new Branch(temp, gameheightdividedbyfive*4, 136, 16, SCROLL_SPEED, gameheight);
		//pipe3 = new Pipe(pipe2.getTailX() + PIPE_GAP, 0, 22, 60, SCROLL_SPEED,
				//yPos);
	}

	public void update(float delta) {
		// Update our objects
		//frontGrass.update(delta);
		//backGrass.update(delta);
		pipe1.update(delta);
		pipe2.update(delta);
		pipe3.update(delta);
		pipe4.update(delta);
		pipe5.update(delta);
		if(pipe2.position.y >= gameheightdividedbyfive*2 && pipe2.position.y <= (gameheightdividedbyfive*2)+2 && pipe2.passedCat == true && pipe2.falling == true){
			pipe2.position.y = gameheightdividedbyfive*2;
			pipe1.position.y = gameheightdividedbyfive;
			pipe5.position.y = 0;
			pipe4.position.y = gameheightdividedbyfive*4;
			pipe3.position.y = gameheightdividedbyfive*3;
			if(pipe2.scorable == true){
			AssetLoader.coin.play();	
			addScore(1);
			pipe2.setScored(true);}
			else{
				addSubScore();
			}
			//AssetLoader.coin.play();
			stop();
		}
		else if(pipe3.position.y >= gameheightdividedbyfive*2 && pipe3.position.y <= (gameheightdividedbyfive*2)+2 && pipe3.passedCat == true&& pipe3.falling == true){
			pipe3.position.y = gameheightdividedbyfive*2;
			pipe2.position.y = gameheightdividedbyfive;
			pipe1.position.y = 0;
			pipe5.position.y = gameheightdividedbyfive*4;
			pipe4.position.y = gameheightdividedbyfive*3;
			if(pipe3.scorable == true){
				AssetLoader.coin.play();	
				addScore(1);
				pipe3.setScored(true);}
			else{
				addSubScore();
			}
			//AssetLoader.coin.play();
			stop();
		}
		else if(pipe4.position.y >= gameheightdividedbyfive*2 && pipe4.position.y <= (gameheightdividedbyfive*2)+2 && pipe4.passedCat == true&& pipe4.falling == true){
			pipe4.position.y = gameheightdividedbyfive*2;
			pipe3.position.y = gameheightdividedbyfive;
			pipe2.position.y = 0;
			pipe1.position.y = gameheightdividedbyfive*4;
			pipe5.position.y = gameheightdividedbyfive*3;
			if(pipe4.scorable == true){
				AssetLoader.coin.play();	
				addScore(1);
				pipe4.setScored(true);}
			else{
				addSubScore();
			}
			//AssetLoader.coin.play();
			stop();
		}
		else if(pipe5.position.y >= gameheightdividedbyfive*2 && pipe5.position.y <= (gameheightdividedbyfive*2)+2 && pipe5.passedCat == true&& pipe5.falling == true){
			pipe5.position.y = gameheightdividedbyfive*2;
			pipe4.position.y = gameheightdividedbyfive;
			pipe3.position.y = 0;
			pipe2.position.y = gameheightdividedbyfive*4;
			pipe1.position.y = gameheightdividedbyfive*3;
			if(pipe5.scorable == true){
				AssetLoader.coin.play();	
				addScore(1);
				pipe5.setScored(true);}
			else{
				addSubScore();
			}
			//AssetLoader.coin.play();
			stop();
		}
		else if(pipe1.position.y >= (gameheightdividedbyfive*2) && pipe1.position.y <= (gameheightdividedbyfive*2)+2 && pipe1.passedCat == true&& pipe1.falling == true){
			pipe1.position.y = gameheightdividedbyfive*2;
			pipe5.position.y = gameheightdividedbyfive;
			pipe4.position.y = 0;
			pipe3.position.y = gameheightdividedbyfive*4;
			pipe2.position.y = gameheightdividedbyfive*3;
			if(pipe1.scorable == true){
				AssetLoader.coin.play();	
				addScore(1);
				pipe1.setScored(true);}
			else{
				addSubScore();
			}
			//AssetLoader.coin.play();
			stop();
		}
		
		
		
		

		// Check if any of the pipes are scrolled left,
		// and reset accordingly
		if (pipe1.isScrolledOut()) {
			pipe1.reset(0);
		}else if (pipe2.isScrolledOut()) {
			pipe2.reset(0);}
		else if (pipe3.isScrolledOut()) {
			pipe3.reset(0);
		}
		else if (pipe4.isScrolledOut()) {
			pipe4.reset(0);
			}
		else if (pipe5.isScrolledOut()) {
			pipe5.reset(0);
			}

		// Same with grass
		//if (frontGrass.isScrolledLeft()) {
			//frontGrass.reset(backGrass.getTailX());

		//} else if (backGrass.isScrolledLeft()) {
			//backGrass.reset(frontGrass.getTailX());
	}
		

	public void stop() {
		//frontGrass.stop();
		//backGrass.stop();
		pipe1.stop();
		pipe2.stop();
		pipe3.stop();
		pipe4.stop();
		pipe5.stop();
	}
	
	public boolean collides(Cat cat) {

		
		
		return (pipe1.collides(cat) || pipe2.collides(cat) || pipe3
				.collides(cat)) || pipe4.collides(cat) || pipe5.collides(cat);
	}

	private void addScore(int increment) {
		gameWorld.addScore(increment);
	}
	private void addSubScore(){
		gameWorld.addSubScore();
	}

	public Branch getPipe1() {
		return pipe1;
	}

	public Branch getPipe2() {
		return pipe2;
	}

	public Branch getPipe3() {
		return pipe3;
	}
	public Branch getPipe4() {
		return pipe4;
	}
	public Branch getPipe5() {
		return pipe5;
	}
	public void onRestart() {
			LastUsed.setLastused(-120);
	        pipe1.onRestart(-120, 0);
	        pipe2.onRestart(-60, gameheightdividedbyfive);
	        pipe3.onRestart(0, gameheightdividedbyfive*2+1);
	        pipe4.onRestart(-55, gameheightdividedbyfive*3);
	        pipe5.onRestart(-30, gameheightdividedbyfive*4);
	        pipe1.stop();pipe2.stop();pipe3.stop();pipe4.stop();pipe5.stop();
	    }


}
